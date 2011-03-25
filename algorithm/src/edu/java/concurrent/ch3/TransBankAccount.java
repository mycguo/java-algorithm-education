package edu.java.concurrent.ch3;

public abstract class TransBankAccount implements Transactor{

	public abstract long balance(Transaction t) throws Failure;
	public abstract void deposit(Transaction t, long amount ) throws Failure;
	public abstract void withdraw(Transaction t, long amount) throws Failure;
}
