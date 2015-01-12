package org.apache.geronimo.daytrader.javaee6.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Compound primary key for ExternalAuthDataBean
 */
@Embeddable
public class ExternalAuthKey implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "PROVIDER")
    private ExternalAuthProvider provider;

    @Column(name = "TOKEN")
    private String token;

    public ExternalAuthKey(ExternalAuthProvider provider, String token) {
        this.provider = provider;
        this.token = token;
    }

    public ExternalAuthProvider getProvider() {
        return provider;
    }

    public void setProvider(ExternalAuthProvider provider) {
        this.provider = provider;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalAuthKey that = (ExternalAuthKey) o;

        if (provider != null ? !provider.equals(that.provider) : that.provider != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = provider != null ? provider.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ExternalAuthKey{" +
                "provider='" + provider + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
