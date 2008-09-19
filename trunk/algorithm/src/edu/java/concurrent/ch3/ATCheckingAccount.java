package edu.java.concurrent.ch3;

public class ATCheckingAccount extends BankAccount{

	ATSavingAccount saving;
	private long threshold;
	private TSBoolean inProgress = new TSBoolean();
	
	public ATCheckingAccount(long t) {
		threshold = t;
	}
	
	synchronized void initSaving(ATSavingAccount s) {
		saving =s;
	}
	
	protected boolean shouldTry() {
		return balance < threshold;
	}
	
	void tryTransfer() {
		if (!inProgress.testAndSet()) {
			try {
				synchronized(this) {
					if (shouldTry())
						balance +=saving.transferOut();
				}
			} finally {
				inProgress.clear();
			}
		}
	}
	
	public synchronized void deposit(long amount) throws Exception {
		if ( balance + amount < 0)
			throw new Exception(" less than zero ");
		else {
			balance += amount;
			tryTransfer();
		}
	}
}
