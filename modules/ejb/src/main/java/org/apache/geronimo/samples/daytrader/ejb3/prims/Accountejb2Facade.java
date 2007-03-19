package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.ejb3.Accountejb2;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class Accountejb2Facade implements Accountejb2FacadeLocal {

    @PersistenceContext
    private EntityManager em;
    
    /** Creates a new instance of Accountejb2Facade */
    public Accountejb2Facade() {
    }

    public void create(Accountejb2 accountejb2) {
        em.persist(accountejb2);
    }

    public void edit(Accountejb2 accountejb2) {
        em.merge(accountejb2);
    }

    public void destroy(Accountejb2 accountejb2) {
        em.merge(accountejb2);
        em.remove(accountejb2);
    }

    public Accountejb2 find(Object pk) {
        return (Accountejb2) em.find(Accountejb2.class, pk);
    }  

    public List findAll() {
        return em.createQuery("select object(o) from Accountejb2 as o").getResultList();
    }
    
}
