package edu.effective.java;

import java.util.Arrays;
import java.util.Collection;

/**
 * This only works with Java 6, doesn't run under Java 5
 * @author CGuo
 *
 */
public class TestExtendedEnum {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double x = Double.parseDouble(args[0]);
		double y = Double.parseDouble(args[1]);
		test(BasicOperation.class, x, y);
		test(AdvancedOperation.class,x,y);
		test(Arrays.asList(AdvancedOperation.values()),x, y);
		

	}

	/**
	 * Note how the generic type parameter is past in
	 * @param <T> must be of Enum type and implements Operations
	 * @param opSet 
	 * @param x
	 * @param y
	 */
	private static <T extends Enum<T> & Operations> void test(Class<T> opSet, double x, double y) {
		for (Operations op: opSet.getEnumConstants()) {
			System.out.printf("%f %s %f = %f %n", x ,op, y, op.apply(x, y));
		}
	}
	
	private static void test (Collection<? extends Operations> opSet, double x, double y) {
		for (Operations op: opSet) {
			System.out.printf("%f %s %f = %f %n", x ,op, y, op.apply(x, y));
		}
	}
}
