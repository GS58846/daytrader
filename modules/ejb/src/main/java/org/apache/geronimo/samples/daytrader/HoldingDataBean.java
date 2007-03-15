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

@Entity(name = "holdingejb")
@Table(name = "holdingejb")
@NamedQueries({
@NamedQuery(name = "holdingsByUserID",
        query = "SELECT h FROM holdingejb h where h.account.profile.userID = :userID")
        })
public class HoldingDataBean
        implements Serializable {

    /* persistent/relationship fields */

    @TableGenerator(
            name="holdingIdGen",
            table="KEYGENEJB",
            pkColumnName="KEYNAME",
            valueColumnName="KEYVAL",
            pkColumnValue="holding",
            allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE,
            generator="holdingIdGen")
    @Column(nullable = false)
    private Integer holdingID;            /* holdingID */
    private double quantity;            /* quantity */
    private BigDecimal purchasePrice;        /* purchasePrice */
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;        /* purchaseDate */
    @Transient
    private String quoteID;            /* Holding(*)  ---> Quote(1) */
    
    @ManyToOne
    @JoinColumn(name="ACCOUNT_ACCOUNTID")
    private AccountDataBean account;
    @ManyToOne
    @JoinColumn(name = "QUOTE_SYMBOL")
    private QuoteDataBean quote;

//    @Version
//    private Integer optLock;

    public HoldingDataBean() {
    }

    public HoldingDataBean(Integer holdingID,
            double quantity,
            BigDecimal purchasePrice,
            Date purchaseDate,
            String quoteID) {
        setHoldingID(holdingID);
        setQuantity(quantity);
        setPurchasePrice(purchasePrice);
        setPurchaseDate(purchaseDate);
        setQuoteID(quoteID);
    }

    public HoldingDataBean(double quantity,
            BigDecimal purchasePrice,
            Date purchaseDate,
            AccountDataBean account,
            QuoteDataBean quote) {
        setQuantity(quantity);
        setPurchasePrice(purchasePrice);
        setPurchaseDate(purchaseDate);
        setAccount(account);
        setQuote(quote);
    }

    public static HoldingDataBean getRandomInstance() {
        return new HoldingDataBean(
                new Integer(TradeConfig.rndInt(100000)),     //holdingID
                TradeConfig.rndQuantity(),                     //quantity
                TradeConfig.rndBigDecimal(1000.0f),             //purchasePrice
                new java.util.Date(TradeConfig.rndInt(Integer.MAX_VALUE)), //purchaseDate
                TradeConfig.rndSymbol()                        // symbol
        );
    }

    public String toString() {
        return "\n\tHolding Data for holding: " + getHoldingID()
                + "\n\t\t      quantity:" + getQuantity()
                + "\n\t\t purchasePrice:" + getPurchasePrice()
                + "\n\t\t  purchaseDate:" + getPurchaseDate()
                + "\n\t\t       quoteID:" + getQuoteID()
                ;
    }

    public String toHTML() {
        return "<BR>Holding Data for holding: " + getHoldingID() + "</B>"
                + "<LI>      quantity:" + getQuantity() + "</LI>"
                + "<LI> purchasePrice:" + getPurchasePrice() + "</LI>"
                + "<LI>  purchaseDate:" + getPurchaseDate() + "</LI>"
                + "<LI>       quoteID:" + getQuoteID() + "</LI>"
                ;
    }

    public void print() {
        Log.log(this.toString());
    }

    /**
     * Gets the holdingID
     *
     * @return Returns a Integer
     */
    public Integer getHoldingID() {
        return holdingID;
    }

    /**
     * Sets the holdingID
     *
     * @param holdingID The holdingID to set
     */
    public void setHoldingID(Integer holdingID) {
        this.holdingID = holdingID;
    }

    /**
     * Gets the quantity
     *
     * @return Returns a BigDecimal
     */
    public double getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity
     *
     * @param quantity The quantity to set
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the purchasePrice
     *
     * @return Returns a BigDecimal
     */
    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * Sets the purchasePrice
     *
     * @param purchasePrice The purchasePrice to set
     */
    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * Gets the purchaseDate
     *
     * @return Returns a Date
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * Sets the purchaseDate
     *
     * @param purchaseDate The purchaseDate to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * Gets the quoteID
     *
     * @return Returns symbol for associated quote
     */
    public String getQuoteID() {
        if (quote != null) {
            return quote.getSymbol();
        }
        return quoteID;
    }

    /**
     * Sets the quoteID
     *
     * @param quoteID The quoteID to set
     */
    public void setQuoteID(String quoteID) {
        this.quoteID = quoteID;
    }

    public AccountDataBean getAccount() {
        return account;
    }

    public void setAccount(AccountDataBean account) {
        this.account = account;
    }

    /**
     * Gets the quoteID
     *
     * @return Returns a Integer
     */
    /* Disabled for D185273
     public String getSymbol() {
         return getQuoteID();
     }
     */
    public QuoteDataBean getQuote() {
        return quote;
    }

    public void setQuote(QuoteDataBean quote) {
        this.quote = quote;
    }
}
