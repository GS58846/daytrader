
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import java.math.BigDecimal;
import java.util.Vector;
import javax.ejb.Remote;


/**
 * This is the business interface for RemoteSLTest enterprise bean.
 */
@Remote
public interface RemoteSLTestRemote {
    public String getPayload();

    public String getPayloadWithParameters(String payload, Integer number, Vector vect, BigDecimal bd);
    
    public String[] getMultiBeanLocalPayload();
}
