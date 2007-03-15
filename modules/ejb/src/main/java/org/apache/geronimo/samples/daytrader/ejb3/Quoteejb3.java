package org.apache.geronimo.samples.daytrader.ejb3;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class Quoteejb3
 * 
 * @author Rob
 */
@Entity
@Table(name = "QUOTEEJB")
public class Quoteejb3 implements Serializable {

    @Column(name = "LOW")
    private BigDecimal low;

    @Column(name = "OPEN1")
    private BigDecimal open;

    @Column(name = "VOLUME", nullable = false)
    private double volume;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "HIGH")
    private BigDecimal high;

    @Column(name = "COMPANYNAME")
    private String companyName;

    @Id
    @Column(name = "SYMBOL", nullable = false)
    private String symbol;

    @Column(name = "CHANGE1", nullable = false)
    private double change;
    
    /** Creates a new instance of Quoteejb */
    public Quoteejb3() {
    }

    /**
     * Creates a new instance of Quoteejb with the specified values.
     * @param symbol the symbol of the Quoteejb
     */
    public Quoteejb3(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Creates a new instance of Quoteejb with the specified values.
     * @param symbol the symbol of the Quoteejb
     * @param volume the volume of the Quoteejb
     * @param change1 the change1 of the Quoteejb
     */
    public Quoteejb3(String symbol, double volume, double change1) {
        this.symbol = symbol;
        this.volume = volume;
        this.change = change1;
    }

    /**
     * Gets the low of this Quoteejb.
     * @return the low
     */
    public BigDecimal getLow() {
        return this.low;
    }

    /**
     * Sets the low of this Quoteejb to the specified value.
     * @param low the new low
     */
    public void setLow(BigDecimal low) {
        this.low = low;
    }

    /**
     * Gets the open1 of this Quoteejb.
     * @return the open1
     */
    public BigDecimal getOpen() {
        return this.open;
    }

    /**
     * Sets the open1 of this Quoteejb to the specified value.
     * @param open1 the new open1
     */
    public void setOpen(BigDecimal open1) {
        this.open = open1;
    }

    /**
     * Gets the volume of this Quoteejb.
     * @return the volume
     */
    public double getVolume() {
        return this.volume;
    }

    /**
     * Sets the volume of this Quoteejb to the specified value.
     * @param volume the new volume
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the price of this Quoteejb.
     * @return the price
     */
    public BigDecimal getPrice() {
        return this.price;
    }

    /**
     * Sets the price of this Quoteejb to the specified value.
     * @param price the new price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * Gets the high of this Quoteejb.
     * @return the high
     */
    public BigDecimal getHigh() {
        return this.high;
    }

    /**
     * Sets the high of this Quoteejb to the specified value.
     * @param high the new high
     */
    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    /**
     * Gets the companyname of this Quoteejb.
     * @return the companyname
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * Sets the companyname of this Quoteejb to the specified value.
     * @param companyname the new companyname
     */
    public void setCompanyName(String companyname) {
        this.companyName = companyname;
    }

    /**
     * Gets the symbol of this Quoteejb.
     * @return the symbol
     */
    public String getSymbol() {
        return this.symbol;
    }

    /**
     * Sets the symbol of this Quoteejb to the specified value.
     * @param symbol the new symbol
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Gets the change1 of this Quoteejb.
     * @return the change1
     */
    public double getChange() {
        return this.change;
    }

    /**
     * Sets the change1 of this Quoteejb to the specified value.
     * @param change1 the new change1
     */
    public void setChange(double change1) {
        this.change = change1;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.symbol != null ? this.symbol.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Quoteejb.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Quoteejb object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Quoteejb3)) {
            return false;
        }
        Quoteejb3 other = (Quoteejb3)object;
        if (this.symbol != other.symbol && (this.symbol == null || !this.symbol.equals(other.symbol))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "org.apache.geronimo.samples.daytrader.ejb3.Quoteejb3[symbol=" + symbol + "]";
    }
    
}
