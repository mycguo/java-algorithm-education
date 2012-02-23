package com.jetbookkeeping;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.XMLFilter;

public class AeroContacts {


	public static void main(String[] args) throws Exception {
		PipeLineUtil util = new PipeLineUtil();
		//String[] xsltFiles = new String[] { "csv2xml.xslt","CUST-SUPPLIERS-to-CONTACTS.xslt" };
		String[] xsltFiles = new String[] { "CUST-SUPPLIERS-to-CONTACTS.xslt" };
		XMLFilter filter = util.getPipeLine(xsltFiles);

		File file = new File("c:/temp/Contacts-trans.xml");
		Result filterResult = new StreamResult(new FileOutputStream(file));

		// transform to the last filter
		//this source is dummy
		SAXSource tranformSource = new SAXSource(filter, new InputSource("c:/temp/Contacts.xml"));
		TransformerHandler h = util.getTransformer(xsltFiles);
		Transformer t = h.getTransformer();
		t.setParameter("pathToCSV", "file:///C:/Users/cg/workspace/JavaProject/src/com/jetbookkeeping/CUST.TXT");
		System.out.println("Pipeline using transformer chain");
		t.transform(tranformSource, filterResult);

	}
}
