package edu.java.concurrent.inpractice;

public interface Computable<A,V> {
	V compute(A args);
}
