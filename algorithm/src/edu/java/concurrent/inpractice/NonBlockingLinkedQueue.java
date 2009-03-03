package edu.java.concurrent.inpractice;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Page 334
 * @author cguo
 *
 * @param <E>
 */
public class NonBlockingLinkedQueue <E> {
	
	static class Node<E> {
		E item;
		AtomicReference<Node<E>> next;
		
		public Node(E item, Node<E> next) {
			this.item = item;
			this.next = new AtomicReference<Node<E>>(next);
		}		
	}
	
	Node<E> dummy = new Node<E>(null,null);
	
	private final AtomicReference<Node<E>> headRef = new AtomicReference<Node<E>>(dummy);
	private final AtomicReference<Node<E>> tailRef = headRef;
	
	
	public boolean add(E item) {
		Node<E> newNode = new Node<E>(item, null);
		
		while (true) {
			Node<E> tail = tailRef.get();
			Node<E> tailNext = tail.next.get();
			if (tail == tailRef.get()) { //make sure it is still current
				//it is still the current tail, stable
				if (tailNext ==null) {
					if (tail.next.compareAndSet(null, newNode)) {  //insert a new node
						tailRef.compareAndSet(tail, newNode);    //advance tail
					}
					return true;
						
				} else { //not stable, advance tail
					tailRef.compareAndSet(tail, tailNext);
					
				}
			}
		}
		
		
		
		
	}

}
