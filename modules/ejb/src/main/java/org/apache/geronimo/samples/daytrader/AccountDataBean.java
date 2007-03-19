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
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJBException;

import javax.persistence.*;

import org.apache.geronimo.samples.daytrader.util.Log;

@Entity(name = "accountejb")
@Table(name = "accountejb")
@NamedQueries( {
        @NamedQuery(name = "accountejb.findByCreationdate", query = "SELECT a FROM accountejb a WHERE a.creationdate = :creationdate"),
        @NamedQuery(name = "accountejb.findByOpenbalance", query = "SELECT a FROM accountejb a WHERE a.openbalance = :openbalance"),
        @NamedQuery(name = "accountejb.findByLogoutcount", query = "SELECT a FROM accountejb a WHERE a.logoutcount = :logoutcount"),
        @NamedQuery(name = "accountejb.findByBalance", query = "SELECT a FROM accountejb a WHERE a.balance = :balance"),
        @NamedQuery(name = "accountejb.findByAccountid", query = "SELECT a FROM accountejb a WHERE a.accountid = :accountid"),
        @NamedQuery(name = "accountejb.findByAccountid_eager", query = "SELECT a FROM accountejb a LEFT JOIN FETCH a.accountProfile WHERE a.accountid = :accountid"),
        @NamedQuery(name = "accountejb.findByAccountid_eagerholdings", query = "SELECT a FROM accountejb a LEFT JOIN FETCH a.holdings WHERE a.accountid = :accountid"),
        @NamedQuery(name = "accountejb.findByLastlogin", query = "SELECT a FROM accountejb a WHERE a.lastlogin = :lastlogin"),
        @NamedQuery(name = "accountejb.findByLogincount", query = "SELECT a FROM accountejb a WHERE a.logincount = :logincount")
    })
public class AccountDataBean implements Serializable {

    /* Accessor methods for persistent fields */
    @TableGenerator(
            name="accountIdGen",
            table="KEYGENEJB",
            pkColumnName="KEYNAME",
            valueColumnName="KEYVAL",
            pkColumnValue="account",
            allocationSize=1000)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE,
            generator="accountIdGen")
    @Column(nullable = false)
    private Integer accountID;         /* accountID */
    private int loginCount;     /* loginCount */
    private int logoutCount;     /* logoutCount */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogin;         /* lastLogin Date */
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;     /* creationDate */
    private BigDecimal balance;         /* balance */
    private BigDecimal openBalance;     /* open balance */
    @OneToMany(mappedBy = "account")
    private Collection<OrderDataBean> orders;
    @OneToMany(mappedBy = "account")
    private Collection<HoldingDataBean> holdings;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="PROFILE_USERID")
    private AccountProfileDataBean profile;
//    @Version
//    private Integer optLock;

    /* Accessor methods for relationship fields are only included for the AccountProfile profileID */
    @Transient
    private String profileID;

    public AccountDataBean() {
    }

    public AccountDataBean(Integer accountID,
            int loginCount,
            int logoutCount,
            Date lastLogin,
            Date creationDate,
            BigDecimal balance,
            BigDecimal openBalance,
            String profileID) {
        setAccountID(accountID);
        setLoginCount(loginCount);
        setLogoutCount(logoutCount);
        setLastLogin(lastLogin);
        setCreationDate(creationDate);
        setBalance(balance);
        setOpenBalance(openBalance);
        setProfileID(profileID);
    }

    public AccountDataBean(int loginCount,
            int logoutCount,
            Date lastLogin,
            Date creationDate,
            BigDecimal balance,
            BigDecimal openBalance,
            String profileID) {
        setLoginCount(loginCount);
        setLogoutCount(logoutCount);
        setLastLogin(lastLogin);
        setCreationDate(creationDate);
        setBalance(balance);
        setOpenBalance(openBalance);
        setProfileID(profileID);
    }

    public static AccountDataBean getRandomInstance() {
        return new AccountDataBean(new Integer(TradeConfig.rndInt(100000)), //accountID
                TradeConfig.rndInt(10000), //loginCount
                TradeConfig.rndInt(10000), //logoutCount
                new java.util.Date(), //lastLogin
                new java.util.Date(TradeConfig.rndInt(Integer.MAX_VALUE)), //creationDate
                TradeConfig.rndBigDecimal(1000000.0f), //balance
                TradeConfig.rndBigDecimal(1000000.0f), //openBalance
                TradeConfig.rndUserID() //profileID
        );
    }

    public String toString() {
        return "\n\tAccount Data for account: " + getAccountID()
                + "\n\t\t   loginCount:" + getLoginCount()
                + "\n\t\t  logoutCount:" + getLogoutCount()
                + "\n\t\t    lastLogin:" + getLastLogin()
                + "\n\t\t creationDate:" + getCreationDate()
                + "\n\t\t      balance:" + getBalance()
                + "\n\t\t  openBalance:" + getOpenBalance()
                + "\n\t\t    profileID:" + getProfileID()
                ;
    }

    public String toHTML() {
        return "<BR>Account Data for account: <B>" + getAccountID() + "</B>"
                + "<LI>   loginCount:" + getLoginCount() + "</LI>"
                + "<LI>  logoutCount:" + getLogoutCount() + "</LI>"
                + "<LI>    lastLogin:" + getLastLogin() + "</LI>"
                + "<LI> creationDate:" + getCreationDate() + "</LI>"
                + "<LI>      balance:" + getBalance() + "</LI>"
                + "<LI>  openBalance:" + getOpenBalance() + "</LI>"
                + "<LI>    profileID:" + getProfileID() + "</LI>"
                ;
    }

    public void print() {
        Log.log(this.toString());
    }

    /**
     * Gets the accountID
     *
     * @return Returns a Integer
     */
    public Integer getAccountID() {
        return accountID;
    }

    /**
     * Sets the accountID
     *
     * @param accountID The accountID to set
     */
    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    /**
     * Gets the loginCount
     *
     * @return Returns a int
     */
    public int getLoginCount() {
        return loginCount;
    }

    /**
     * Sets the loginCount
     *
     * @param loginCount The loginCount to set
     */
    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * Gets the logoutCount
     *
     * @return Returns a int
     */
    public int getLogoutCount() {
        return logoutCount;
    }

    /**
     * Sets the logoutCount
     *
     * @param logoutCount The logoutCount to set
     */
    public void setLogoutCount(int logoutCount) {
        this.logoutCount = logoutCount;
    }

    /**
     * Gets the lastLogin
     *
     * @return Returns a Date
     */
    public Date getLastLogin() {
        return lastLogin;
    }

    /**
     * Sets the lastLogin
     *
     * @param lastLogin The lastLogin to set
     */
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    /**
     * Gets the creationDate
     *
     * @return Returns a Date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Sets the creationDate
     *
     * @param creationDate The creationDate to set
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Gets the balance
     *
     * @return Returns a BigDecimal
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the balance
     *
     * @param balance The balance to set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Gets the openBalance
     *
     * @return Returns a BigDecimal
     */
    public BigDecimal getOpenBalance() {
        return openBalance;
    }

    /**
     * Sets the openBalance
     *
     * @param openBalance The openBalance to set
     */
    public void setOpenBalance(BigDecimal openBalance) {
        this.openBalance = openBalance;
    }

    /**
     * Gets the profileID
     *
     * @return Returns a String
     */
    public String getProfileID() {
        return profileID;
    }

    /**
     * Sets the profileID
     *
     * @param profileID The profileID to set
     */
    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    /**
     * Gets the profileID
     *
     * @return Returns a String
     */
    /* Disabled for D185273
     public String getUserID() {
         return getProfileID();
     }
     */
    public Collection<OrderDataBean> getOrders() {
        return orders;
    }

    public void setOrders(Collection<OrderDataBean> orders) {
        this.orders = orders;
    }
    
    public Collection<HoldingDataBean> getHoldings() {
        return holdings;
    }

    public void setHoldings(Collection<HoldingDataBean> holdings) {
        this.holdings = holdings;
    }

    public AccountProfileDataBean getProfile() {
        return profile;
    }

    public void setProfile(AccountProfileDataBean profile) {
        this.profile = profile;
    }

    public void login(String password) {
        AccountProfileDataBean profile = getProfile();
        if ((profile == null) || (profile.getPassword().equals(password) == false)) {
            String error = "AccountBean:Login failure for account: " + getAccountID() +
                    ((profile == null) ? "null AccountProfile" :
                            "\n\tIncorrect password-->" + profile.getUserID() + ":" + profile.getPassword());
            throw new EJBException(error);
        }

        setLastLogin(new Timestamp(System.currentTimeMillis()));
        setLoginCount(getLoginCount() + 1);
    }

    public void logout() {
        setLogoutCount(getLogoutCount() + 1);
    }

}