package edu.java.concurrent.lock;

import EDU.oswego.cs.dl.util.concurrent.Mutex;
import EDU.oswego.cs.dl.util.concurrent.Sync;

/*
 * http://gee.cs.oswego.edu/dl/classes/EDU/oswego/cs/dl/util/concurrent/Mutex.html
 */

public class HandInHandList {

	public static class Node {
		Node(Object x, Node n) {
			item = x;
			next = n;
		}

		Object item;
		Node next;
		Sync sync = new Mutex();
	}

	public HandInHandList() {
		head = null;
	}

	protected Node head;

	public void add(Object o) {
		head = new Node(o, head);
	}

	synchronized public Node getHead() {
		return this.head;
	}

	public boolean search(Object o) throws InterruptedException {
		Node p = getHead();
		if (p == null)
			return false;

		p.sync.acquire();

		for (;;) {

			if (o.equals(p.item)) {
				p.sync.release();
				return true;
			} else {
				Node nextP = p.next;
				if (nextP == null) {
					p.sync.release();
					return false;
				} else {
					try {
						nextP.sync.acquire();

					} catch (InterruptedException ie) {
						p.sync.release();
						throw ie;
					}
					p.sync.release();
					p = nextP;
				}
			}

		}

	}
}
