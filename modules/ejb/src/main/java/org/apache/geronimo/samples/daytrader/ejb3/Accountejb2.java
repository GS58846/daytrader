package org.apache.geronimo.samples.daytrader.ejb3;

import org.apache.geronimo.samples.daytrader.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "ACCOUNTEJB")
public class Accountejb2 implements Serializable {

    @Column(name = "CREATIONDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Column(name = "OPENBALANCE")
    private BigDecimal openBalance;

    @Column(name = "LOGOUTCOUNT", nullable = false)
    private int logoutCount;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Id
    @Column(name = "ACCOUNTID", nullable = false)
    private Integer accountID;

    @Column(name = "LASTLOGIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;

    @Column(name = "LOGINCOUNT", nullable = false)
    private int loginCount;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PROFILE_USERID")
    private AccountProfileDataBean profile;
    
    @OneToMany(mappedBy = "account", fetch=FetchType.EAGER)
    private Collection<Holdingejb2> holdings;
    
    /** Creates a new instance of Accountejb */
    public Accountejb2() {
    }

    /**
     * Creates a new instance of Accountejb with the specified values.
     * @param accountid the accountid of the Accountejb
     */
    public Accountejb2(Integer accountid) {
        this.accountID = accountid;
    }

    /**
     * Creates a new instance of Accountejb with the specified values.
     * @param accountid the accountid of the Accountejb
     * @param logoutcount the logoutcount of the Accountejb
     * @param logincount the logincount of the Accountejb
     */
    public Accountejb2(Integer accountid, int logoutcount, int logincount) {
        this.accountID = accountid;
        this.logoutCount = logoutcount;
        this.loginCount = logincount;
    }

    /**
     * Gets the creationdate of this Accountejb.
     * @return the creationdate
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Sets the creationdate of this Accountejb to the specified value.
     * @param creationdate the new creationdate
     */
    public void setCreationDate(Date creationdate) {
        this.creationDate = creationdate;
    }

    /**
     * Gets the openbalance of this Accountejb.
     * @return the openbalance
     */
    public BigDecimal getOpenBalance() {
        return this.openBalance;
    }

    /**
     * Sets the openbalance of this Accountejb to the specified value.
     * @param openbalance the new openbalance
     */
    public void setOpenbalance(BigDecimal openbalance) {
        this.openBalance = openbalance;
    }

    /**
     * Gets the logoutcount of this Accountejb.
     * @return the logoutcount
     */
    public int getLogoutCount() {
        return this.logoutCount;
    }

    /**
     * Sets the logoutcount of this Accountejb to the specified value.
     * @param logoutcount the new logoutcount
     */
    public void setLogoutcount(int logoutcount) {
        this.logoutCount = logoutcount;
    }

    /**
     * Gets the balance of this Accountejb.
     * @return the balance
     */
    public BigDecimal getBalance() {
        return this.balance;
    }

    /**
     * Sets the balance of this Accountejb to the specified value.
     * @param balance the new balance
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Gets the accountid of this Accountejb.
     * @return the accountid
     */
    public Integer getAccountID() {
        return this.accountID;
    }

    /**
     * Sets the accountid of this Accountejb to the specified value.
     * @param accountid the new accountid
     */
    public void setAccountID(Integer accountid) {
        this.accountID = accountid;
    }

    /**
     * Gets the lastlogin of this Accountejb.
     * @return the lastlogin
     */
    public Date getLastLogin() {
        return this.lastLogin;
    }

    /**
     * Sets the lastlogin of this Accountejb to the specified value.
     * @param lastlogin the new lastlogin
     */
    public void setLastLogin(Date lastlogin) {
        this.lastLogin = lastlogin;
    }

    /**
     * Gets the logincount of this Accountejb.
     * @return the logincount
     */
    public int getLoginCount() {
        return this.loginCount;
    }

    /**
     * Sets the logincount of this Accountejb to the specified value.
     * @param logincount the new logincount
     */
    public void setLoginCount(int logincount) {
        this.loginCount = logincount;
    }

    /**
     * Returns a hash code value for the object.  This implementation computes 
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.accountID != null ? this.accountID.hashCode() : 0);
        return hash;
    }

    /**
     * Determines whether another object is equal to this Accountejb.  The result is 
     * <code>true</code> if and only if the argument is not null and is a Accountejb object that 
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accountejb2)) {
            return false;
        }
        Accountejb2 other = (Accountejb2)object;
        if (this.accountID != other.accountID && (this.accountID == null || !this.accountID.equals(other.accountID))) return false;
        return true;
    }

    /**
     * Returns a string representation of the object.  This implementation constructs 
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "org.apache.geronimo.samples.daytrader.ejb3.Accountejb2[accountid=" + accountID + "]";
    }

    public AccountProfileDataBean getProfile() {
        return profile;
    }

    public void setProfile(AccountProfileDataBean accountProfile) {
        this.profile = accountProfile;
    }

    public Collection<Holdingejb2> getHoldings() {
        return holdings;
    }

    public void setHoldings(Collection<Holdingejb2> holdings2) {
        this.holdings = holdings2;
    }
    
}
