package edu.java.concurrent.ch3;

public class BoundedCounterWithStateVariable {

	private static enum state {
		TOP,MIDDLE,BOTTOM;
	}
	private static enum boundary {
		LOW(0),HIGH(1000);
		private int limit;
		boundary(int l ) {
			limit =l;
		}
	}
	
	protected state initState = state.BOTTOM;
	protected long count = 0;
	
	protected void updateState() {
		state oldState = initState;
		if (count == boundary.LOW.limit)
			initState = state.BOTTOM;
		else if (count == boundary.HIGH.limit)
			initState = state.TOP;
		else
			initState = state.MIDDLE;
		
		if (oldState != initState)
			notifyAll();
		
	}
	
	public synchronized long count() {return count;};
	
	public synchronized void inc() throws InterruptedException {
		if (initState == state.TOP)
			wait();
		++count;
		updateState();
	}
	
	public synchronized void dec() throws InterruptedException {
		if (initState == state.BOTTOM)
			wait();
		--count;
		updateState();
	}
}
