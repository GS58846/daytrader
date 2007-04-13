package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class AccountProfileFacade implements AccountProfileFacadeLocal {

    @PersistenceContext(unitName="daytrader")
    private EntityManager em;
    
    /** Creates a new instance of AccountProfileDataBeanFacade */
    public AccountProfileFacade() {
    }

    public void create(AccountProfileDataBean profile) {
        em.persist(profile);
    }

    public void edit(AccountProfileDataBean profile) {
        em.merge(profile);
    }

    public void destroy(AccountProfileDataBean profile) {
        em.merge(profile);
        em.remove(profile);
    }

    public AccountProfileDataBean find(Object pk) {
        return (AccountProfileDataBean) em.find(AccountProfileDataBean.class, pk);
    }    

    public List findAll() {
        return em.createQuery("select object(o) from accountprofileejb as o").getResultList();
    }
    
}
