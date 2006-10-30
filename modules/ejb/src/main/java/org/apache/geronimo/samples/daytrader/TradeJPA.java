/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


package org.apache.geronimo.samples.daytrader;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.geronimo.samples.daytrader.ejb.Trade;
import org.apache.geronimo.samples.daytrader.util.FinancialUtils;
import org.apache.geronimo.samples.daytrader.util.Log;
import org.apache.geronimo.samples.daytrader.util.MDBStats;

public class TradeJPA implements SessionBean {

    private EntityManager entityManager;

    private SessionContext context = null;

    private ConnectionFactory qConnFactory = null;
    private Queue queue = null;
    private ConnectionFactory tConnFactory = null;
    private Topic streamerTopic = null;

    private boolean publishQuotePriceChange = true;

    private void queueOrderInternal(Integer orderID, boolean twoPhase)
            throws javax.jms.JMSException {
        if (Log.doTrace()) Log.trace("TradeBean:queueOrderInternal", orderID);

        Connection conn = null;
        Session sess = null;
        try {
            conn = qConnFactory.createConnection();
            sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer msgProducer = sess.createProducer(queue);
            TextMessage message = sess.createTextMessage();

            message.setStringProperty("command", "neworder");
            message.setIntProperty("orderID", orderID);
            message.setBooleanProperty("twoPhase", twoPhase);
            message.setText("neworder: orderID=" + orderID + " runtimeMode=EJB twoPhase=" + twoPhase);
            message.setLongProperty("publishTime", System.currentTimeMillis());

            if (Log.doTrace()) Log.trace("TradeBean:queueOrder Sending message: " + message.getText());
            msgProducer.send(message);
        }
        catch (javax.jms.JMSException e) {
            throw e; // pass the exception back
        }

        finally {
            if (conn != null)
                conn.close();
            if (sess != null)
                sess.close();
        }
    }

    /**
     * @see org.apache.geronimo.samples.daytrader.TradeServices#queueOrder(Integer, boolean)
     */

    public void queueOrder(Integer orderID, boolean twoPhase)
            throws Exception {
        if (Log.doTrace()) Log.trace("TradeBean:queueOrder", orderID, twoPhase);
        if (twoPhase)
            queueOrderInternal(orderID, true);
        else {
            // invoke the queueOrderOnePhase method -- which requires a new transaction
            // the queueOrder will run in it's own transaction thus not requiring a
            // 2-phase commit
            ((Trade) context.getEJBObject()).queueOrderOnePhase(orderID);
        }
    }


    /**
     * @see Trade#queueOrderOnePhase(Integer)
     *      Queue the Order identified by orderID to be processed in a One Phase commit
     *      <p/>
     *      In short, this method is deployed as TXN REQUIRES NEW to avoid a
     *      2-phase commit transaction across Entity and MDB access
     */

    public void queueOrderOnePhase(Integer orderID) {
        try {
            if (Log.doTrace()) Log.trace("TradeBean:queueOrderOnePhase", orderID);
            queueOrderInternal(orderID, false);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    class quotePriceComparator implements java.util.Comparator {
        public int compare(Object quote1, Object quote2) {
            double change1 = ((QuoteDataBean) quote1).getChange();
            double change2 = ((QuoteDataBean) quote2).getChange();
            return new Double(change2).compareTo(change1);
        }
    }

    public MarketSummaryDataBean getMarketSummary()
            throws Exception {


        MarketSummaryDataBean marketSummaryData;
        try {
            if (Log.doTrace()) {
                Log.trace("TradeBean:getMarketSummary -- getting market summary");
            }

            //Find Trade Stock Index Quotes (Top 100 quotes)
            //ordered by their change in value
            Collection quotes;
//            if (orderBySQLSupported) {
                Query query = entityManager.createNamedQuery("quotesByChange");
                quotes = query.getResultList();
//            }
//            else
//                quotes = quoteHome.findTSIAQuotes();

            //SORT by price change the collection of stocks if the AppServer
            //     does not support the "ORDER BY" SQL clause
//            if (! orderBySQLSupported) {
                //if (Log.doTrace())
//                Log.trace("TradeBean:getMarketSummary() -- Sorting TSIA quotes");
//                ArrayList sortedQuotes = new ArrayList(quotes);
//                java.util.Collections.sort(sortedQuotes, new TradeJPA.quotePriceComparator());
//                quotes = sortedQuotes;
//            }
            //SORT END
            QuoteDataBean[] quoteArray = (QuoteDataBean[]) quotes.toArray(new QuoteDataBean[quotes.size()]);
            ArrayList<QuoteDataBean> topGainers = new ArrayList<QuoteDataBean>(5);
            ArrayList<QuoteDataBean> topLosers = new ArrayList<QuoteDataBean>(5);
            BigDecimal TSIA = FinancialUtils.ZERO;
            BigDecimal openTSIA = FinancialUtils.ZERO;
            double totalVolume = 0.0;

            if (quoteArray.length > 5) {
                for (int i = 0; i < 5; i++)
                    topGainers.add(quoteArray[i]);
                for (int i = quoteArray.length - 1; i >= quoteArray.length - 5; i--)
                    topLosers.add(quoteArray[i]);

                for (QuoteDataBean quote : quoteArray) {
                    BigDecimal price = quote.getPrice();
                    BigDecimal open = quote.getOpen();
                    double volume = quote.getVolume();
                    TSIA = TSIA.add(price);
                    openTSIA = openTSIA.add(open);
                    totalVolume += volume;
                }
                TSIA = TSIA.divide(new BigDecimal(quoteArray.length),
                        FinancialUtils.ROUND);
                openTSIA = openTSIA.divide(new BigDecimal(quoteArray.length),
                        FinancialUtils.ROUND);
            }
            /* This is an alternate approach using ejbSelect methods
                *   In this approach an ejbSelect is used to select only the
                *   current price and open price values for the TSIA
                   LocalQuote quote = quoteHome.findOne();
                   BigDecimal TSIA = quote.getTSIA();
                   openTSIA = quote.getOpenTSIA();
                   Collection topGainers = quote.getTopGainers(5);
                   Collection topLosers = quote.getTopLosers(5);
                   LocalQuote quote = (LocalQuote)topGainers.iterator().next();
                     double volume = quote.getTotalVolume();
                *
                */

            marketSummaryData = new MarketSummaryDataBean(TSIA, openTSIA, totalVolume, topGainers, topLosers);
        }
        catch (Exception e) {
            Log.error("TradeBean:getMarketSummary", e);
            throw new EJBException("TradeBean:getMarketSummary -- error ", e);
        }
        return marketSummaryData;
    }

    public QuoteDataBean createQuote(String symbol, String companyName, BigDecimal price) {
        try {
            QuoteDataBean quote = new QuoteDataBean(symbol, companyName, 0, price, price, price, price, 0);
            entityManager.persist(quote);
            if (Log.doTrace()) Log.trace("TradeBean:createQuote-->" + quote);
            return quote;
        } catch (Exception e) {
            Log.error("TradeBean:createQuote -- exception creating Quote", e);
            throw new EJBException(e);
        }
    }

    public QuoteDataBean getQuote(String symbol) {

        if (Log.doTrace()) Log.trace("TradeBean:getQuote", symbol);
            return entityManager.find(QuoteDataBean.class, symbol);
//        } catch (NamingException e) {
//            throw new EJBException(e);
            //Cannot find quote for given symbol
//            Log.error("TradeBean:getQuote--> Symbol: " + symbol + " cannot be found");
//            BigDecimal z = new BigDecimal(0.0);
//            return new QuoteDataBean("Error: Symbol " + symbol + " not found", "", 0.0, z, z, z, z, 0.0);
//        }
    }

    public Collection getAllQuotes()
            throws Exception {
        if (Log.doTrace()) Log.trace("TradeBean:getAllQuotes");

            Query query = entityManager.createNamedQuery("allQuotes");
            return query.getResultList();
    }

    public QuoteDataBean updateQuotePriceVolume(String symbol, BigDecimal changeFactor, double sharesTraded)
            throws Exception {

        if (!TradeConfig.getUpdateQuotePrices())
            return new QuoteDataBean();

        if (Log.doTrace())
            Log.trace("TradeBean:updateQuote", symbol, changeFactor);

//        try {
            QuoteDataBean quote = entityManager.find(QuoteDataBean.class, symbol);
            BigDecimal oldPrice = quote.getPrice();

            if (quote.getPrice().equals(TradeConfig.PENNY_STOCK_PRICE)) {
                changeFactor = TradeConfig.PENNY_STOCK_RECOVERY_MIRACLE_MULTIPLIER;
            }

            BigDecimal newPrice = changeFactor.multiply(oldPrice).setScale(2, BigDecimal.ROUND_HALF_UP);

            quote.setPrice(newPrice);
            quote.setVolume(quote.getVolume() + sharesTraded);
//        TODO find out if requires new here is really intended -- it is backwards, change can get published w/o it occurring.
            ((Trade) context.getEJBObject()).publishQuotePriceChange(quote, oldPrice, changeFactor, sharesTraded);
//            publishQuotePriceChange(quote, oldPrice, changeFactor, sharesTraded);
            return quote;
//        } catch (FinderException fe) {
//            Cannot find quote for given symbol
//            Log.error("TradeBean:updateQuotePriceVolume--> Symbol: " + symbol + " cannot be found");
//            quoteData = new QuoteDataBean("Error: Symbol " + symbol + " not found");
//        }
    }

    public void publishQuotePriceChange(QuoteDataBean quoteData, BigDecimal oldPrice, BigDecimal changeFactor, double sharesTraded) {
        if (!publishQuotePriceChange)
            return;
        if (Log.doTrace())
            Log.trace("TradeBean:publishQuotePricePublishing -- quoteData = " + quoteData);

        Connection conn = null;
        Session sess = null;

        try {
            conn = tConnFactory.createConnection();
            sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer msgProducer = sess.createProducer(streamerTopic);
            TextMessage message = sess.createTextMessage();

            String command = "updateQuote";
            message.setStringProperty("command", command);
            message.setStringProperty("symbol", quoteData.getSymbol());
            message.setStringProperty("company", quoteData.getCompanyName());
            message.setStringProperty("price", quoteData.getPrice().toString());
            message.setStringProperty("oldPrice", oldPrice.toString());
            message.setStringProperty("open", quoteData.getOpen().toString());
            message.setStringProperty("low", quoteData.getLow().toString());
            message.setStringProperty("high", quoteData.getHigh().toString());
            message.setDoubleProperty("volume", quoteData.getVolume());

            message.setStringProperty("changeFactor", changeFactor.toString());
            message.setDoubleProperty("sharesTraded", sharesTraded);
            message.setLongProperty("publishTime", System.currentTimeMillis());
            message.setText("Update Stock price for " + quoteData.getSymbol() + " old price = " + oldPrice + " new price = " + quoteData.getPrice());

            msgProducer.send(message);
        }
        catch (Exception e) {
            throw new EJBException(e.getMessage(), e); // pass the exception back
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
                if (sess != null)
                    sess.close();
            } catch (Exception e) {
                throw new EJBException(e.getMessage(), e); // pass the exception back
            }
        }
    }

    public OrderDataBean buy(String userID, String symbol, double quantity, int orderProcessingMode)
            throws Exception {

        OrderDataBean order;
        BigDecimal total;
        try {
            if (Log.doTrace())
                Log.trace("TradeBean:buy", userID, symbol, quantity, orderProcessingMode);
            /*  The following commented code shows alternative forms of the finder needed for this
            *  method
            *  The first alternative requires a 2-table join. Some database cannot allocate an Update
            *  Lock on a join select.
            *
            *  The second alternative shows the finder being executed without allocation an update
            *  lock on the row. Normally, an update lock would not be necessary, but is required if
            *  the same user logs in multiple times to avoid a deadlock situation.
            *
            *  The third alternative runs the finder and allocates an update lock on the row(s)
            *
               LocalAccount account = accountHome.findByUserIDForUpdate(userID);

               LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKey(userID)).getAccountForUpdate();
            */
            AccountProfileDataBean profile = entityManager.find(AccountProfileDataBean.class, userID);
            AccountDataBean account = profile.getAccount();
            QuoteDataBean quote = entityManager.find(QuoteDataBean.class, symbol);
            HoldingDataBean holding = null;  //The holding will be created by this buy order
//            Integer orderID = keySequence.getNextID("order");

            order = createOrder(account, quote, holding, "buy", quantity);

            //UPDATE - account should be credited during completeOrder
            BigDecimal price = quote.getPrice();
            BigDecimal orderFee = order.getOrderFee();
            BigDecimal balance = account.getBalance();
            total = (new BigDecimal(quantity).multiply(price)).add(orderFee);
            account.setBalance(balance.subtract(total));

            if (orderProcessingMode == TradeConfig.SYNCH)
                completeOrderInternal(order.getOrderID());
            else if (orderProcessingMode == TradeConfig.ASYNCH)
                // Invoke the queueOrderOnePhase method w/ TXN requires new attribute
                // to side-step a 2-phase commit across DB and JMS access
                queueOrder(order.getOrderID(), false);
            else //TradeConfig.ASYNC_2PHASE
                queueOrder(order.getOrderID(), true);
        }
        catch (Exception e) {
            Log.error("TradeBean:buy(" + userID + "," + symbol + "," + quantity + ") --> failed", e);
            /* On exception - cancel the order */
            //TODO figure out how to do this with JPA
//            if (order != null) order.cancel();
            throw new EJBException(e);
        }
        return order;
    }

    public OrderDataBean sell(String userID, Integer holdingID, int orderProcessingMode)
            throws Exception {

        OrderDataBean order;
        BigDecimal total;
        try {
            if (Log.doTrace())
                Log.trace("TradeBean:sell", userID, holdingID, orderProcessingMode);

            /* Some databases cannot allocate an update lock on a JOIN
                * use the second approach below to acquire update lock
               LocalAccount account = accountHome.findByUserIDForUpdate(userID);
               */

            AccountProfileDataBean profile = entityManager.find(AccountProfileDataBean.class, userID);
            AccountDataBean account = profile.getAccount();
            HoldingDataBean holding = entityManager.find(HoldingDataBean.class, holdingID);
//            catch (ObjectNotFoundException oe) {
//                Log.error("TradeBean:sell User " + userID + " attempted to sell holding " + holdingID + " which has already been sold");
//                OrderDataBean orderData = new OrderDataBean();
//                orderData.setOrderStatus("cancelled");
//                return orderData;
//            }
            QuoteDataBean quote = holding.getQuote();
            double quantity = holding.getQuantity();
            order = createOrder(account, quote, holding, "sell", quantity);

            //UPDATE the holding purchase data to signify this holding is "inflight" to be sold
            //    -- could add a new holdingStatus attribute to holdingEJB
            holding.setPurchaseDate(new java.sql.Timestamp(0));

            //UPDATE - account should be credited during completeOrder
            BigDecimal price = quote.getPrice();
            BigDecimal orderFee = order.getOrderFee();
            BigDecimal balance = account.getBalance();
            total = (new BigDecimal(quantity).multiply(price)).subtract(orderFee);
            account.setBalance(balance.add(total));

            if (orderProcessingMode == TradeConfig.SYNCH)
                completeOrderInternal(order.getOrderID());
            else if (orderProcessingMode == TradeConfig.ASYNCH)
                queueOrder(order.getOrderID(), false);
            else //TradeConfig.ASYNC_2PHASE
                queueOrder(order.getOrderID(), true);

        }
        catch (Exception e) {
            Log.error("TradeBean:sell(" + userID + "," + holdingID + ") --> failed", e);
            //TODO figure out JPA cancel
//            if (order != null) order.cancel();
            //UPDATE - handle all exceptions like:
            throw new EJBException("TradeBean:sell(" + userID + "," + holdingID + ")", e);
        }
        return order;
    }


    public Collection getOrders(String userID)
            throws FinderException, Exception {

        if (Log.doTrace())
            Log.trace("TradeBean:getOrders", userID);

        /*  The following commented code shows alternative forms of the finder needed for this
           *  method
           *  The first alternative requires a 2-table join. Some database cannot allocate an Update
           *  Lock on a join select.
           *
           *  The second alternative shows the finder being executed without allocation an update
           *  lock on the row. Normally, an update lock would not be necessary, but is required if
           *  the same user logs in multiple times to avoid a deadlock situation.
           *
           *  The third alternative runs the finder and allocates an update lock on the row(s)
           *

              Collection orders = accountHome.findByUserID(userID).getOrders();

              LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKey(userID)).getAccountForUpdate();
           */
        AccountProfileDataBean profile = entityManager.find(AccountProfileDataBean.class, userID);
        AccountDataBean account = profile.getAccount();
        return account.getOrders();

//        ArrayList dataBeans = new ArrayList();
//        if (orders == null) return dataBeans;
//
//        Iterator it = orders.iterator();
        //TODO: return top 5 orders for now -- next version will add a getAllOrders method
        //      also need to get orders sorted by order id descending
//        int i = 0;
//        while ((it.hasNext()) && (i++ < 5))
//            dataBeans.add(((OrderData) it.next()).getDataBean());
//
//        return dataBeans;
    }

    public Collection getClosedOrders(String userID)
            throws FinderException, Exception {
        if (Log.doTrace())
            Log.trace("TradeBean:getClosedOrders", userID);

        try {

            /*  The following commented code shows alternative forms of the finder needed for this
            *  method
            *  The first alternative requires a 2-table join. Some database cannot allocate an Update
            *  Lock on a join select.
            *
            *  The second alternative shows the finder being executed without allocation an update
            *  lock on the row. Normally, an update lock would not be necessary, but is required if
            *  the same user logs in multiple times to avoid a deadlock situation.
            *
            *  The third alternative runs the finder and allocates an update lock on the row(s)
            *
               Collection orders = orderHome.findClosedOrdersForUpdate(userID);
            *
                  LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKey(userID)).getAccount();
            */

            //Get the primary keys for all the closed Orders for this account.
            Query query = entityManager.createNamedQuery("closedOrders");
            query.setParameter("userId", userID);
            Collection results = query.getResultList();
            Query updateStatus = entityManager.createNamedQuery("completeClosedOrders");
            updateStatus.setParameter("userId", userID);
            updateStatus.executeUpdate();
            return results;
//            if (ordersKeys == null) return dataBeans;
//
//            Iterator it = ordersKeys.iterator();
//            while (it.hasNext()) {
//                Integer orderKey = (Integer) it.next();
//                LocalOrder order = (LocalOrder) orderHome.findByPrimaryKeyForUpdate(orderKey);
                //Complete the order
//                order.setOrderStatus("completed");
//                dataBeans.add(order.getDataBean());
//            }

        }
        catch (Exception e) {
            Log.error("TradeBean.getClosedOrders", e);
            throw new EJBException("TradeBean.getClosedOrders - error", e);
        }
    }


    public OrderDataBean completeOrder(Integer orderID, boolean twoPhase)
            throws Exception {
        if (Log.doTrace()) Log.trace("TradeBean:completeOrder", orderID + " twoPhase=" + twoPhase);
        if (twoPhase)
            return completeOrderInternal(orderID);
        else {
            // invoke the completeOrderOnePhase -- which requires a new transaction
            // the completeOrder will run in it's own transaction thus not requiring a
            // 2-phase commit
            return ((Trade) context.getEJBObject()).completeOrderOnePhase(orderID);
        }
    }

    //completeOrderOnePhase method is deployed w/ TXN_REQUIRES_NEW
    //thus the completeOrder call from the MDB should not require a 2-phase commit
    public OrderDataBean completeOrderOnePhase(Integer orderID) {
        try {
            if (Log.doTrace()) Log.trace("TradeBean:completeOrderOnePhase", orderID);
            return completeOrderInternal(orderID);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    private OrderDataBean completeOrderInternal(Integer orderID)
            throws Exception {

         OrderDataBean order = entityManager.find(OrderDataBean.class, orderID);

        if (order == null) {
            Log.error("TradeBean:completeOrderInternal  -- Unable to find Order " + orderID + " FBPK returned " + order);
            return null;
        }

        if (order.isCompleted())
            throw new EJBException("Error: attempt to complete Order that is already completed\n" + order);

        AccountDataBean account = order.getAccount();
        QuoteDataBean quote = order.getQuote();
        HoldingDataBean holding = order.getHolding();
        BigDecimal price = order.getPrice();
        double quantity = order.getQuantity();

        /*
           * 	getProfile is marked as Pess. Update to get a DB lock
           * Here we invoke getProfileForRead which is deployed to not
           * lock the DB (Pess. Read)
           */
        String userID = account.getProfile().getUserID();

        /*
           * total = (quantity * purchasePrice) + orderFee
           */


        if (Log.doTrace()) Log.trace(
                "TradeBeanInternal:completeOrder--> Completing Order " + order.getOrderID()
                        + "\n\t Order info: " + order
                        + "\n\t Account info: " + account
                        + "\n\t Quote info: " + quote
                        + "\n\t Holding info: " + holding);


        if (order.isBuy()) {
            /* Complete a Buy operation
                *	- create a new Holding for the Account
                *	- deduct the Order cost from the Account balance
                */

            HoldingDataBean newHolding = createHolding(account, quote, quantity, price);
            order.setHolding(newHolding);
        }

        if (order.isSell()) {
            /* Complete a Sell operation
                *	- remove the Holding from the Account
                *	- deposit the Order proceeds to the Account balance
                */
            if (holding == null) {
                Log.error("TradeBean:completeOrderInternal -- Unable to sell order " + order.getOrderID() + " holding already sold");
                order.cancel();
                return order;
            } else {
                entityManager.remove(holding);
                order.setHolding(null);
//                holding.remove();
//                holding = null;
            }

            // This is managed by the container
            // order.setHolding(null);

        }
        order.setOrderStatus("closed");

        order.setCompletionDate(new java.sql.Timestamp(System.currentTimeMillis()));

        if (Log.doTrace()) Log.trace(
                "TradeBean:completeOrder--> Completed Order " + order.getOrderID()
                        + "\n\t Order info: " + order
                        + "\n\t Account info: " + account
                        + "\n\t Quote info: " + quote
                        + "\n\t Holding info: " + holding);

        if (Log.doTrace())
            Log.trace("Calling TradeAction:orderCompleted from Session EJB using Session Object");
        //FUTURE All getEJBObjects could be local -- need to add local I/F

        TradeServices trade = (TradeServices) context.getEJBObject();
        TradeAction tradeAction = new TradeAction(trade);

        //signify this order for user userID is complete
        //TODO figure out wtf is going on, all implementations of this method throw NotSupportedException !!!
        //Also try to figure out the business reason this should be in a separate tx ?!?!?!?
        //tradeAction.orderCompleted(userID, orderID);
        return order;
    }


    //These methods are used to provide the 1-phase commit runtime option for TradeDirect
    // Basically these methods are deployed as txn requires new and invoke TradeDirect methods
    // There is no mechanism outside of EJB to start a new transaction
    public OrderDataBean completeOrderOnePhaseDirect(Integer orderID) {
        try {
            if (Log.doTrace())
                Log.trace("TradeBean:completeOrderOnePhaseDirect -- completing order by calling TradeDirect orderID=" + orderID);
            return (new org.apache.geronimo.samples.daytrader.direct.TradeDirect()).completeOrderOnePhase(orderID);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    public void cancelOrderOnePhaseDirect(Integer orderID) {
        try {
            if (Log.doTrace())
                Log.trace("TradeBean:cancelOrderOnePhaseDirect -- cancelling order by calling TradeDirect orderID=" + orderID);
            (new org.apache.geronimo.samples.daytrader.direct.TradeDirect()).cancelOrderOnePhase(orderID);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }


    public void cancelOrder(Integer orderID, boolean twoPhase)
            throws Exception {
        if (Log.doTrace()) Log.trace("TradeBean:cancelOrder", orderID + " twoPhase=" + twoPhase);
        if (twoPhase)
            cancelOrderInternal(orderID);
        else {
            // invoke the cancelOrderOnePhase -- which requires a new transaction
            // the completeOrder will run in it's own transaction thus not requiring a
            // 2-phase commit
            ((Trade) context.getEJBObject()).cancelOrderOnePhase(orderID);
        }
    }

    //cancelOrderOnePhase method is deployed w/ TXN_REQUIRES_NEW
    //thus the completeOrder call from the MDB should not require a 2-phase commit
    public void cancelOrderOnePhase(Integer orderID) {
        try {
            if (Log.doTrace()) Log.trace("TradeBean:cancelOrderOnePhase", orderID);
            cancelOrderInternal(orderID);
        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }


    private void cancelOrderInternal(Integer orderID)
            throws Exception {
        OrderDataBean order = entityManager.find(OrderDataBean.class, orderID);
        order.cancel();
    }


    public void orderCompleted(String userID, Integer orderID)
            throws Exception {
        throw new UnsupportedOperationException("TradeBean:orderCompleted method not supported");
    }

    public HoldingDataBean createHolding(
            AccountDataBean account,
            QuoteDataBean quote,
            double quantity,
            BigDecimal purchasePrice)
            throws Exception {
        HoldingDataBean newHolding = new HoldingDataBean(quantity, purchasePrice, new Timestamp(System.currentTimeMillis()), account, quote);
        entityManager.persist(newHolding);
        return newHolding;
    }

    public Collection getHoldings(String userID)
            throws FinderException, Exception {
        if (Log.doTrace())
            Log.trace("TradeBean:getHoldings", userID);
        Query query = entityManager.createNamedQuery("holdingsByUserID");
        query.setParameter("userID", userID);
        return query.getResultList();
    }

    public HoldingDataBean getHolding(Integer holdingID)
            throws FinderException, Exception {
        if (Log.doTrace())
            Log.trace("TradeBean:getHolding", holdingID);
        return entityManager.find(HoldingDataBean.class, holdingID);
    }



    public OrderDataBean createOrder(
            AccountDataBean account,
            QuoteDataBean quote,
            HoldingDataBean holding,
            String orderType,
            double quantity)
            throws CreateException, Exception {

        OrderDataBean order;

        if (Log.doTrace())
            Log.trace(
                    "TradeBean:createOrder(orderID="
                            + " account="
                            + ((account == null) ? null : account.getAccountID())
                            + " quote="
                            + ((quote == null) ? null : quote.getSymbol())
                            + " orderType="
                            + orderType
                            + " quantity="
                            + quantity);
        try {
            assert quote != null;
            order = new OrderDataBean(orderType, "open", new Timestamp(System.currentTimeMillis()), null, quantity, quote.getPrice().setScale(FinancialUtils.SCALE, FinancialUtils.ROUND), TradeConfig.getOrderFee(orderType), account, quote, holding);
            entityManager.persist(order);
        }
        catch (Exception e) {
            Log.error("TradeBean:createOrder -- failed to create Order", e);
            throw new EJBException("TradeBean:createOrder -- failed to create Order", e);
        }
        return order;
    }

    public AccountDataBean login(String userID, String password)
            throws FinderException, Exception {

        /*  The following commented code shows alternative forms of the finder needed for this
           *  method
           *  The first alternative requires a 2-table join. Some database cannot allocate an Update
           *  Lock on a join select.
           *
           *  The second alternative shows the finder being executed without allocation an update
           *  lock on the row. Normally, an update lock would not be necessary, but is required if
           *  the same user logs in multiple times to avoid a deadlock situation.
           *
           *  The third alternative runs the finder and allocates an update lock on the row(s)
           *
              LocalAccount account = accountHome.findByUserIDForUpdate(userID);

              LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKey(userID)).getAccountForUpdate();
           */

        AccountProfileDataBean profile = entityManager.find(AccountProfileDataBean.class, userID);
        if (profile == null) {
            throw new EJBException("No such user: " + userID);
        }
        AccountDataBean account = profile.getAccount();

        if (Log.doTrace())
            Log.trace("TradeBean:login", userID, password);
        account.login(password);
        if (Log.doTrace())
            Log.trace("TradeBean:login(" + userID + "," + password + ") success" + account);
        return account;
    }

    public void logout(String userID)
            throws FinderException, Exception {
        if (Log.doTrace())
            Log.trace("TradeBean:logout", userID);

        /*  The following commented code shows alternative forms of the finder needed for this
           *  method
           *  The first alternative requires a 2-table join. Some database cannot allocate an Update
           *  Lock on a join select.
           *
           *  The second alternative shows the finder being executed without allocation an update
           *  lock on the row. Normally, an update lock would not be necessary, but is required if
           *  the same user logs in multiple times to avoid a deadlock situation.
           *
           *  The third alternative runs the finder and allocates an update lock on the row(s)

           * 	LocalAccount account = accountHome.findByUserIDForUpdate(userID);
              LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKey(userID)).getAccountForUpdate();
           *
           */
        AccountProfileDataBean profile = entityManager.find(AccountProfileDataBean.class, userID);
        AccountDataBean account = profile.getAccount();


        if (Log.doTrace()) Log.trace("TradeBean:logout(" + userID + ") success");
        account.logout();
    }

    public AccountDataBean register(
            String userID,
            String password,
            String fullname,
            String address,
            String email,
            String creditcard,
            BigDecimal openBalance)
            throws CreateException, Exception {

        AccountDataBean account = new AccountDataBean(0, 0, null, new Timestamp(System.currentTimeMillis()), openBalance, openBalance, null);
        AccountProfileDataBean profile = new AccountProfileDataBean(userID, password, fullname, address, email, creditcard);
        account.setProfile(profile);
        //are both of these necessary?
        profile.setAccount(account);
        //this should save the linked profile as well?
        entityManager.persist(account);
        //apparently doesn't??
        entityManager.persist(profile);
        return account;
    }

    public AccountDataBean getAccountData(String userID) {

        if (Log.doTrace())
            Log.trace("TradeBean:getAccountData", userID);

        /*  The following commented code shows alternative forms of the finder needed for this
           *  method
           *  The first alternative requires a 2-table join. Some database cannot allocate an Update
           *  Lock on a join select.
           *
           *  The second alternative shows the finder being executed without allocation an update
           *  lock on the row. Normally, an update lock would not be necessary, but is required if
           *  the same user logs in multiple times to avoid a deadlock situation.
           *
           *  The third alternative runs the finder and allocates an update lock on the row(s)
           *
              LocalAccount account = accountHome.findByUserID(userID);

              LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKey(userID)).getAccountForUpdate();
           */
        AccountProfileDataBean profile = entityManager.find(AccountProfileDataBean.class, userID);
        return profile.getAccount();
    }

    public AccountProfileDataBean getAccountProfileData(String userID)
            throws FinderException, Exception {
        if (Log.doTrace())
            Log.trace("TradeBean:getAccountProfileData", userID);

        /*  The following commented code shows alternative forms of the finder needed for this
           *  method
           *  The first alternative requires a 2-table join. Some database cannot allocate an Update
           *  Lock on a join select.
           *
           *  The second alternative shows the finder being executed without allocation an update
           *  lock on the row. Normally, an update lock would not be necessary, but is required if
           *  the same user logs in multiple times to avoid a deadlock situation.
           *
           *  The third alternative runs the finder and allocates an update lock on the row(s)
           *
              LocalAccount account = accountHome.findByUserID(userID);

              LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKey(userID)).getAccountForUpdate();
           */
        return entityManager.find(AccountProfileDataBean.class, userID);
    }

    public AccountProfileDataBean updateAccountProfile(AccountProfileDataBean accountProfileData)
            throws FinderException, Exception {

        if (Log.doTrace())
            Log.trace("TradeBean:updateAccountProfileData", accountProfileData);

        //TODO this might not be correct
        entityManager.merge(accountProfileData);
//        LocalAccount account = ((LocalAccountProfile) profileHome.findByPrimaryKeyForUpdate(accountProfileData.getUserID())).getAccountForUpdate();
//
//        accountProfileData =
//                account.updateAccountProfile(accountProfileData);
        return accountProfileData;
    }

    public RunStatsDataBean resetTrade(boolean deleteAll)
            throws Exception {
        if (Log.doTrace())
            Log.trace("TradeBean:resetTrade", deleteAll);

        //Clear MDB Statistics
        MDBStats.getInstance().reset();
        // Reset Trade
        return new org.apache.geronimo.samples.daytrader.direct.TradeDirect().resetTrade(deleteAll);
    }

    /**
     * provides a simple session method with no database access to test performance of a simple
     * path through a stateless session
     *
     * @param investment amount
     * @param NetValue   current value
     * @return return on investment as a percentage
     */
    public double investmentReturn(double investment, double NetValue) {
        if (Log.doTrace())
            Log.trace("TradeBean:investmentReturn");

        double diff = NetValue - investment;
        return diff / investment;

    }

    /**
     * This method provides a ping test for a 2-phase commit operation
     *
     * @param symbol to lookup
     * @return quoteData after sending JMS message
     */
    public QuoteDataBean pingTwoPhase(String symbol) {
        try {
            if (Log.doTrace()) Log.trace("TradeBean:pingTwoPhase", symbol);
            Connection conn = null;
            Session sess = null;
            try {

                //Get a Quote and send a JMS message in a 2-phase commit
                QuoteDataBean quote = entityManager.find(QuoteDataBean.class, symbol);

                conn = qConnFactory.createConnection();
                sess = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageProducer msgProducer = sess.createProducer(queue);
                TextMessage message = sess.createTextMessage();

                String command = "ping";
                message.setStringProperty("command", command);
                message.setLongProperty("publishTime", System.currentTimeMillis());
                message.setText("Ping message for queue java:comp/env/jms/TradeBrokerQueue sent from TradeSessionEJB:pingTwoPhase at " + new java.util.Date());

                msgProducer.send(message);
                return quote;
            } finally {
                if (conn != null)
                    conn.close();
                if (sess != null)
                    sess.close();
            }

        } catch (Exception e) {
            throw new EJBException(e.getMessage(), e);
        }
    }

    /* Required javax.ejb.SessionBean interface methods */

    public TradeJPA() {
    }

    private static boolean warnJMSFailure = true;

    public void ejbCreate() throws CreateException {
        try {

            if (Log.doTrace())
                Log.trace("TradeBean:ejbCreate  -- JNDI lookups of EJB and JMS resources");

            InitialContext ic = new InitialContext();

            publishQuotePriceChange = (Boolean) ic.lookup("java:comp/env/publishQuotePriceChange");
            boolean updateQuotePrices = (Boolean) ic.lookup("java:comp/env/updateQuotePrices");
            TradeConfig.setUpdateQuotePrices(updateQuotePrices);

            try {
                qConnFactory = (ConnectionFactory) ic.lookup("java:comp/env/jms/QueueConnectionFactory");
                tConnFactory = (ConnectionFactory) ic.lookup("java:comp/env/jms/TopicConnectionFactory");
                streamerTopic = (Topic) ic.lookup("java:comp/env/jms/TradeStreamerTopic");
                queue = (Queue) ic.lookup("java:comp/env/jms/TradeBrokerQueue");
            }
            catch (Exception e) {
                if (TradeJPA.warnJMSFailure) {
                    TradeJPA.warnJMSFailure = false;
                    Log.error("TradeBean:ejbCreate  Unable to lookup JMS Resources\n\t -- Asynchronous mode will not work correctly and Quote Price change publishing will be disabled", e);
                }
                publishQuotePriceChange = false;
            }

        } catch (Exception e) {
            Log.error("TradeBean:ejbCreate: Lookup of Local Entity Homes Failed\n" + e);
            e.printStackTrace();
            //UPDATE
            //throw new CreateException(e.toString());
        }

    }

    public void ejbRemove() {
        try {
            if (Log.doTrace())
                Log.trace("TradeBean:ejbRemove");
        }
        catch (Exception e) {
            Log.error(e, "Unable to close Queue or Topic connection on Session EJB remove");
        }
    }

    public void ejbActivate() {
    }

    public void ejbPassivate() {
    }

    public void setSessionContext(SessionContext sc) {
        context = sc;
        if (sc != null) {
        try {
            entityManager = (EntityManager) new InitialContext().lookup("java:comp/env/jpa/daytrader");
        } catch (NamingException e) {
            throw new EJBException("could not get Naming Context", e);
        }
        } else {
            entityManager = null;
        }

    }
}
