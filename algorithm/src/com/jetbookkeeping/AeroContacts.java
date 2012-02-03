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

	public void convertContacts() throws Exception {
		PipeLineUtil util = new PipeLineUtil();
		String[] xsltFiles = new String[] { "csv2xml.xslt" };
		XMLFilter filter = util.getPipeLine(xsltFiles);

		File file = new File("c:/temp/Contacts.xml");
		Result filterResult = new StreamResult(new FileOutputStream(file));

		// transform to the last filter
		SAXSource tranformSource = new SAXSource(filter, new InputSource("src/com/jetbookkeeping/csv2xml.xslt"));
		TransformerHandler h = util.getTransformer(xsltFiles);
		Transformer t = h.getTransformer();
		t.setParameter("pathToCSV", "file:///C:/Users/cg/workspace/JavaProject/src/com/jetbookkeeping/CUST.TXT");
		System.out.println("Pipeline using transformer chain");
		t.transform(tranformSource, filterResult);

	}
}
