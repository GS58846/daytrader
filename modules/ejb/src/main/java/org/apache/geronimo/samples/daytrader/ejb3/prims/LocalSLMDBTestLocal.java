
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import javax.ejb.Local;
import javax.jms.Message;


/**
 * This is the business interface for LocalSLMDBTest enterprise bean.
 */
@Local
public interface LocalSLMDBTestLocal {
    public String publishToTradeBrokerQueue();
}
