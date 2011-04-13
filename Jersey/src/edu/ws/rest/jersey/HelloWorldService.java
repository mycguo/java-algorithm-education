package edu.ws.rest.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


// The Java class will be hosted at the URI path "/helloworld"
/*
 * http://download.oracle.com/docs/cd/E19776-01/820-4867/ggnxs/index.html
 * 
 * client URL: http://localhost:8080/Jersey/helloworld/
 */
	@Path("/helloworld")
	public class HelloWorldService {
	    
	    // The Java method will process HTTP GET requests
	    @GET
	    // The Java method will produce content identified by the MIME Media
	    // type "text/plain"
	    @Produces("text/plain")
	    public String getClichedMessage() {
	        // Return some cliched textual content
	        return "Hello World";
	    }
	}