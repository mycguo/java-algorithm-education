package com.jetbookkeeping;

import java.io.FileInputStream;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class PipeLineUtil {

	public XMLFilter getPipeLine(String[] xsltFiles) throws Exception {
		// Transformations with a Filter Chain
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		SAXParser p = saxFactory.newSAXParser();
		XMLReader reader = p.getXMLReader();

		SAXTransformerFactory tFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();

		// first filter parent is reader
		// the rest is the previous filter
		// set the result for the last one
		XMLFilter filter = null;
		try {
			filter = tFactory.newXMLFilter(new StreamSource(getClass().getResourceAsStream(xsltFiles[0])));
			filter.setParent(reader);
			for (int i = 1; i < xsltFiles.length; i++) {
				XMLFilter nextFilter = tFactory.newXMLFilter(new StreamSource(getClass().getResourceAsStream(xsltFiles[i])));
				nextFilter.setParent(filter);
				filter = nextFilter;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return filter;

	}
	
	public TransformerHandler getTransformer(String[] xsltFiles) throws Exception {
		
		//Pipe using transformerHandler
		SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		
		//Result result = new StreamResult(System.out);
		
		if (xsltFiles.length ==1) {
			//transfomer.transform(new StreamSource(new FileInputStream(xmlSource)), result);
			return factory.newTransformerHandler(new StreamSource(getClass().getResourceAsStream(xsltFiles[0])));
		} else {
			//create first handler
			TransformerHandler handler = factory.newTransformerHandler(new StreamSource(getClass().getResourceAsStream(xsltFiles[0])));
			TransformerHandler firstHandler = handler;
			//set the content handler to be the next handler
			for (int i=1; i< xsltFiles.length; i++) {
				TransformerHandler nextHandler = factory.newTransformerHandler(new StreamSource(getClass().getResourceAsStream(xsltFiles[i])));
				handler.setResult(new SAXResult(nextHandler));
				handler = nextHandler;
			}
			// last handler output is to the Result
			//handler.setResult(result);
			
			return handler;
			
			//transform to the first handler
			//System.out.println("Pipe line using transfomer handler");
			//transfomer.transform(new StreamSource(new FileInputStream(xmlSource)), new SAXResult(firstHandler));

		}
		
	}
}