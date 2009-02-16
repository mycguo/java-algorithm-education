package edu.java.concurrent.inpractice;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Barrier {

	private final Board mainBoard;
	private final CyclicBarrier barrier;
	private final Worker[] workers;
	
	public Barrier(Board board) {
		this.mainBoard = board;
		int count = Runtime.getRuntime().availableProcessors();
		this.barrier = new CyclicBarrier(count, new Runnable() {
			public void run() {
				mainBoard.commitNewValues();
			};
		});
		this.workers = new Worker[count];
		for (int i=0; i< count; i++) {
			workers[i] = new Worker(mainBoard.getSubBoard(count,i));
		}
	}
	
	public void start() {
		for (Worker w: workers) {
			new Thread(w).start();
		}
		mainBoard.waitForCovergence();
	}
	
	private class Worker implements Runnable {
		private final Board board;
		public Worker (Board board) {this.board = board;}
		public void run() {
			while (!board.hasConverged()) {
				//doSomething complicated
				board.computeNewValue();
				try {
					barrier.await();
				} catch (InterruptedException e) {
					return;
				} catch(BrokenBarrierException ex) {
					return;
				}
			}
		}
	}
	
	public class Board {

		public void commitNewValues() {
			// TODO Auto-generated method stub
			
		}

		public void waitForCovergence() {
			// TODO Auto-generated method stub
			
		}

		public void computeNewValue() {
			// TODO Auto-generated method stub
			
		}

		public boolean hasConverged() {
			// TODO Auto-generated method stub
			return false;
		}

		public Board getSubBoard(int count, int i) {
			// TODO Auto-generated method stub
			return null;
		}
	
	};
}
