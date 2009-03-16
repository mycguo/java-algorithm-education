package edu.java.management.mbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

public class RMIAgent {
   private MBeanServer mbs = null;

   public RMIAgent() {

      // Get the platform MBeanServer
       mbs = ManagementFactory.getPlatformMBeanServer();

      // Unique identification of MBeans
      Hello helloBean = new Hello();
      ObjectName helloName = null;

      try {
         // Uniquely identify the MBeans and register them with the platform MBeanServer 
         helloName = new ObjectName("edu.java.management.mbean:name=hellothere");
         mbs.registerMBean(helloBean, helloName);
         
         JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/server");
         JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
         cs.start();

      } catch(Exception e) {
         e.printStackTrace();
      }
   }

   // Utility method: so that the application continues to run
   private static void waitForEnterPressed() {
      try {
         System.out.println("Press  to continue...");
         System.in.read();
      } catch (Exception e) {
         e.printStackTrace();
      }
    }

   public static void main(String argv[]) {
      RMIAgent agent = new RMIAgent();
      System.out.println("RMIAgent is running...");
      RMIAgent.waitForEnterPressed();
   }
}
