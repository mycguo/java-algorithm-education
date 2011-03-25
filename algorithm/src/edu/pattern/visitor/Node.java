package edu.pattern.visitor;

public interface Node {

	public void operation();
	public void accept(Visitor v);

}
