package edu.pattern.visitor;

public interface Visitor {
	public void visit(Node n);
	
	//visit class from different type
	public void visit(DifferentNode n);

}
