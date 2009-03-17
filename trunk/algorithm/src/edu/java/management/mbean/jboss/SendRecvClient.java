package edu.java.management.mbean.jboss;

import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class SendRecvClient
{
    static Logger log;

    QueueConnection conn;
    QueueSession session;
    Queue que;
    
    public static class ExListener 
        implements MessageListener
    {
        public void onMessage(Message msg)
        {
            TextMessage tm = (TextMessage) msg;
            try {
                log.info("onMessage, recv text=" + tm.getText());
            } catch(Throwable t) {
                t.printStackTrace();
            }
        }
    }
    
    public void setupPTP()
        throws JMSException, 
               NamingException
    {
        InitialContext iniCtx = new InitialContext();
        Object tmp = iniCtx.lookup("ConnectionFactory");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        que = (Queue) iniCtx.lookup("queue/testQueue");
        session = conn.createQueueSession(false,
                                          QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }
    
    public void sendRecvAsync(String text)
        throws JMSException, 
               NamingException
    {
        log.info("Begin sendRecvAsync");
        // Setup the PTP connection, session
        setupPTP();

        // Set the async listener
        QueueReceiver recv = session.createReceiver(que);
        recv.setMessageListener(new ExListener());

        // Send a text msg
        QueueSender send = session.createSender(que);
        TextMessage tm = session.createTextMessage(text);
        send.send(tm);
        log.info("sendRecvAsync, sent text=" + tm.getText());
        send.close();
        log.info("End sendRecvAsync");
    }
    
    public void stop()
        throws JMSException
    {
        conn.stop();
        session.close();
        conn.close();
    }
    
    public static void main(String args[]) 
        throws Exception
    {
        log = Logger.getLogger("SendRecvClient");
        
        log.info("Begin SendRecvClient, now=" + System.currentTimeMillis());
        SendRecvClient client = new SendRecvClient();
        client.sendRecvAsync("A text msg");
        client.stop();
        log.info("End SendRecvClient");
        System.exit(0);
    }
}
