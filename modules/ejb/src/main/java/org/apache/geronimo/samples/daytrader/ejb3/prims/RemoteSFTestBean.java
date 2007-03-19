package org.apache.geronimo.samples.daytrader.ejb3.prims;

import java.util.ArrayList;
import javax.ejb.Remove;
import javax.ejb.Stateful;

@Stateful
public class RemoteSFTestBean implements org.apache.geronimo.samples.daytrader.ejb3.prims.RemoteSFTestRemote {
    
    ArrayList cache;
    
    /** Creates a new instance of RemoteSFTestBean */
    public RemoteSFTestBean() {
        cache = new ArrayList();
    }

    public void addToArrayList(Object o) {
        cache.add(o);
    }

    public Object getFirstItemFromArrayList() {
        if(cache.size()!=0){
            return cache.remove(cache.size()-1);
        } 
        return null;
    }
    
    @Remove
    public void remove(){
    }
    
}
