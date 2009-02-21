package edu.java.concurrent.inpractice;

import java.util.concurrent.Semaphore;

public class BoundedBuffer<E> {

	private final Semaphore avilableSpace, itemsSpace;
	private final E[] items;
	private int putPosition =0, takePosition =0;
	
	@SuppressWarnings("unchecked")
	public BoundedBuffer(int capacity) {
		avilableSpace = new Semaphore(capacity);
		itemsSpace= new Semaphore(0);
		items = (E[]) new Object[capacity];
	}
	
	public boolean isEmpty() {
		return avilableSpace.availablePermits() ==0;
	}
	
	public boolean isFull() {
		return itemsSpace.availablePermits()==0;
	}
	
	public void put(E x) throws InterruptedException {
		avilableSpace.acquire();
		doInsert(x);
		itemsSpace.release();
		
	}
	
	public E take() throws InterruptedException {
		itemsSpace.acquire();
		E item =doExtract();
		avilableSpace.release();
		return item;
	}

	private synchronized E  doExtract() throws InterruptedException {
		int i = takePosition;
		E item = items[i];
		items[i] = null;
		takePosition = (++i==items.length?0:i);
		return item;
	}

	private synchronized void doInsert(E x) {
		int i = putPosition;
		items[i] = x;
		putPosition = (++i==items.length? 0:i);
		
	}
}
