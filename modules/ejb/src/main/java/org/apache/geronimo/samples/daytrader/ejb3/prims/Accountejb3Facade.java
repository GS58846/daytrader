package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.ejb3.Accountejb3;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class Accountejb3Facade implements Accountejb3FacadeLocal {

    @PersistenceContext
    private EntityManager em;
    
    /** Creates a new instance of Accountejb3Facade */
    public Accountejb3Facade() {
    }

    public void create(Accountejb3 accountejb3) {
        em.persist(accountejb3);
    }

    public void edit(Accountejb3 accountejb3) {
        em.merge(accountejb3);
    }

    public void destroy(Accountejb3 accountejb3) {
        em.merge(accountejb3);
        em.remove(accountejb3);
    }

    public Accountejb3 find(Object pk) {
        return (Accountejb3) em.find(Accountejb3.class, pk);
    }    

    public List findAll() {
        return em.createQuery("select object(o) from Accountejb3 as o").getResultList();
    }
    
}
