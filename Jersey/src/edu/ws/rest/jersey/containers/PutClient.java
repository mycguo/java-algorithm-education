package edu.ws.rest.jersey.containers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutClient {

	/**
	 * http://download.oracle.com/docs/cd/E19776-01/820-4867/ggrby/index.html
	 * 
	 * cUrl -X PUT http://127.0.0.1:7070/Jersey/containers/quotes
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)  throws IOException {
		executePut("http://localhost:7070/Jersey/containers/quotes");

	}

	 public static void executePut(String targetURL) throws IOException {
			URL url = new URL("http://localhost:7070/Jersey/containers/quotes");
			HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
			httpCon.setDoInput(true);
			httpCon.setDoOutput(true);
			httpCon.setRequestMethod("PUT");

		      //Send request
		      DataOutputStream wr = new DataOutputStream (
		                  httpCon.getOutputStream ());
		      wr.writeBytes ("something");
		      wr.flush ();
		      wr.close ();

		      //Get Response	
		      InputStream is = httpCon.getInputStream(); //this line is must for it to work, why?
		      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		      String line;
		      StringBuffer response = new StringBuffer(); 
		      while((line = rd.readLine()) != null) {
		        response.append(line);
		        response.append('\r');
		      }
		      rd.close();
		      System.out.println("Response " + response.toString());

		 
	 }
	
	
	  public static String excutePost(String targetURL, String urlParameters)
	  {
	    URL url;
	    HttpURLConnection connection = null;  
	    try {
	      //Create connection
	      url = new URL(targetURL);
	      connection = (HttpURLConnection)url.openConnection();
	      connection.setRequestMethod("POST");
	      connection.setRequestProperty("Content-Type", 
	           "application/x-www-form-urlencoded");
				
	      connection.setRequestProperty("Content-Length", "" + 
	               Integer.toString(urlParameters.getBytes().length));
	      connection.setRequestProperty("Content-Language", "en-US");  
				
	      connection.setUseCaches (false);
	      connection.setDoInput(true);
	      connection.setDoOutput(true);

	      //Send request
	      DataOutputStream wr = new DataOutputStream (
	                  connection.getOutputStream ());
	      wr.writeBytes (urlParameters);
	      wr.flush ();
	      wr.close ();

	      //Get Response	
	      InputStream is = connection.getInputStream();
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is));
	      String line;
	      StringBuffer response = new StringBuffer(); 
	      while((line = rd.readLine()) != null) {
	        response.append(line);
	        response.append('\r');
	      }
	      rd.close();
	      return response.toString();

	    } catch (Exception e) {

	      e.printStackTrace();
	      return null;

	    } finally {

	      if(connection != null) {
	        connection.disconnect(); 
	      }
	    }
	  }
}
