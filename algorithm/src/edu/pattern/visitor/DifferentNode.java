package edu.pattern.visitor;

public class DifferentNode {
	
	public void accept(Visitor v) {
		v.visit(this);
	}

}
