package org.apache.geronimo.samples.daytrader.ejb3.prims;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(name = "TradeBrokerQueue", activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
    })
public class TestMDB implements MessageListener {
    
    /** Creates a new instance of TradeTestMDB */
    public TestMDB() {
    }

    public void onMessage(Message message) {
        System.out.println("TestMDB.onMessage: " + message.toString());
    }
}
