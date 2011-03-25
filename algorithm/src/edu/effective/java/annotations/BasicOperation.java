package edu.effective.java.annotations;

public enum BasicOperation implements Operations {
	PLUS("+") {public double apply(double x, double y) {return x+y;} },
	MINUS("-") {public double apply(double x, double y) {return x-y;} },
	TIMES("*") {public double apply(double x, double y) {return x*y;} },
	DIVIDE("/") {public double apply(double x, double y) {return x/y;} };
	
	private final String symbol;
	private BasicOperation(String symbol) {
		this.symbol = symbol;
	}
	@Override public String toString() { return symbol; }
	
}
