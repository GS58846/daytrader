
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import javax.ejb.Local;
import javax.ejb.Remove;

@Local
public interface LocalSFTestLocal {
    public void addToArrayList(Object o);
    public Object getFirstItemFromArrayList();
    @Remove
    public void remove();
}
