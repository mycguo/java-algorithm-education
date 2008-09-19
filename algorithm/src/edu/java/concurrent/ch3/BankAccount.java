package edu.java.concurrent.ch3;

public class BankAccount {

	protected long balance = 0;
	
	public synchronized void deposit(long amount) throws Exception {
		if ((balance+amount) <0)
			throw new Exception("negavie amount");
		else
			balance += amount;		
	}
	
	public void withdraw(long amount) throws Exception {
		deposit(-amount);
	}
	
}
