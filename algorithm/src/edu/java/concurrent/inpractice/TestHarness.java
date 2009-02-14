package edu.java.concurrent.inpractice;

import java.util.concurrent.CountDownLatch;
/**
 * Page 94, Chapter 5.5.1 in Java Concurrency in Practice
 * @author CGuo
 *
 */
public class TestHarness {

	public long timeTaskes(int nThreads, final Runnable task)
			throws InterruptedException {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);

		for (int i = 0; i < nThreads; i++) {
			Thread t = new Thread() {
				public void run() {
					try {
						startGate.await();
						try {
							task.run();
						} finally {
							endGate.countDown();
						}
					} catch (InterruptedException e) {
					}
					;
				}

			};
			t.start();

		}

		long start = System.nanoTime();
		startGate.countDown();
		endGate.await();
		long end = System.nanoTime();
		return end - start;

	}
}
