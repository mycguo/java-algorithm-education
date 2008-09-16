package edu.effective.java.generics;

import java.util.List;

public class Reduce {

	static Object reduceMethod(List list, Function f, Object initVal) {
		Object result = initVal;
		Object[] snapShot = list.toArray();
		for (Object o: snapShot) {
			result = f.apply(result,o);
		}		
		return result;
	}

	public static interface Function {
		Object apply(Object arg1, Object arg2);
	}
}
