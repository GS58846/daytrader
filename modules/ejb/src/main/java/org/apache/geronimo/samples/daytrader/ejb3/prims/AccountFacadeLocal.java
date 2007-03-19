package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.*;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AccountFacadeLocal {
    void create(AccountDataBean account);

    void edit(AccountDataBean account);

    void destroy(AccountDataBean account);

    AccountDataBean find(Object pk);
    
    AccountDataBean findByAccountID(Integer key, boolean lazy);

    List findAll();

    AccountDataBean findByAccountIDEagerHoldings(Integer key);
    
}
