package com.jetbookkeeping;

import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XreoAccount {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		PipeLineUtil util = new PipeLineUtil();
		String[] xsltFiles = new String[] {"csv2xml.xslt"};
		Transformer t = util.getPipeLine(xsltFiles);
		t.setParameter("pathToCSV", "ACCOUNTS.txt");
		t.transform(new StreamSource("<something/>"), new StreamResult(System.out));
		
	}

}
