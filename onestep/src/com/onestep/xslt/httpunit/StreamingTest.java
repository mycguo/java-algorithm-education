package com.onestep.xslt.httpunit;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.TestCase;

import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class StreamingTest extends TestCase {

	public void testStream() throws IOException, SAXException {
		ServletRunner sr = new ServletRunner();
		sr.registerServlet("myServlet", StreamingServlet.class.getName());

		ServletUnitClient sc = sr.newClient();
		WebRequest request = new GetMethodWebRequest("http://test.meterware.com/myServlet");
		request.setParameter("color", "red");
		WebResponse response = sc.getResponse(request);
		assertNotNull("No response received", response);
		System.out.println("Output type: " + response.getContentType() + " size: " + response.getContentLength() + " code: "  +response.getResponseCode());
		
		ByteArrayInputStream stream = (java.io.ByteArrayInputStream) response.getInputStream();
		System.out.println("size: " + stream.available());


	}

}
