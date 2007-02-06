/**
 * TradeWSServices.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package org.apache.geronimo.samples.daytrader;

public interface TradeWSServices extends java.rmi.Remote {
    public org.apache.geronimo.samples.daytrader.MarketSummaryDataBeanWS getMarketSummary() throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.OrderDataBean buy(java.lang.String userID, java.lang.String symbol, double quantity, int orderProcessingMode) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.OrderDataBean sell(java.lang.String userID, java.lang.Integer holdingID, int orderProcessingMode) throws java.rmi.RemoteException;
    public void queueOrder(java.lang.Integer orderID, boolean twoPhase) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.OrderDataBean completeOrder(java.lang.Integer orderID, boolean twoPhase) throws java.rmi.RemoteException;
    public void cancelOrder(java.lang.Integer orderID, boolean twoPhase) throws java.rmi.RemoteException;
    public void orderCompleted(java.lang.String userID, java.lang.Integer orderID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.OrderDataBean[] getOrders(java.lang.String userID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.OrderDataBean[] getClosedOrders(java.lang.String userID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.QuoteDataBean createQuote(java.lang.String symbol, java.lang.String companyName, java.math.BigDecimal price) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.QuoteDataBean getQuote(java.lang.String symbol) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.QuoteDataBean[] getAllQuotes() throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.QuoteDataBean updateQuotePriceVolume(java.lang.String symbol, java.math.BigDecimal newPrice, double sharesTraded) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.HoldingDataBean[] getHoldings(java.lang.String userID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.HoldingDataBean getHolding(java.lang.Integer holdingID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.AccountDataBean getAccountData(java.lang.String userID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.AccountProfileDataBean getAccountProfileData(java.lang.String userID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.AccountProfileDataBean updateAccountProfile(org.apache.geronimo.samples.daytrader.AccountProfileDataBean profileData) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.AccountDataBean login(java.lang.String userID, java.lang.String password) throws java.rmi.RemoteException;
    public void logout(java.lang.String userID) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.AccountDataBean register(java.lang.String userID, java.lang.String password, java.lang.String fullname, java.lang.String address, java.lang.String email, java.lang.String creditcard, java.math.BigDecimal openBalance) throws java.rmi.RemoteException;
    public org.apache.geronimo.samples.daytrader.RunStatsDataBean resetTrade(boolean deleteAll) throws java.rmi.RemoteException;
}
