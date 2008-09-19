package edu.java.concurrent.ch3;

public class SimpleTransBankAccount extends TransBankAccount{
	
	protected long balance=0;
	protected long workingBalance=0;
	protected Transaction tx = null;

	@Override
	public synchronized long  balance(Transaction t) throws Failure {
		if (t!=tx) throw new Failure();
		return workingBalance;
	}

	@Override
	public synchronized void deposit(Transaction t, long amount) throws Failure {
		if (t!=tx) throw new Failure();
		workingBalance +=amount;
		
	}

	@Override
	public synchronized void withdraw(Transaction t, long amount) throws Failure {
		deposit(t,-amount);
		
	}

	public synchronized void abort(Transaction t) {
		//
		if (t==tx)
			tx = null;
		
	}

	public synchronized boolean canCommit(Transaction t) {
		return t==tx;
	}

	public synchronized void commit(Transaction t) throws Failure {
		if (t!=tx) throw new Failure();
		balance = workingBalance;
		tx=null;
		
	}

	public boolean join(Transaction t) {
		if (tx !=null) return false;
		tx =t;
		workingBalance = balance;
		
		return true;
	}
	
	

}
