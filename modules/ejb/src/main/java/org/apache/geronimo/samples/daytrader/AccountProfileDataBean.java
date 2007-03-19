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

import javax.persistence.*;

import org.apache.geronimo.samples.daytrader.util.Log;

@Entity(name = "accountprofileejb")
@Table(name = "accountprofileejb")
@NamedQueries( {
        @NamedQuery(name = "accountprofileejb.findByAddress", query = "SELECT a FROM accountprofileejb a WHERE a.address = :address"),
        @NamedQuery(name = "accountprofileejb.findByPasswd", query = "SELECT a FROM accountprofileejb a WHERE a.passwd = :passwd"),
        @NamedQuery(name = "accountprofileejb.findByUserid", query = "SELECT a FROM accountprofileejb a WHERE a.userid = :userid"),
        @NamedQuery(name = "accountprofileejb.findByEmail", query = "SELECT a FROM accountprofileejb a WHERE a.email = :email"),
        @NamedQuery(name = "accountprofileejb.findByCreditcard", query = "SELECT a FROM accountprofileejb a WHERE a.creditcard = :creditcard"),
        @NamedQuery(name = "accountprofileejb.findByFullname", query = "SELECT a FROM accountprofileejb a WHERE a.fullname = :fullname")
    })
public class AccountProfileDataBean
        implements java.io.Serializable {

    /* Accessor methods for persistent fields */

    @Id
    //@GeneratedValue
    @Column(nullable = false)
    private String userID;                /* userID */
    private String passwd;            /* password */
    private String fullName;            /* fullName */
    private String address;            /* address */
    private String email;                /* email */
    private String creditCard;            /* creditCard */
    @OneToOne(mappedBy="profile")
    private AccountDataBean account;
//    @Version
//    private Integer optLock;

    public AccountProfileDataBean() {
    }

    public AccountProfileDataBean(String userID,
            String password,
            String fullName,
            String address,
            String email,
            String creditCard) {
        setUserID(userID);
        setPassword(password);
        setFullName(fullName);
        setAddress(address);
        setEmail(email);
        setCreditCard(creditCard);
    }

    public static AccountProfileDataBean getRandomInstance() {
        return new AccountProfileDataBean(
                TradeConfig.rndUserID(),            // userID
                TradeConfig.rndUserID(),            // passwd
                TradeConfig.rndFullName(),            // fullname
                TradeConfig.rndAddress(),            // address
                TradeConfig.rndEmail(TradeConfig.rndUserID()), //email
                TradeConfig.rndCreditCard()          // creditCard
        );
    }

    public String toString() {
        return "\n\tAccount Profile Data for userID:" + getUserID()
                + "\n\t\t   passwd:" + getPassword()
                + "\n\t\t   fullName:" + getFullName()
                + "\n\t\t    address:" + getAddress()
                + "\n\t\t      email:" + getEmail()
                + "\n\t\t creditCard:" + getCreditCard()
                ;
    }

    public String toHTML() {
        return "<BR>Account Profile Data for userID: <B>" + getUserID() + "</B>"
                + "<LI>   passwd:" + getPassword() + "</LI>"
                + "<LI>   fullName:" + getFullName() + "</LI>"
                + "<LI>    address:" + getAddress() + "</LI>"
                + "<LI>      email:" + getEmail() + "</LI>"
                + "<LI> creditCard:" + getCreditCard() + "</LI>"
                ;
    }

    public void print() {
        Log.log(this.toString());
    }

    /**
     * Gets the userID
     *
     * @return Returns a String
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Sets the userID
     *
     * @param userID The userID to set
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Gets the passwd
     *
     * @return Returns a String
     */
    public String getPassword() {
        return passwd;
    }

    /**
     * Sets the passwd
     *
     * @param password The passwd to set
     */
    public void setPassword(String password) {
        this.passwd = password;
    }

    /**
     * Gets the fullName
     *
     * @return Returns a String
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the fullName
     *
     * @param fullName The fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Gets the address
     *
     * @return Returns a String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address
     *
     * @param address The address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the email
     *
     * @return Returns a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email
     *
     * @param email The email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the creditCard
     *
     * @return Returns a String
     */
    public String getCreditCard() {
        return creditCard;
    }

    /**
     * Sets the creditCard
     *
     * @param creditCard The creditCard to set
     */
    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public AccountDataBean getAccount() {
        return account;
    }

    public void setAccount(AccountDataBean account) {
        this.account = account;
    }
}
