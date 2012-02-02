package com.jetbookkeeping;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.XMLFilter;

public class XreoAccount {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		PipeLineUtil util = new PipeLineUtil();
		String[] xsltFiles = new String[] {"csv2xml.xslt"};
		XMLFilter filter = util.getPipeLine(xsltFiles);
		
		File file = new File("c:/temp/out.xml");
		Result filterResult = new StreamResult(new FileOutputStream(file));
		SAXTransformerFactory tFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

		//transform to the last filter
		SAXSource tranformSource = new SAXSource(filter,new InputSource("src/com/jetbookkeeping/csv2xml.xslt"));
		Transformer t = tFactory.newTransformer();
		t.setParameter("pathToCSV", "file:///C:/Users/cg/workspace/JavaProject/src/com/jetbookkeeping/ACCOUNTS.TXT");
		System.out.println("Pipeline using Filter chain");
		t.transform(tranformSource, filterResult);
		
		
		t.transform(tranformSource, new StreamResult(System.out));
		
	}

}
