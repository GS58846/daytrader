
package org.apache.geronimo.samples.daytrader.ejb3.prims;

import org.apache.geronimo.samples.daytrader.*;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Rob
 */
@Local
public interface QuoteFacadeLocal {
    void create(QuoteDataBean quote);

    void edit(QuoteDataBean quote);

    void destroy(QuoteDataBean quote);

    QuoteDataBean find(Object pk);

    List findAll();

    QuoteDataBean testForUpdateQuery(String symbol);
    
}
