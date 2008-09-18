package edu.java.concurrent.ch3;

public class GamePlayer implements Runnable {

	public void run() {
		try {
			for (;;) {
				awaitTurn();
				move();
				releaseTurn();
			}
		} catch (InterruptedException ie) {
			// die
		}
	}

	protected GamePlayer other;
	protected boolean myTurn = false;

	protected synchronized void awaitTurn() throws InterruptedException {
		while (!myTurn)
			wait();
	}

	protected void move() throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " is moving");
		Thread.sleep(1000);
	}

	protected void releaseTurn() {
		GamePlayer p;
		synchronized (this) {
			myTurn = false;
			p = other;
		}
		p.myTurn();
	}

	protected synchronized void myTurn() {
		myTurn = true;
		notify();
	}
	
	synchronized void setOther(GamePlayer p) {
		other = p;
	}

	public static void main(String[] args) {
		GamePlayer one = new GamePlayer();
		GamePlayer two = new GamePlayer();
		one.setOther(two);
		two.setOther(one);
		one.myTurn();
		Thread t1 = new Thread(one);
		t1.setName("PlayerOne");
		t1.start();
		Thread t2 = new Thread(two);
		t2.setName("PlayerTwo");
		t2.start();
		
	}
}
