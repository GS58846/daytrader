package org.apache.geronimo.daytrader.javaee6.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Compound primary key for ExternalAuthDataBean
 */
@Embeddable
public class ExternalAuthKey implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER")
    private ExternalAuthProvider provider;

    @Column(name = "UID")
    private String uid;

    public ExternalAuthKey(ExternalAuthProvider provider, String uid) {
        this.provider = provider;
        this.uid = uid;
    }

    public ExternalAuthProvider getProvider() {
        return provider;
    }

    public void setProvider(ExternalAuthProvider provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalAuthKey that = (ExternalAuthKey) o;

        if (provider != null ? !provider.equals(that.provider) : that.provider != null) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = provider != null ? provider.hashCode() : 0;
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExternalAuthKey{" +
                "provider='" + provider + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
