package org.apache.geronimo.samples.daytrader.ejb3.prims;

import java.util.ArrayList;
import javax.ejb.Remove;
import javax.ejb.Stateful;


@Stateful
public class LocalSFTestBean implements org.apache.geronimo.samples.daytrader.ejb3.prims.LocalSFTestLocal {
    
    ArrayList cache;
    
    /** Creates a new instance of LocalSFTestBean */
    public LocalSFTestBean() {
        cache = new ArrayList();
    }

    public void addToArrayList(Object o) {
        cache.add(o);
    }

    public Object getFirstItemFromArrayList() {
        if(cache.size()!=0){
            return cache.get(0);
        } 
        return null;
    }
    
    @Remove
    public void remove(){
    }   
}
