package edu.pattern.builder;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;
/**
 * XML processing is quite a typical builder pattern, where
 * 	Director: XMLReader, which controls how the parts to be build
 * 	InputSource: This is the parts to be built
 * 	ConcreteBuilder: ContentHandler, which actually builds the product
 * 	Product: The SAXResult or StreamResult
 * @author CGuo
 *
 */


public class Director {
	
	@Test
	public void builder() throws ParserConfigurationException,
			SAXException, TransformerConfigurationException, TransformerFactoryConfigurationError, IOException {
		
		// This is the director
		XMLReader reader = XMLReaderFactory.createXMLReader();
		
		//Do NOT use SAXParser, it is SAX1 legacy code
		//SAXParserFactory f = SAXParserFactory.newInstance();
		//SAXParser p = f.newSAXParser();
		//p.setProperty("http://xml.org/sax/features/namespaces", true);
		//XMLReader reader = p.getXMLReader();
		
		//this is the builder
		TransformerHandler handler = ((SAXTransformerFactory) SAXTransformerFactory.newInstance()).newTransformerHandler();
		
		//this is the product
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		handler.setResult(result);
		reader.setContentHandler(handler);
		
		reader.parse(new InputSource(new StringReader("<a><b><c></c></b></a>")));
		
		//get the result
		System.out.println(writer.toString());
	}
	
	

}
