package edu.java.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;


@GroupToDo(description="to do it later", assignedTo = "charles", dateAssigned = 19970709)
public class WithAnnotation {

	public static void main(String[] args) {
		WithAnnotation with = new WithAnnotation();
		Annotation[] a = with.getClass().getAnnotations();
		for (Annotation aa: a) {
			System.out.println("Class: " + aa.annotationType() + " " + aa.annotationType().toString());
		}
		
		Method[] m = with.getClass().getMethods();
		for (Method mm: m) {
			System.out.println("Method: " + mm.getName() + " " + mm.getAnnotations().length);
		}
	}
	
	@GroupToDo(assignedTo = "charles", dateAssigned = 0, description = "do it quick")
	public void doSomething() {
		
	}
}
