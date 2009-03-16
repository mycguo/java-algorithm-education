package edu.java.management.mbean;

import javax.management.AttributeChangeNotification;
import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class Hello extends NotificationBroadcasterSupport implements HelloMBean {
	   private String message = null;

	   public Hello() {
	      message = "Hello there";
	   }

	   public Hello(String message) {
	      this.message = message;
	   }

	   public void setMessage(String message) {
		  String oldMessage = getMessage();
	      this.message = message;
	      
	      Notification n = 
	            new AttributeChangeNotification(this, 
						    sequenceNumber++, 
						    System.currentTimeMillis(), 
						    "CacheSize changed", 
						    "message", 
						    "String", 
						    oldMessage, 
						    this.message); 
	 
		sendNotification(n); 

	   }
	   
	    @Override 
	    public MBeanNotificationInfo[] getNotificationInfo() { 
	        String[] types = new String[] { 
	            AttributeChangeNotification.ATTRIBUTE_CHANGE 
	        }; 
	        String name = AttributeChangeNotification.class.getName(); 
	        String description = "An attribute of this MBean has changed"; 
	        MBeanNotificationInfo info = 
	            new MBeanNotificationInfo(types, name, description); 
	        return new MBeanNotificationInfo[] {info}; 
	    } 
	 


	   public String getMessage() {
	      return message;
	   }

	   public void sayHello() {
	      System.out.println(message);
	   }
	   
	   int sequenceNumber =0;
	}
