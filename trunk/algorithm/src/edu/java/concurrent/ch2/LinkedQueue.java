package edu.java.concurrent.ch2;

public class LinkedQueue {

	public static class Node {
		Object object;
		Node next = null;
		
		Node(Object o) {
			this.object = o;
		}
	}
	
	
	protected Node head = new Node(null);
	protected Node last = head;
	
	protected final Object pollLock = new Object();
	protected final Object addLock = new Object();
	
	public void put(Object  o) {
		synchronized (addLock) {
			Node n = new Node(o);
			synchronized(last) {
				last.next = n;
				last = n;
			}
			
		}
	}
	
	public Object poll() {
		synchronized(pollLock) {
			synchronized(head) {
				Object x = null;
				Node first = head.next;
				if (first !=null) {
					x =first.object;
					first.object = null;
					head = first;
				}
				return x;
				
			}
		}
	}
	
}

