
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import javax.ejb.Remote;
import javax.jms.Message;


/**
 * This is the business interface for LocalSLMDBTest enterprise bean.
 */
@Remote
public interface LocalSLMDBTestRemote {
    public String publishToTradeBrokerQueue();
}
