package edu.java.concurrent.ch3;

public interface Transactor {

	//enter and return true, if possilbe
	public boolean join(Transaction t);
	
	public boolean canCommit(Transaction t) throws  Failure;
	
	public void commit(Transaction t) throws Failure;
	
	//NO excpetion here, ignore if inapplicable
	public void abort(Transaction t) ;
	
	
	static public class Failure extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;}
}
