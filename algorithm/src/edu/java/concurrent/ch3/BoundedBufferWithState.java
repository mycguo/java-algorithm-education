package edu.java.concurrent.ch3;

public class BoundedBufferWithState {

	protected final Object[] array;
	protected  int putPtr = 0;
	protected  int getPtr = 0;
	protected int usedSlots =0;
	
	public BoundedBufferWithState(int cap) {
		array = new Object[cap];
	}
	
	public synchronized int size() {
		return usedSlots;
	}
	
	public int capacity() {
		return array.length;
	}
	
	public synchronized void put(Object x) throws InterruptedException {
		while (usedSlots == array.length) 
			wait();
		array[putPtr] =x;
		putPtr = (putPtr +1) % array.length;
		if (usedSlots++ ==0)
			notifyAll();
		
	}
	
	public synchronized Object take() throws InterruptedException {
		while (usedSlots == 0)
			wait();
		Object o = array[getPtr];
		array[getPtr] = null;
		getPtr = (getPtr + 1) % array.length;
		if (usedSlots-- == array.length)
			notifyAll();
		
		return o;
	}
	
}
