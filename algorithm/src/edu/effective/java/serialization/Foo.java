package edu.effective.java.serialization;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Foo extends AbstractFoo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		int x = s.readInt();
		int y = s.readInt();
		
		//manually deserialize and init super class state
		initialize(x,y);
	}
	
	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		
		s.writeInt(getX());
		s.writeInt(getY());
	}
	
	public Foo(int x, int y) {super(x,y);};
}

