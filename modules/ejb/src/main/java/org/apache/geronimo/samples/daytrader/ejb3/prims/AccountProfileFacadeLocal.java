package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.*;
import java.util.List;
import javax.ejb.Local;


@Local
public interface AccountProfileFacadeLocal {
    void create(AccountProfileDataBean profile);

    void edit(AccountProfileDataBean profile);

    void destroy(AccountProfileDataBean profile);

    AccountProfileDataBean find(Object pk);

    List findAll();
    
}
