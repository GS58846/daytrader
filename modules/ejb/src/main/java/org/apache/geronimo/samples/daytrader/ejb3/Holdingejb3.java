package org.apache.geronimo.samples.daytrader.ejb3;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "HOLDINGEJB")
public class Holdingejb3 implements Serializable {

    @Column(name = "PURCHASEPRICE")
    private BigDecimal purchasePrice;

    @Id
    @Column(name = "HOLDINGID", nullable = false)
    private Integer holdingID;

    @Column(name = "QUANTITY", nullable = false)
    private double quantity;

    @Column(name = "PURCHASEDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QUOTE_SYMBOL")
    private Quoteejb3 quote;
    
    @ManyToOne
    @JoinColumn(name="ACCOUNT_ACCOUNTID")
    private Accountejb3 account;
    
    /** Creates a new instance of Holdingejb */
    public Holdingejb3() {
    }

    /**
     * Creates a new instance of Holdingejb with the specified values.
     * @param holdingid the holdingid of the Holdingejb
     */
    public Holdingejb3(Integer holdingid) {
        this.holdingID = holdingid;
    }

    /**
     * Creates a new instance of Holdingejb with the specified values.
     * @param holdingid the holdingid of the Holdingejb
     * @param quantity the quantity of the Holdingejb
     */
    public Holdingejb3(Integer holdingid, double quantity) {
        this.holdingID = holdingid;
        this.quantity = quantity;
    }

    /**
     * Gets the purchaseprice of this Holdingejb.
     * @return the purchaseprice
     */
    public BigDecimal getPurchasePrice() {
        return this.purchasePrice;
    }

    /**
     * Sets the purchaseprice of this Holdingejb to the specified value.
     * @param purchaseprice the new purchaseprice
     */
    public void setPurchasePrice(BigDecimal purchaseprice) {
        this.purchasePrice = purchaseprice;
    }

    /**
     * Gets the holdingid of this Holdingejb.
     * @return the holdingid
     */
    public Integer getHoldingID() {
        return this.holdingID;
    }

    /**
     * Sets the holdingid of this Holdingejb to the specified value.
     * @param holdingid the new holdingid
     */
    public void setHoldingID(Integer holdingid) {
        this.holdingID = holdingid;
    }

    /**
     * Gets the quantity of this Holdingejb.
     * @return the quantity
     */
    public double getQuantity() {
        return this.quantity;
    }

    /**
     * Sets the quantity of this Holdingejb to the specified value.
     * @param quantity the new quantity
     */
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the purchasedate of this Holdingejb.
     * @return the purchasedate
     */
    public Date getPurchaseDate() {
        return this.purchaseDate;
    }

    /**
     * Sets the purchasedate of this Holdingejb to the specified value.
     * @param purchasedate the new purchasedate
     */
    public void setPurchaseDate(Date purchasedate) {
        this.purchaseDate = purchasedate;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.holdingID != null ? this.holdingID.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Holdingejb.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Holdingejb object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Holdingejb3)) {
            return false;
        }
        Holdingejb3 other = (Holdingejb3)object;
        if (this.holdingID != other.holdingID && (this.holdingID == null || !this.holdingID.equals(other.holdingID))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "org.apache.geronimo.samples.daytrader.ejb3.Holdingejb3[holdingid=" + holdingID + "]";
    }

    public Quoteejb3 getQuote() {
        return quote;
    }

    public void setQuote(Quoteejb3 quote) {
        this.quote = quote;
    }
    
}
