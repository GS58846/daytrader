/**
 * HoldingDataBean.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package org.apache.geronimo.samples.daytrader;

public class HoldingDataBean  {
    private java.lang.Integer holdingID;
    private double quantity;
    private java.math.BigDecimal purchasePrice;
    private java.util.Calendar purchaseDate;
    private java.lang.String quoteID;

    public HoldingDataBean() {
    }

    public java.lang.Integer getHoldingID() {
        return holdingID;
    }

    public void setHoldingID(java.lang.Integer holdingID) {
        this.holdingID = holdingID;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public java.math.BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(java.math.BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public java.util.Calendar getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(java.util.Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public java.lang.String getQuoteID() {
        return quoteID;
    }

    public void setQuoteID(java.lang.String quoteID) {
        this.quoteID = quoteID;
    }

}
