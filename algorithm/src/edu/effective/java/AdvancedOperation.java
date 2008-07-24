package edu.effective.java;

public enum AdvancedOperation implements Operations {
	EXP("^") {public double apply(double x, double y) {return Math.pow(x, y);} },
	MINUS("%") {public double apply(double x, double y) {return x % y;} };
	
	private final String symbol;
	private AdvancedOperation(String symbol) {
		this.symbol = symbol;
	}
	@Override public String toString() { return symbol; }
}
