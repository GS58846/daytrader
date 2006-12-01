package org.apache.geronimo.samples.daytrader.session;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Collection;

import org.apache.geronimo.samples.daytrader.*;
import org.apache.geronimo.samples.daytrader.direct.TradeDirect;
import org.apache.geronimo.samples.daytrader.util.*;

import javax.ejb.*;


public class TradeJDBCBean implements SessionBean {
	private SessionContext context = null;
	private TradeDirect tradeDirect = null;

	public TradeJDBCBean() {
	}

	public void ejbCreate() throws CreateException {
		if (Log.doTrace())
			Log.trace("TradeJDBCBean:ejbCreate");
	}

	public void ejbRemove() {
		try {
			if (Log.doTrace())
				Log.trace("TradeJDBCBean:ejbRemove");
		} catch (Exception e) {
			Log.error(e, "Unable to close Queue or Topic connection on Session EJB remove");
		}
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		this.context = sc;
	}

	public MarketSummaryDataBean getMarketSummary() throws Exception, RemoteException {
		return (new TradeDirect(true)).getMarketSummary();
	}


	public OrderDataBean buy(String userID, String symbol, double quantity, int orderProcessingMode) throws Exception, RemoteException {
		return (new TradeDirect(true)).buy(userID, symbol, quantity, orderProcessingMode);
	}

	public OrderDataBean sell(String userID, Integer holdingID, int orderProcessingMode) throws Exception, RemoteException {
		return (new TradeDirect(true)).sell(userID, holdingID, orderProcessingMode);
	}

	public void queueOrder(Integer orderID, boolean twoPhase) throws Exception, RemoteException {
		(new TradeDirect(true)).queueOrder(orderID, twoPhase);
	}

	public OrderDataBean completeOrder(Integer orderID, boolean twoPhase) throws Exception, RemoteException {
		return (new TradeDirect(true)).completeOrder(orderID, twoPhase);
	}

	public void cancelOrder(Integer orderID, boolean twoPhase) throws Exception, RemoteException {
		(new TradeDirect(true)).cancelOrder(orderID, twoPhase);
	}

	public void orderCompleted(String userID, Integer orderID) throws Exception, RemoteException {
		(new TradeDirect(true)).orderCompleted(userID, orderID);
	}

	public Collection getOrders(String userID) throws Exception, RemoteException {
		return (new TradeDirect(true)).getOrders(userID);
	}

	public Collection getClosedOrders(String userID) throws Exception, RemoteException {
		return (new TradeDirect(true)).getClosedOrders(userID);
	}

	public QuoteDataBean createQuote(String symbol, String companyName, BigDecimal price) throws Exception, RemoteException {
		return (new TradeDirect(true)).createQuote(symbol, companyName, price);
	}

	public QuoteDataBean getQuote(String symbol) throws Exception, RemoteException {
		return (new TradeDirect(true)).getQuote(symbol);
	}

	public Collection getAllQuotes() throws Exception, RemoteException {
		return (new TradeDirect(true)).getAllQuotes();
	}

	public QuoteDataBean updateQuotePriceVolume(String symbol, BigDecimal newPrice, double sharesTraded) throws Exception, RemoteException {
		return (new TradeDirect(true)).updateQuotePriceVolume(symbol, newPrice, sharesTraded);
	}

	public Collection getHoldings(String userID) throws Exception, RemoteException {
		return (new TradeDirect(true)).getHoldings(userID);
	}

	public HoldingDataBean getHolding(Integer holdingID) throws Exception, RemoteException {
		return (new TradeDirect(true)).getHolding(holdingID);
	}

	public AccountDataBean getAccountData(String userID) throws javax.ejb.FinderException, Exception {
		return (new TradeDirect(true)).getAccountData(userID);
	}

	public AccountProfileDataBean getAccountProfileData(String userID) throws Exception, RemoteException {
		return (new TradeDirect(true)).getAccountProfileData(userID);
	}

	public AccountProfileDataBean updateAccountProfile(AccountProfileDataBean profileData) throws Exception, RemoteException {
		return (new TradeDirect(true)).updateAccountProfile(profileData);
	}

	public AccountDataBean login(String userID, String password) throws Exception, RemoteException {
		return (new TradeDirect(true)).login(userID, password);
	}

	public void logout(String userID) throws Exception, RemoteException {
		(new TradeDirect(true)).logout(userID);
	}

	public AccountDataBean register(String userID, String password, String fullname, String address, String email, String creditcard, BigDecimal openBalance) throws Exception, RemoteException {
		return (new TradeDirect(true)).register(userID, password, fullname, address, email, creditcard, openBalance);
	}

	public RunStatsDataBean resetTrade(boolean deleteAll) throws Exception, RemoteException {
		return (new TradeDirect(true)).resetTrade(deleteAll);
	}
}
