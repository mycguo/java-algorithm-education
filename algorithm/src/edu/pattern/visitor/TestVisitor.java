package edu.pattern.visitor;

public class TestVisitor {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		testVisitor();

	}
	
	public static void testVisitor() {
		ConcreteVisitor v = new ConcreteVisitor();
		
		//in real applications, there should be some iterator to iterate through the list
		Node a = new ConcreteNodeA();
		Node b = new ConcreteNodeB();
		a.accept(v);
		b.accept(v);
		
		//visit different node
		DifferentNode d = new DifferentNode();
		d.accept(v);
	}

}
