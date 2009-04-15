package edu.pattern.builder;

import java.io.StringReader;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
/**
 * XML processing is quite a typical builder pattern, where
 * 	Director: XMLReader, which controls how the parts to be build
 * 	InputSource: This is the parts to be built
 * 	ConcreteBuilder: ContentHandler, which actually builds the product
 * 	Product: The SAXResult
 * @author CGuo
 *
 */


public class Director {
	
	
	public XMLReader getReader() throws ParserConfigurationException,
			SAXException, TransformerConfigurationException, TransformerFactoryConfigurationError {
		// for xml, the director should be the reader
		XMLReader reader = XMLReaderFactory.createXMLReader();
		
		//Do NOT use SAXParser, it is SAX1 legacy code
		//SAXParserFactory f = SAXParserFactory.newInstance();
		//SAXParser p = f.newSAXParser();
		//p.setProperty("http://xml.org/sax/features/namespaces", true);
		//XMLReader reader = p.getXMLReader();
		//this is the builder
		TransformerHandler handler = ((SAXTransformerFactory) SAXTransformerFactory.newInstance()).newTransformerHandler();
		
		//this is the product
		StreamResult result = new StreamResult(System.out);
		handler.setResult(result);
		reader.setContentHandler(handler);
		return reader;
	}
	
	
	@Test
	public void contruct() throws Throwable, SAXException, ParserConfigurationException {
		//build it now
		getReader().parse(new InputSource(new StringReader("<a><b><c></c></b></a>")));
		
		//get result
		ContentHandler handler = getReader().getContentHandler();
		
		
		
		

		
		
		
	}


}
