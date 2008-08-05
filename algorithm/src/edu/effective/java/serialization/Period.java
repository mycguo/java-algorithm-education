package edu.effective.java.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Period implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Date start;
	private Date end;
	
	public Period(Date start, Date end) {
		this.start = start;
		this.end = end;
	}
	
	public Date start() {
		return new Date(start.getTime());
	}
	
	public Date end() {
		return new Date(end.getTime());
	}
	
	
	@Override
	public String toString() {
		return start + " - " + end;
	}
	
	private static class SerializationProxy implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final Date start;
		private final Date end;
		SerializationProxy(Period p) {
			this.start = p.start();
			this.end = p.end();
			
		}
		
		private Object readResolve() {
			return new Period(start,end);
		}
		

	}

	private Object writeReplace() {
		return new SerializationProxy(this);
	}
	
	private void readObject(ObjectInputStream s) throws InvalidObjectException {
		throw new InvalidObjectException(" proxy required");
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		Period p = new Period(new Date(), new Date());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);		
		out.writeObject(p);
		
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
		Period pp = (Period) in.readObject();
		
		System.out.println(pp);
		
		
		
		
	}
}
