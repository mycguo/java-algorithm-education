package edu.java.concurrent.ch2;

import java.util.NoSuchElementException;

public class ExpandableArray {
	
	protected Object[] data;
	protected int size=0;
	
	public ExpandableArray(int cap) {
		data = new Object[cap];
	}
	
	public synchronized int size() {
		return size;
	}
	
	public synchronized Object get(int i) {
		if (i <0 || i>=size) {
			throw new NoSuchElementException();
		} else
			return data[i];
	}
	
	public synchronized void add(Object x) {
		if (size == data.length) {
			Object[] olddata = data;
			data = new Object[3*(size+1)/2];
			System.arraycopy(olddata, 0, data, 0, size);
		}
		data[size++] = x;
	}
	
	public synchronized void removeLast() {
		if (size == 0)
			throw new NoSuchElementException();
		data[--size] = null;
	}

}
