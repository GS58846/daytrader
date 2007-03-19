
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import java.math.BigDecimal;
import java.util.Vector;
import javax.ejb.Local;


/**
 * This is the business interface for LocalSLTest enterprise bean.
 */
@Local
public interface LocalSLTestLocal {
    public String getPayload();
    
    public String getPayloadWithParameters(String payload, Integer number, Vector vect, BigDecimal bd);
    
    public String[] getMultiBeanRemotePayload();
}
