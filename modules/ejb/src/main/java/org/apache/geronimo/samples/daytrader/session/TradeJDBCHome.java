package org.apache.geronimo.samples.daytrader.session;

import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface TradeJDBCHome extends EJBHome {
 
    TradeJDBC create() throws RemoteException, CreateException;

}

