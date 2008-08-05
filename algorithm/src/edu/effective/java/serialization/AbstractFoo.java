package edu.effective.java.serialization;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractFoo {

	private int x,y;
	
	//track initialization
	private enum State {NEW,INIT,DONE};
	private final AtomicReference<State> init = new AtomicReference<State>(State.NEW);
	
	public AbstractFoo(int x, int y) { initialize(x,y);}
	
	protected AbstractFoo() {};
	
	protected final void initialize(int x, int y) {
		if (!init.compareAndSet(State.NEW, State.INIT)) {
			throw new IllegalStateException(" already inited");
		}
		this.x = x;
		this.y = y;
		init.set(State.DONE);
	}
	
	protected final int getX() {
		checkInit();
		return x;
		
	}
	protected final int getY() {
		checkInit();
		return y;
		
	}
	private void checkInit() {
		if (init.get() != State.DONE) {
			throw new IllegalStateException(" not init yet");
		}
		
	}
	

}
