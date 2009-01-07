package edu.java.concurrent.channel;

import EDU.oswego.cs.dl.util.concurrent.BoundedLinkedQueue;
import EDU.oswego.cs.dl.util.concurrent.Channel;

public class ProducerCousumerChannel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Channel channel = new BoundedLinkedQueue(100);
		Producer p = new Producer(channel);
		Consummer c = new Consummer(channel);
		(new Thread(p)).start();
		(new Thread(c)).start();

	}

	private static class Producer implements Runnable {
		private Channel channal;

		Producer(Channel chan) {
			this.channal = chan;
		}

		public void run() {
			for (;;) {
				System.out.println("Putting object");
				try {
					channal.put(new Object());
				} catch (InterruptedException e) {
					System.exit(0);
				}
			}
		}
	}

	private static class Consummer implements Runnable {
		private Channel channal;

		Consummer(Channel chan) {
			this.channal = chan;
		}

		public void run() {
			for (;;) {
				System.out.println("Getting object");
				try {
					channal.take();
				} catch (InterruptedException e) {
					System.exit(0);
				}
			}
		}
	}
}
