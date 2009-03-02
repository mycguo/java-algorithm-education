package edu.java.concurrent.inpractice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SemaphoreOnLock {

	private ReentrantLock lock = new ReentrantLock();
	private Condition count = lock.newCondition();
	private int permit;

	public SemaphoreOnLock(int permit) {
		//is this really necessary?
		lock.lock();
		try {
			this.permit = permit;
		} finally {
			lock.unlock();
		}
	}

	// might block
	public void acquire() throws InterruptedException {
		try {
			lock.lock();
			//notice here, should use while instead of if
			//because of the possibilities of missed signal or walked up on different monitor
			while (permit <= 0) {
				count.await();
				permit--;
			}
		} finally {
			lock.unlock();
		}

	}

	public void release() {
		lock.lock();
		try {
			if (permit > 0) {
				permit++;
				count.signal();
			}

		} finally {
			lock.unlock();
		}
	}
}
