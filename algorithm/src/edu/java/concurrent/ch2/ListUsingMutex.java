package edu.java.concurrent.ch2;

import java.lang.InterruptedException;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class ListUsingMutex {

	static class Node {
		Object item;
		Node next;
		Mutex lock = new Mutex();
		Node(Object x, Node n) {
			item =x;
			next =n;
		}
	}
	
	protected Node head;
	protected synchronized Node getHead() {return head;};
	
	public synchronized void adds(Object x) {
		head = new Node(x,head);
		
	}
	
	boolean search(Object x) throws InterruptedException {
		Node p = getHead();
		if ( p == null || x == null) return false;
		p.lock.acquire();
		
		for (;;) {
			Node nextp = null;
			boolean found;
			
			try {
				found = x.equals(p.item);
				if (!found) {
					nextp = p.next;
					if (nextp !=null) {
						try {
							nextp.lock.acquire();
						} catch (InterruptedException nie) {
							throw nie;
						}
					}
				}
			} finally {
				p.lock.release();
			}
			
			if (found) 
				return true;
			else if (nextp == null)
				return false;
			else
				p =nextp;
		}
	}
	
}
