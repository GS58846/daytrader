package org.apache.geronimo.samples.daytrader.ejb3.prims;

import java.math.BigDecimal;
import java.util.Vector;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author jstecher
 */
@Stateless
public class LocalSLTestBean implements LocalSLTestLocal {

    @EJB
    private RemoteSLTestRemote remoteSLTestBean;
    private String payload = "Welcome to JEE5 Local SL Beans!";
    /** Creates a new instance of LocalSLTestBean */
    public LocalSLTestBean() {
    }

    public String getPayload() {
        return payload;
    }

    public String getPayloadWithParameters(String payload, Integer number, Vector vect, BigDecimal bd) {
        return this.payload;
    }
    
    public String[] getMultiBeanRemotePayload() {
        String[] i = new String[2];
        i[0] = payload;
        i[1] = remoteSLTestBean.getPayload();
        return i;
    }  
}
