package edu.java.concurrent.ch3;

public class Observer {

	protected double oldState;
	protected final Subject sub;
	
	public Observer(Subject s) {
		sub =s ;
		oldState = s.getValue();
		display();
	}
	public synchronized void changed(Subject s) {
		double oldStateVal = oldState;
		oldState = s.getValue();
		if (oldState != oldStateVal)
			display();

		
	}
	
	public void display() {
		//do something
		System.out.println("state: " + oldState);
	}
}
