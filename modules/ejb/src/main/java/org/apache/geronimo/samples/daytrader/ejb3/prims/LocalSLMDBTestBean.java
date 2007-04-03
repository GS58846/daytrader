package org.apache.geronimo.samples.daytrader.ejb3.prims;


import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.NamingException;

@Stateless
public class LocalSLMDBTestBean implements LocalSLMDBTestLocal, LocalSLMDBTestRemote {
   
    @Resource(name = "jms/TradeBrokerQCF")
    private ConnectionFactory queueConnectionFactory;
    
    @Resource(name = "jms/TradeBrokerQueue")
    private Queue tradeBrokerQueue;

    /** Creates a new instance of LocalSLMDBTestBean */
    public LocalSLMDBTestBean() {
    }
    
    public Message publishToTradeBrokerQueue() {
        Message message = null;
        
        try {
            message = this.sendJMSMessageToTestMDB("Hello World from TradeBrokerQueue!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return message;
    }
    
    
    private Message createJMSMessageForTestMDB(Session session, Object messageData) throws JMSException {
        javax.jms.TextMessage tm = session.createTextMessage();
        tm.setText(messageData.toString());
        return tm;
    }
    
    private Message sendJMSMessageToTestMDB(Object messageData) throws NamingException, JMSException {
        javax.jms.Connection connection = null;
        javax.jms.Session session = null;
        
        Message message = null;
        
        try {
            connection = queueConnectionFactory.createConnection();
            session = connection.createSession(false,javax.jms.Session.AUTO_ACKNOWLEDGE);
            message = createJMSMessageForTestMDB(session, messageData);
            javax.jms.MessageProducer messageProducer = session.createProducer(tradeBrokerQueue);
            messageProducer.send(message);
        } finally {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        
        return message;
    } 
}
