package edu.pattern.visitor;

public class ConcreteNodeB implements Node {

	public void operation() {
		//do my stuff

	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
