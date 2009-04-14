package edu.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
/*
 * * http://www.ibm.com/developerworks/library/j-annotate2.html 
 * Data members in
 * annotation types are set up to work using limited information. You don't
 * define a member variable and then provide accessor and mutator methods.
 * Instead, you define a single method, named after the member, that you want to
 * allow for. The data type should be the return value of the method.
 */
public @interface GroupToDo {

	enum Severity {
		CRITICAL, IMPORTANT, TRIVIAL, DOCUMENTATION
	};

	Severity severity() default Severity.IMPORTANT;

	String description();

	String assignedTo();

	long dateAssigned();
}
