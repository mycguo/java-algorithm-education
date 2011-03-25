package edu.java.concurrent.ch3;

import java.util.Hashtable;

public class Inventory {

	protected final Hashtable<String,Object> items = new Hashtable<String,Object>();
	final Hashtable<String,Object> suppliers = new Hashtable<String,Object>();
	
	int storing =0;
	int retrieving =0;
	
	private void doStore(String des, Object item, String supplier) {
		items.put(des,item);
		suppliers.put(supplier, des);
	}
	private Object doRetrive(String des) {
		Object o = items.get(des);
		if ( o != null)
			items.remove(des);
		return o;
	}
	
	public void store(String des,Object item, String supplier) throws InterruptedException {
		synchronized(this) {           //PRE dno't conflict with retrieving
			while (retrieving != 0)
				wait();
			++storing;
		}
		try {
			doStore(des,item,supplier);
		} finally {
			synchronized(this) {
				if (--storing ==0)         //when storing is zero, others can retrieve 
					notifyAll();
			}
		}
		
		
	}
	
	public Object retrive(String des) throws InterruptedException {
		//no others is retriving
		synchronized(this) {
			while (retrieving !=0)
				wait();
			++retrieving;
		}
		
		try {
			Object o = doRetrive(des);  //ground action
			return o;
		} finally {
			synchronized(this) {   //notify others if no one is retrieing
				if (--retrieving ==0)
					notifyAll();
			}
		}
	}
	
}
