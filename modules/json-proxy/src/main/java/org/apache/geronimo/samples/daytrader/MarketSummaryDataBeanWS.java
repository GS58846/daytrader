/**
 * MarketSummaryDataBeanWS.java
 *
 * This file was auto-generated from WSDL
 * by the IBM Web services WSDL2Java emitter.
 * o0526.04 v62905175048
 */

package org.apache.geronimo.samples.daytrader;

public class MarketSummaryDataBeanWS  {
    private java.math.BigDecimal TSIA;
    private java.math.BigDecimal openTSIA;
    private double volume;
    private org.apache.geronimo.samples.daytrader.QuoteDataBean[] topGainers;
    private org.apache.geronimo.samples.daytrader.QuoteDataBean[] topLosers;
    private java.util.Calendar summaryDate;

    public MarketSummaryDataBeanWS() {
    }

    public java.math.BigDecimal getTSIA() {
        return TSIA;
    }

    public void setTSIA(java.math.BigDecimal TSIA) {
        this.TSIA = TSIA;
    }

    public java.math.BigDecimal getOpenTSIA() {
        return openTSIA;
    }

    public void setOpenTSIA(java.math.BigDecimal openTSIA) {
        this.openTSIA = openTSIA;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public org.apache.geronimo.samples.daytrader.QuoteDataBean[] getTopGainers() {
        return topGainers;
    }

    public void setTopGainers(org.apache.geronimo.samples.daytrader.QuoteDataBean[] topGainers) {
        this.topGainers = topGainers;
    }

    public org.apache.geronimo.samples.daytrader.QuoteDataBean[] getTopLosers() {
        return topLosers;
    }

    public void setTopLosers(org.apache.geronimo.samples.daytrader.QuoteDataBean[] topLosers) {
        this.topLosers = topLosers;
    }

    public java.util.Calendar getSummaryDate() {
        return summaryDate;
    }

    public void setSummaryDate(java.util.Calendar summaryDate) {
        this.summaryDate = summaryDate;
    }

}
