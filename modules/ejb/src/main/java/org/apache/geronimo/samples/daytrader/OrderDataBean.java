/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.geronimo.samples.daytrader;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.apache.geronimo.samples.daytrader.util.Log;


@Entity(name="orderejb")
@Table(name = "orderejb")
@NamedQueries({
         @NamedQuery(name="closedOrders",
         query="SELECT o FROM orderejb o WHERE " +
        "                    o.orderStatus = 'closed' AND " +
        "                    o.account.profile.userID  = :userID"),
         @NamedQuery(name="completeClosedOrders",
         query="UPDATE orderejb o SET o.orderStatus = 'completed' WHERE " +
        "                    o.orderStatus = 'closed' AND " +
        "                    o.account.profile.userID  = :userID")
        })
public class OrderDataBean implements Serializable
{

    @TableGenerator(
            name="orderIdGen",
            table="KEYGENEJB",
            pkColumnName="KEYNAME",
            valueColumnName="KEYVAL",
            pkColumnValue="order",
            allocationSize=1000)
   @Id
   @GeneratedValue(strategy=GenerationType.TABLE,
            generator="orderIdGen")
    @Column(nullable = false)        
    private Integer		orderID;			/* orderID */
    private String		orderType;			/* orderType (buy, sell, etc.) */
    private String		orderStatus;		/* orderStatus (open, processing, completed, closed, cancelled) */
    @Temporal(TemporalType.TIMESTAMP)
    private Date		openDate;			/* openDate (when the order was entered) */
    @Temporal(TemporalType.TIMESTAMP)
    private Date		completionDate;		/* completionDate */
    private double	quantity;			/* quantity */
    private BigDecimal	price;				/* price */
    private BigDecimal	orderFee;			/* price */
    @ManyToOne
    @JoinColumn(name="ACCOUNT_ACCOUNTID")
    private AccountDataBean account;
    @ManyToOne
    @JoinColumn(name="QUOTE_SYMBOL")
    private QuoteDataBean quote;
    @OneToOne
    @JoinColumn(name = "HOLDING_HOLDINGID")
    private HoldingDataBean holding;
//    @Version
//    private Integer optLock;


    /* Fields for relationship fields are not kept in the Data Bean */
    @Transient
    private String 		symbol;

    public OrderDataBean() {}
    /**
     * OrderDataBean
     */
    public OrderDataBean(Integer orderID,
                            String orderType,
                            String orderStatus,
                            Date openDate,
                            Date completionDate,
                            double quantity,
                            BigDecimal price,
                            BigDecimal orderFee,
                            String symbol
                            )
    {
        setOrderID(orderID);
        setOrderType(orderType);
        setOrderStatus(orderStatus);
        setOpenDate(openDate);
        setCompletionDate(completionDate);
        setQuantity(quantity);
        setPrice(price);
        setOrderFee(orderFee);
        setSymbol(symbol);
    }
    public OrderDataBean(String orderType,
            String orderStatus,
            Date openDate,
            Date completionDate,
            double quantity,
            BigDecimal price,
            BigDecimal orderFee,
            AccountDataBean account,
            QuoteDataBean quote, HoldingDataBean holding)
    {
        setOrderType(orderType);
        setOrderStatus(orderStatus);
        setOpenDate(openDate);
        setCompletionDate(completionDate);
        setQuantity(quantity);
        setPrice(price);
        setOrderFee(orderFee);
        setAccount(account);
        setQuote(quote);
        setHolding(holding);
    }

    public static OrderDataBean getRandomInstance() {
        return new OrderDataBean(
            new Integer(TradeConfig.rndInt(100000)),
            TradeConfig.rndBoolean() ? "buy" : "sell",
            "open",
            new java.util.Date(TradeConfig.rndInt(Integer.MAX_VALUE)),
            new java.util.Date(TradeConfig.rndInt(Integer.MAX_VALUE)),
            TradeConfig.rndQuantity(),
            TradeConfig.rndBigDecimal(1000.0f),
            TradeConfig.rndBigDecimal(1000.0f),
            TradeConfig.rndSymbol()
        );
    }

    public String toString()
    {
        return "Order " + getOrderID()
                + "\n\t      orderType: " + getOrderType()
                + "\n\t    orderStatus: " +	getOrderStatus()
                + "\n\t       openDate: " +	getOpenDate()
                + "\n\t completionDate: " +	getCompletionDate()
                + "\n\t       quantity: " +	getQuantity()
                + "\n\t          price: " +	getPrice()
                + "\n\t       orderFee: " +	getOrderFee()
                + "\n\t         symbol: " +	getSymbol()
                ;
    }
    public String toHTML()
    {
        return "<BR>Order <B>" + getOrderID() + "</B>"
                + "<LI>      orderType: " + getOrderType() + "</LI>"
                + "<LI>    orderStatus: " +	getOrderStatus() + "</LI>"
                + "<LI>       openDate: " +	getOpenDate() + "</LI>"
                + "<LI> completionDate: " +	getCompletionDate() + "</LI>"
                + "<LI>       quantity: " +	getQuantity() + "</LI>"
                + "<LI>          price: " +	getPrice() + "</LI>"
                + "<LI>       orderFee: " +	getOrderFee() + "</LI>"
                + "<LI>         symbol: " +	getSymbol() + "</LI>"
                ;
    }

    public void print()
    {
        Log.log( this.toString() );
    }

    /**
     * Gets the orderID
     * @return Returns a Integer
     */
    public Integer getOrderID() {
        return orderID;
    }
    /**
     * Sets the orderID
     * @param orderID The orderID to set
     */
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }


    /**
     * Gets the orderType
     * @return Returns a String
     */
    public String getOrderType() {
        return orderType;
    }
    /**
     * Sets the orderType
     * @param orderType The orderType to set
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }


    /**
     * Gets the orderStatus
     * @return Returns a String
     */
    public String getOrderStatus() {
        return orderStatus;
    }
    /**
     * Sets the orderStatus
     * @param orderStatus The orderStatus to set
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     * Gets the openDate
     * @return Returns a Date
     */
    public Date getOpenDate() {
        return openDate;
    }
    /**
     * Sets the openDate
     * @param openDate The openDate to set
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }


    /**
     * Gets the completionDate
     * @return Returns a Date
     */
    public Date getCompletionDate() {
        return completionDate;
    }
    /**
     * Sets the completionDate
     * @param completionDate The completionDate to set
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }


    /**
     * Gets the quantity
     * @return Returns a BigDecimal
     */
    public double getQuantity() {
        return quantity;
    }
    /**
     * Sets the quantity
     * @param quantity The quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }


    /**
     * Gets the price
     * @return Returns a BigDecimal
     */
    public BigDecimal getPrice() {
        return price;
    }
    /**
     * Sets the price
     * @param price The price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    /**
     * Gets the orderFee
     * @return Returns a BigDecimal
     */
    public BigDecimal getOrderFee() {
        return orderFee;
    }
    /**
     * Sets the orderFee
     * @param orderFee The orderFee to set
     */
    public void setOrderFee(BigDecimal orderFee) {
        this.orderFee = orderFee;
    }

    /**
     * Gets the symbol
     * @return Returns a String
     */
    public String getSymbol() {
        if (quote != null) {
            return quote.getSymbol();
        }
        return symbol;
    }
    /**
     * Sets the symbol
     * @param symbol The symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public AccountDataBean getAccount() {
        return account;
    }

    public void setAccount(AccountDataBean account) {
        this.account = account;
    }

    public QuoteDataBean getQuote() {
        return quote;
    }

    public void setQuote(QuoteDataBean quote) {
        this.quote = quote;
    }

    public HoldingDataBean getHolding() {
        return holding;
    }

    public void setHolding(HoldingDataBean holding) {
        this.holding = holding;
    }

    public boolean isBuy()
    {
    	String orderType = getOrderType();
    	if ( orderType.compareToIgnoreCase("buy") == 0 )
    		return true;
    	return false;
    }

    public boolean isSell()
    {
    	String orderType = getOrderType();
    	if ( orderType.compareToIgnoreCase("sell") == 0 )
    		return true;
    	return false;
    }

    public boolean isOpen()
    {
    	String orderStatus = getOrderStatus();
    	if ( (orderStatus.compareToIgnoreCase("open") == 0) ||
	         (orderStatus.compareToIgnoreCase("processing") == 0) )
	    		return true;
    	return false;
    }

    public boolean isCompleted()
    {
    	String orderStatus = getOrderStatus();
    	if ( (orderStatus.compareToIgnoreCase("completed") == 0) ||
	         (orderStatus.compareToIgnoreCase("alertcompleted") == 0)    ||
	         (orderStatus.compareToIgnoreCase("cancelled") == 0) )
	    		return true;
    	return false;
    }

    public boolean isCancelled()
    {
    	String orderStatus = getOrderStatus();
    	if (orderStatus.compareToIgnoreCase("cancelled") == 0)
	    		return true;
    	return false;
    }


	public void cancel()
	{
		setOrderStatus("cancelled");
	}

}

