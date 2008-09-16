package edu.java.concurrent.ch2;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ExpandableArrayWithVersion extends ExpandableArray {
	protected int version = 0;

	public ExpandableArrayWithVersion(int cap) {
		super(cap);
	}
	@Override
	public synchronized void add(Object x) {
		super.add(x);
		++version;
	}
	@Override
	public synchronized void removeLast() {
		super.removeLast();
		++version;
	}
	public Iterator<Object> getIterator() {
		return new EAIterator();
	}
	
	protected class EAIterator implements Iterator<Object> {

		protected final int currentVersion;
		protected int currentIndex =0;
		
		EAIterator() {
			currentVersion = version;
		}
		public boolean hasNext() {
			synchronized(ExpandableArrayWithVersion.this) {
				return (currentIndex < size);
			}
			
		}

		public Object next() {
			synchronized(ExpandableArrayWithVersion.this) {
				if (currentVersion != version)
					throw new ConcurrentModificationException();
				else if (currentIndex==size)
					throw new NoSuchElementException();
				else
					return data[currentIndex++];
			}
		}

		public void remove() {
			synchronized(ExpandableArrayWithVersion.this) {
				if (currentVersion != version)
					throw new ConcurrentModificationException();
				else if (currentIndex==size)
					throw new NoSuchElementException();
				else
					data[currentIndex--] = null;
			}
		}
		
	};
	

	
	

}
