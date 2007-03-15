
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
public class RemoteSLTestBean implements RemoteSLTestRemote {

    @EJB
    private LocalSLTestLocal localSLTestBean;
    private String payload = "Welcome to JEE5 Remote SL Beans!";
    
    /** Creates a new instance of RemoteSLTestBean */
    public RemoteSLTestBean() {
    }

    public String getPayload() {
        return payload;
    }

    public String getPayloadWithParameters(String payload, Integer number, Vector vect, BigDecimal bd) {
        return this.payload;
    }

    public String[] getMultiBeanLocalPayload() {
        String[] i = new String[2];
        i[0] = payload;
        i[1] = localSLTestBean.getPayload();
        return i;
    }   
}
