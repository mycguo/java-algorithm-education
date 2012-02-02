package com.jetbookkeeping;

import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class PipeLineUtil {

	public Transformer getPipeLine(String[] xsltFiles) throws Exception {
		// String xmlSource =
		// "C:/applications/workspacesvn/algorithm/src/edu/java/xml/pipeline/source.xml";
		// xsltFiles = {
		// "C:/applications/workspacesvn/algorithm/src/edu/java/xml/pipeline/xslt1.xslt",
		// "C:/applications/workspacesvn/algorithm/src/edu/java/xml/pipeline/xslt2.xslt"
		// };

		// Pipe using filter
		// http://java.sun.com/j2ee/1.4/docs/tutorial/doc/ Concatenating
		// Transformations with a Filter Chain
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser p = saxFactory.newSAXParser();
		XMLReader reader = p.getXMLReader();

		SAXTransformerFactory tFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

		// first filter parent is reader
		// the rest is the previous filter
		// set the result for the last one
		try {
			XMLFilter filter = tFactory.newXMLFilter(new StreamSource(getClass().getResourceAsStream(xsltFiles[0])));
			filter.setParent(reader);
			for (int i = 1; i < xsltFiles.length; i++) {
				XMLFilter nextFilter = tFactory.newXMLFilter(new StreamSource(getClass().getResourceAsStream(xsltFiles[i])));
				nextFilter.setParent(filter);
				filter = nextFilter;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Transformer t = tFactory.newTransformer();
		return t;
	}
}