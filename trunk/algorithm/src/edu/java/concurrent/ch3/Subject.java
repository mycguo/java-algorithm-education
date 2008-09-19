package edu.java.concurrent.ch3;

import java.util.ArrayList;

public class Subject {

	protected double val =0.0;
	final ArrayList<Observer> observers = new ArrayList<Observer>(); //should use CopyOnWriteArrayList
	
	public synchronized double getValue() { return val;};
	protected synchronized void setValue(long val) {
		this.val = val;
	}
	
	public void attach(Observer o) {
		observers.add(o);
	}
	public void detach(Observer o) {
		observers.remove(o);
	}
	
	public synchronized void changedValue( long value) {
		setValue(value);
		for (Observer o: observers) {
			o.changed(this);
		}
	}
	
}
