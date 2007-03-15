
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import javax.ejb.Remote;
import javax.ejb.Remove;


/**
 * This is the business interface for RemoteSFTest enterprise bean.
 */
@Remote
public interface RemoteSFTestRemote {
    public void addToArrayList(Object o);
    public Object getFirstItemFromArrayList();
    @Remove
    public void remove();
}
