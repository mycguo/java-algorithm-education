package edu.java.concurrent.inpractice;

import java.util.concurrent.atomic.AtomicReference;

public class NonBlockingStack<E> {
	
	private static class Node <E> {
		Node (E item) {
			this.item = item;
		}
		E item;
		Node<E> next; 
	}

	AtomicReference<Node<E>> top = new AtomicReference<Node<E>>();
	
	public void push(E next) {
		Node<E> newHead = new Node<E>(next);
		Node<E> oldHead ;
		
		do {
			oldHead = top.get();
			newHead.next = oldHead;
		} while ( !top.compareAndSet(oldHead, newHead));
		
	}
	
	public E pop() {
		Node<E> newHead,oldHead;

		do {
			oldHead = top.get();
			if (oldHead == null)
				return null;
			newHead = oldHead.next;
			
		} while (!top.compareAndSet(oldHead, newHead));
		
		return oldHead.item;
	}
	
	
}
