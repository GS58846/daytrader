package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.ejb3.Accountejb3;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rob
 */
@Local
public interface Accountejb3FacadeLocal {
    void create(Accountejb3 accountejb3);

    void edit(Accountejb3 accountejb3);

    void destroy(Accountejb3 accountejb3);

    Accountejb3 find(Object pk);

    List findAll();
    
}
