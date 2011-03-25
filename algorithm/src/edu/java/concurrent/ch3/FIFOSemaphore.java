package edu.java.concurrent.ch3;


public class FIFOSemaphore extends Semaphore {

	protected final WaitQueue queue = new WaitQueue(); //dummy
	
	public FIFOSemaphore(long initPermits) {
		super(initPermits);
	}
	
	protected static class WaitQueue {
		public void enq(WaitNode node) {
			//
		}
		public WaitNode deq() {
			//
			return new WaitNode();
		}
	}
	
	protected static class WaitNode {
		boolean released = false;
		WaitNode next = null;
		
		synchronized void doWait() throws InterruptedException {
			try { 
				while(!released)
					wait();
			} catch (InterruptedException ie) {
				if (!released) {  //interrupted before notify
					released = true;
					throw ie;
					
				}
				else {  //interrutped after notify
					Thread.currentThread().interrupt(); //ignore exception but progpage status
				}
			}
		}
		
		synchronized boolean doNotify() { //return true if notified
			if (released)
				return false; //was interrupted or time out
			else {
				released = true;
				notify();
				return true;
			}
		}
		
		synchronized boolean doTimeWait(long msecs) {
			//simliar
			return false;
		}
	}

	
	public void acquire() throws InterruptedException {
		if (Thread.interrupted())
			throw new InterruptedException();
		
		WaitNode node = null;
		synchronized (this) {
			if (permits >0) { 
				--permits;
				return;
			} else {
				node = new WaitNode();
				queue.enq(node);
			}
		}
	
	}
	
	public synchronized void release() {
		for (;;) {
			WaitNode node = queue.deq();
			
			if (node == null) {
				++permits;
				return;
			} else if (node.doNotify())
				return;
		}
	}
}
