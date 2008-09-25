package edu.java.concurrent.ch3;

public class Semaphore {

	protected long permits;
	
	public Semaphore(long init) {
		permits = init;
	}
	
	public synchronized void release() {
		++permits;
		notify();
	}
	
	public void acquire() throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException();
		}
		
		synchronized(this) {
			try {
				while (permits < 0) 
					wait();
				--permits;
			} catch (InterruptedException e) {
				notify();
				throw e;
			}
		}
	}
	
	public boolean attempt(long msecs) throws InterruptedException {
		if (Thread.interrupted())
			throw new InterruptedException();
		//simliar to acquire, just add time-out
		synchronized (this) {
			
		}
		
		return false;
	}
}
