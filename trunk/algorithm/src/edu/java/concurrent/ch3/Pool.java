package edu.java.concurrent.ch3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.Semaphore;

public class Pool {

	ArrayList<Object> items = new ArrayList<Object>();
	HashSet<Object> busy = new HashSet<Object>();
	
	protected final Semaphore s;

	public Pool(int n) {
		s = new Semaphore(n);
		initializeItems(n);
	}
	
	public Object getItem() throws InterruptedException {
		s.acquire();
		return doGet();
	}
	
	synchronized Object doGet() {
		Object x  = items.remove(items.size()-1);
		busy.add(x);
		return x;
	}
	
	public void release(Object x) {
		if (doReturn(x))
			s.release();
	}
	
	synchronized boolean doReturn(Object x) {
		if (busy.remove(x)) {
			items.add(x);
			return true;
		} else
			return false;
	}
	
	void initializeItems(int n) {
		//
	}
}
