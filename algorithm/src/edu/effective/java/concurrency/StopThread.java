package edu.effective.java.concurrency;

import java.util.concurrent.TimeUnit;

public class StopThread {
	
	private static boolean stopRequested;
	
	private static synchronized void requestStop() {
		stopRequested = true;
	}
	private static synchronized boolean stopRequested() {
		return stopRequested;
	}
	
	public static void main(String args[]) throws InterruptedException {
		Thread backgroudThread = new Thread() {
			public void run() {
				int i =0;
				while (!stopRequested()) {
					i++;
					System.out.println(" Running: " + i);
				}
			}
		};
		
		backgroudThread.start();
		TimeUnit.SECONDS.sleep(1);
		requestStop();
		
	}

}
