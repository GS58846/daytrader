package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AccountFacade implements AccountFacadeLocal {

    @PersistenceContext(unitName="daytrader")
    private EntityManager em;
    
    /** Creates a new instance of AccountDataBeanFacade */
    public AccountFacade() {
    }

    public void create(AccountDataBean account) {
        em.persist(account);
    }

    public void edit(AccountDataBean account) {
        em.merge(account);
    }

    public void destroy(AccountDataBean account) {
        em.merge(account);
        em.remove(account);
    }

    public AccountDataBean find(Object pk) {
        return (AccountDataBean) em.find(AccountDataBean.class, pk);
    }    

    public List findAll() {
        return em.createQuery("select object(o) from accountejb as o").getResultList();
    }

    public AccountDataBean findByAccountID(Integer key, boolean lazy){
        Query q = null;
        if (lazy) {
            q = em.createNamedQuery("accountejb.findByAccountid");    
        } else {
            q = em.createNamedQuery("accountejb.findByAccountid_eager");    
        }
        
        q.setParameter("accountid", key);
        
        return (AccountDataBean)q.getSingleResult();
    }        
    
    
    public AccountDataBean findByAccountIDEagerHoldings(Integer key){
        Query q = null;
        q = em.createNamedQuery("accountejb.findByAccountid_eagerholdings");    
        
        q.setParameter("accountid", key);
        
        List l = q.getResultList();
        AccountDataBean ae = (AccountDataBean)l.get(0);
        
        return ae;
    }    
    
}
