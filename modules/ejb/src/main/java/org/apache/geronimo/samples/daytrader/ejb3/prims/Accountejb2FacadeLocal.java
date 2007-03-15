package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.ejb3.Accountejb2;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rob
 */
@Local
public interface Accountejb2FacadeLocal {
    void create(Accountejb2 accountejb2);

    void edit(Accountejb2 accountejb2);

    void destroy(Accountejb2 accountejb2);

    Accountejb2 find(Object pk);

    List findAll();
    
}
