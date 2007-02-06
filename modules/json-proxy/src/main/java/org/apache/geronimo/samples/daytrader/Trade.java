/**
 * Trade.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package org.apache.geronimo.samples.daytrader;

public interface Trade extends javax.xml.rpc.Service {
    public org.apache.geronimo.samples.daytrader.TradeWSServices getTradeWSServices() throws javax.xml.rpc.ServiceException;
    public java.lang.String getTradeWSServicesAddress();
    public org.apache.geronimo.samples.daytrader.TradeWSServices getTradeWSServices(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
