package edu.effective.java.annotations;

public class Sample {
	@Test public static void m1() {};
	@Test public static void m3() {
		throw new RuntimeException("boom");
	}
	@Test public void m5() {};

}
