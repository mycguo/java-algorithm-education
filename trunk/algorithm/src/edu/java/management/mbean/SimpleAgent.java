package edu.java.management.mbean;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;

public class SimpleAgent {
   private MBeanServer mbs = null;
   private HtmlAdaptorServer adaptor;

   public SimpleAgent() {

      // Get the platform MBeanServer
       mbs = ManagementFactory.getPlatformMBeanServer();
       adaptor = new HtmlAdaptorServer();

      // Unique identification of MBeans
      HelloMBean helloBean = new Hello();
      ObjectName helloName = null;

      try {
         // Uniquely identify the MBeans and register them with the platform MBeanServer 
         helloName = new ObjectName("SimpleAgent:name=hellothere");
         mbs.registerMBean(helloBean, helloName);
         
         ObjectName adapterName = new ObjectName("SimpleAgent:name=htmladapter,port=8000");
         adaptor.setPort(8002);
         mbs.registerMBean(adaptor,adapterName);
         
         
         //adaptor.start();

      } catch(Exception e) {
         e.printStackTrace();
      }
   }

   public void start() {
	   adaptor.start();
   }

   public static void main(String argv[]) {
      SimpleAgent agent = new SimpleAgent();
      agent.start();
      System.out.println("SimpleAgent is running...");

   }
}
