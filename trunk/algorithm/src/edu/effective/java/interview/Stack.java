package edu.effective.java.interview;

public interface Stack {
    void push( Object x );
    Object pop( );
    Object top( );
    boolean isEmpty( );
    void makeEmpty( );
}