package edu.java.concurrent.inpractice;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class OneShotLatch {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		final OneShotLatch latch = new OneShotLatch();
		System.out.println("wait here");
		Thread t1 = new Thread() {
			public void run() {
				try {
					latch.await();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		};

		System.out.println("signal here");
		Thread t2 = new Thread() {
			public void run() {
				latch.signal();
			}
		};
		t1.start();
		t2.start();
		System.out.println("over");
	}
	
	
	private final Sync sync = new Sync();
	
	public void signal() {
		sync.releaseShared(0);
	}
	public void await() throws InterruptedException {
		sync.acquireSharedInterruptibly(0);
	}
	
	private class Sync extends AbstractQueuedSynchronizer {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5975379995208321693L;

		protected int tryAcquireShared(int ignored) {
			//succeed if latch is open (state==1), else fail
			return (getState()==1?1:-1);
		}
		

		protected boolean tryReleaseShared(int ignore) {
			setState(1); //latch is open
			return true;
		}
	}

}
