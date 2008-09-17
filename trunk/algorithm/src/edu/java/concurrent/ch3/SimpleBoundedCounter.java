package edu.java.concurrent.ch3;

public class SimpleBoundedCounter {

	protected final long MIN =0;
	protected final long MAX = 10000;
	protected long count = MIN;
	
	public synchronized long getCount() {return count ;};
	
	public synchronized void inc() throws InterruptedException {
		awaitUnderMax();
		setCount(count+1);
	}
	
	public synchronized void dec() throws InterruptedException {
		awaitOverMin();
		setCount(count-1);
	}
	
	protected void awaitOverMin() throws InterruptedException {
		while (count == MIN ) Thread.currentThread().wait();
	}
	
	protected void awaitUnderMax() throws InterruptedException {
		while (count == MAX) Thread.currentThread().wait();
	}
	
	protected void setCount(long count) {
		this.count = count;
		notifyAll();
	}
}
