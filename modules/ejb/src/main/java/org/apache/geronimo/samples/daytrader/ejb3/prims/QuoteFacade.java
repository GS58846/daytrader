
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.*;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class QuoteFacade implements QuoteFacadeLocal {

    @PersistenceContext
    private EntityManager em;
    
    /** Creates a new instance of QuoteDataBeanFacade */
    public QuoteFacade() {
    }

    public void create(QuoteDataBean quote) {
        em.persist(quote);
    }

    public void edit(QuoteDataBean quote) {
        em.merge(quote);
    }

    /*
     *TODO: why does this entity detach?
     */
    public void destroy(QuoteDataBean quote) {
        QuoteDataBean temp = em.merge(quote);
        em.remove(temp);
    }

    public QuoteDataBean find(Object pk) {
        return (QuoteDataBean) em.find(QuoteDataBean.class, pk);
    }

    public List findAll() {
        return em.createQuery("select object(o) from quoteejb as o").getResultList();
    }
    
    public QuoteDataBean testForUpdateQuery(String symbol){
        Query q = em.createNamedQuery("quoteejb.quoteForUpdate");
        q.setParameter(1,symbol);
        QuoteDataBean temp = (QuoteDataBean) q.getResultList().get(0);
        temp.setCompanyName("test_"+System.currentTimeMillis());
        return em.merge(temp);
    }
    
}
