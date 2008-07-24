package edu.effective.java.annotations;

public enum OperationSymbol {
	
	
	PLUS("+") {double apply(double x, double y) {return x+y;} },
	MINUS("-") {double apply(double x, double y) {return x-y;} },
	TIMES("*") {double apply(double x, double y) {return x*y;} },
	DIVIDE("/") {double apply(double x, double y) {return x/y;} };
	
	private final String symbol;
	private OperationSymbol(String symbol) {
		this.symbol = symbol;
	}
	@Override public String toString() { return symbol; }
	abstract double apply (double x, double y);

}
