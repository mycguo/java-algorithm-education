package edu.java.concurrent.ch3;

import java.util.concurrent.Semaphore;

/**
 * A channel which has no internal capacity
 * One put must followed by one take
 * @author CGuo
 *
 */
public class SynchronizedChannel {
	Object item = null;
	
	final Semaphore putPermit;
	final Semaphore takePermit;
	final Semaphore taken;
	
	public SynchronizedChannel() {
		putPermit = new Semaphore(1);
		takePermit = new Semaphore(0);
		taken = new Semaphore(0);
	}
	
	public void put(Object x) throws InterruptedException {
		putPermit.acquire();
		item = x;
		takePermit.release();
		
		//must wait for the signal from taker
		InterruptedException caught = null;
		for (;;){
			try {
				taken.acquire();
				break;
			} catch (InterruptedException ie) {
				caught = ie;
			}
		}
		
		if (caught != null)
			throw caught;
	}
	
	public Object get() throws InterruptedException {
		takePermit.acquire();
		Object x = item;
		item = null;
		putPermit.release();
		taken.release();
		
		return x;
		
	}

}
