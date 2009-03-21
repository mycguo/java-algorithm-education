package edu.java.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
/**
 * http://www.ibm.com/developerworks/library/j-annotate2.html
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
