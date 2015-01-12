package org.apache.geronimo.daytrader.javaee6.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "externalauthejb")
@Table(name = "externalauthejb")
public class ExternalAuthDataBean implements Serializable {

    @EmbeddedId
    private ExternalAuthKey externalAuthKey;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="PROFILE_USERID")
    private AccountProfileDataBean profile;

    public ExternalAuthDataBean(ExternalAuthKey externalAuthKey) {
        this.externalAuthKey = externalAuthKey;
    }

    public ExternalAuthKey getExternalAuthKey() {
        return externalAuthKey;
    }

    public void setExternalAuthKey(ExternalAuthKey externalAuthKey) {
        this.externalAuthKey = externalAuthKey;
    }

    public AccountProfileDataBean getProfile() {
        return profile;
    }

    public void setProfile(AccountProfileDataBean profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalAuthDataBean that = (ExternalAuthDataBean) o;

        if (externalAuthKey != null ? !externalAuthKey.equals(that.externalAuthKey) : that.externalAuthKey != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return externalAuthKey != null ? externalAuthKey.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ExternalAuthDataBean{" +
                "externalAuthKey=" + externalAuthKey +
                ", profile=" + profile +
                '}';
    }
}
