package edu.java.concurrent.ch4;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

public class FutureResult {
	protected Object value = null;
	protected boolean ready = false;
	protected InvocationTargetException exception = null;
	
	public synchronized Object get() throws InterruptedException, InvocationTargetException {
		while (!ready)
			wait();
		if (exception !=null)
			throw exception;
		else
			return value;
	}
	
	public Runnable setter(final Callable<Object> function) {
		return new Runnable()
				{
					public void run() {
						try {
							set(function.call());
						} catch (Throwable t) {
							setException(t);
						}
					}
				};
	}
	
	synchronized void set(Object result) {
		value = result;
		ready = true;
		notifyAll();
	}
	
	public void setException(Throwable t) {
		exception = new InvocationTargetException(t);
		ready=true;
		notifyAll();
	}
	
}
