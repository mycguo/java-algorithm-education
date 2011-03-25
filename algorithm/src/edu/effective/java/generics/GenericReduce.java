package edu.effective.java.generics;

import java.util.ArrayList;
import java.util.List;

public class GenericReduce {

	public static <T> T reduce(List<T> list, Function f, T initVal) {
		T result = initVal;
		
		List<T> snapShot;
		synchronized (list) {
			snapShot = new ArrayList<T>(list);
		}
		
		for (T o: snapShot) {
			result = f.apply(result,o);
		}
		
		
		return result;
	}
	
	
	public static interface Function {
		public <T> T apply(T arg1, T arg2);
	}
}
