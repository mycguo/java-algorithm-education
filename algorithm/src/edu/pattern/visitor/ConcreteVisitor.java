package edu.pattern.visitor;

public class ConcreteVisitor implements Visitor {

	public void visit(Node n) {
		System.out.println("Visiting " + n.getClass().toString());

	}

	public void visit(DifferentNode n) {
		System.out.println("Visiting different node: " + n.getClass().getName());
		
	}

}
