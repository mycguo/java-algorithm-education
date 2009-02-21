package edu.java.concurrent.inpractice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class TestConcurrent {
	
	final ExecutorService pool = Executors.newCachedThreadPool();
	final BoundedBuffer<Integer> buff = new BoundedBuffer<Integer>(20);
	final AtomicInteger putSum = new AtomicInteger(0);
	final AtomicInteger getSum = new AtomicInteger(0);
	//assume
	int pairs =10;
	int runs =10;
	final CyclicBarrier barrier = new CyclicBarrier(2*pairs+1);

	@Test
	public void testBoundedBuffer() {
		final BoundedBuffer<Integer> bb = new BoundedBuffer<Integer>(10);
		
		Thread taker = new Thread() {
			public void run() {
				try {
					bb.take();
					fail("should never come here");
					
				} catch (InterruptedException e) {}
			}
		};
		
		try {
			taker.start();
			Thread.sleep(1000);
			taker.interrupt();
			taker.join(1000);
			assertFalse(taker.isAlive());
		} catch (Exception ee) {
			fail("unexpected happened");
		}
	}
	
	@Test
	public void testMultiThread() {

		
		try {
			for (int i=0; i<pairs ;i++) {
				pool.execute(new Producer());
				pool.execute(new Consumer());
				
			}
			int i = barrier.await(); //wait all to start
			System.out.println("All has started: " + i);

			int b = barrier.await();//wait all to finish
			System.out.println("All has finished: " + b);
			assertEquals(putSum.get(),getSum.get());
			System.out.println("Sum: " + putSum.get());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	class Consumer implements Runnable {
		public void run() {
			try {
				int a = barrier.await();
				System.out.println("count for consumer: " + a);
				int sum=0;
				for (int i=runs;i>0; --i) {
					sum += buff.take();
				}
				getSum.getAndAdd(sum);
				a = barrier.await();
				System.out.println("count for consumer after: " + a);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	class Producer implements Runnable {
		public void run( ) {
			try {
				int seed = (this.hashCode() ^ (int) System.nanoTime());
				int sum=0;
				int b = barrier.await();
				System.out.println("count for producer: " + b);
				for (int i=runs;i>0;i--) {
					buff.put(seed);
					sum += seed;
					seed = xorShift(seed);
				}
				putSum.getAndAdd(sum);
				barrier.await();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
	int xorShift(int y) {
		y ^=(y << 6);
		y ^= (y >>>21);
		y ^= (y << 7);
		return y;
	}
}
