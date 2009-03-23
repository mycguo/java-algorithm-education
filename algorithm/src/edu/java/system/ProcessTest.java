package edu.java.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		Process p = new ProcessBuilder("ipconfig").start();
		InputStream stream = p.getInputStream();
		BufferedReader reader = new BufferedReader( new InputStreamReader(stream));
		
		/*
		for (String line = reader.readLine(); line !=null ; line=reader.readLine()) {
			System.out.println(line);
		}
		*/
		String l;
		while ( (l=reader.readLine()) != null)  {
			System.out.println(l);
		}

	}

}
