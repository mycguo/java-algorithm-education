package edu.pattern.visitor;

public class ConcreteNodeA implements Node {

	public void operation() {
		//do my things
		
	}
	
	public void accept(Visitor v) {
		v.visit(this);
	}

}
