package edu.ws.rest.jersey.containers;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutClient {

	/**
	 * http://download.oracle.com/docs/cd/E19776-01/820-4867/ggrby/index.html
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		URL url = new URL("http://localhost:8080/Jersey/containers/quotes");
		HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		OutputStreamWriter out = new OutputStreamWriter(
		    httpCon.getOutputStream());
		out.write("Resource content");
		System.out.println("sending request put");
		out.close();

	}

}
