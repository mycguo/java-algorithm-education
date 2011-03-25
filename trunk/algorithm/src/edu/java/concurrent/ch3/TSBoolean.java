package edu.java.concurrent.ch3;

public class TSBoolean {

	private boolean value =false;
	public synchronized boolean testAndSet() {
		boolean oldValue = value;  ;
		value = true;
		return oldValue;
	}
	public synchronized void clear() {
		value = false;
	}
}
