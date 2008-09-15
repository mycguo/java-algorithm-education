package edu.java.concurrent.ch2;

public class ExpandableArrayWithApply extends ExpandableArray {

	static interface Function {
		void apply(Object o);
	};
	
	public ExpandableArrayWithApply(int cap) {
		super(cap);
	}
	
	synchronized void applyToAll(Function f) {
		for (int i=0; i<size; i++)
			f.apply(data[i]);
	}
	
	
	
	
	
}
