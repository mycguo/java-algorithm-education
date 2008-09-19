package edu.java.concurrent.ch3;

public class ATSavingAccount extends BankAccount {

	ATCheckingAccount checking;
	long maxTranser;
	long balance;
	
	public ATSavingAccount(long max) {
		maxTranser = max;
	}
	
	synchronized void intChecking(ATCheckingAccount c) {
		checking = c;
	}
	
	synchronized long transferOut() {
		long amount = balance;
		if (amount > maxTranser)
			amount = maxTranser;
		if (amount >=0)
			balance -= amount;
		return amount;		
		
	}
	
	public synchronized void deposit(long amount) throws Exception {
		if (balance + amount < 0)
			throw new Exception(" less than zero");
		else {
			balance += amount;
			checking.tryTransfer();
		}
	}
}
