/**
 * QuoteDataBean.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package org.apache.geronimo.samples.daytrader;

public class QuoteDataBean  {
    private java.lang.String symbol;
    private java.lang.String companyName;
    private java.math.BigDecimal price;
    private java.math.BigDecimal open;
    private java.math.BigDecimal low;
    private java.math.BigDecimal high;
    private double change;
    private double volume;

    public QuoteDataBean() {
    }

    public java.lang.String getSymbol() {
        return symbol;
    }

    public void setSymbol(java.lang.String symbol) {
        this.symbol = symbol;
    }

    public java.lang.String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(java.lang.String companyName) {
        this.companyName = companyName;
    }

    public java.math.BigDecimal getPrice() {
        return price;
    }

    public void setPrice(java.math.BigDecimal price) {
        this.price = price;
    }

    public java.math.BigDecimal getOpen() {
        return open;
    }

    public void setOpen(java.math.BigDecimal open) {
        this.open = open;
    }

    public java.math.BigDecimal getLow() {
        return low;
    }

    public void setLow(java.math.BigDecimal low) {
        this.low = low;
    }

    public java.math.BigDecimal getHigh() {
        return high;
    }

    public void setHigh(java.math.BigDecimal high) {
        this.high = high;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
