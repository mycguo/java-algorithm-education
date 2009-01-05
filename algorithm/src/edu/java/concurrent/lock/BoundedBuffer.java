package edu.java.concurrent.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedBuffer {
	Lock lock = new ReentrantLock();
	
	Condition notNull = lock.newCondition();
	Condition notFull = lock.newCondition();
	private Object[] buffer = new Object[100];

	int putCount, takeCount, count;
	
	public void put(Object o) throws InterruptedException {
		lock.lock();
		try {
			//buffer is full
			if ( count == buffer.length ) {
				System.out.println("buffer is full, waiting to be taken");
				notFull.await();
			}
			//it is not full
			buffer[putCount] = o;
			count++;
			if (++putCount == buffer.length  ) 
				putCount =0;
			System.out.println("Single not null, putting " + putCount);
			notNull.signal();
			
		} finally {
			lock.unlock();
		}
	}

	public Object get() throws InterruptedException {
		lock.lock();
		try {
			//check whether it is empty
			if (count ==0) {
				System.out.println("Buffer is null, waiting for new entry");
				notNull.await();
			}
			//not empty
			Object o = buffer[takeCount];
			++takeCount;
			if (takeCount == buffer.length )
				takeCount = 0;
			--count;
			System.out.println("Signal not empty, taking " + takeCount);
			notFull.signal();
			return o;
		
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] argc) {
		final BoundedBuffer b = new BoundedBuffer();

		
		Thread get = new Thread(new Runnable () {
			public void run() {
				while (true) {
				try {
					b.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
		});
		
		Thread put = new Thread(new Runnable () {
			public void run() {
				while (true) {
				try {
					b.put(new Object());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				}
			}
		});
		
		get.start();
		put.start();
	}
		

}
