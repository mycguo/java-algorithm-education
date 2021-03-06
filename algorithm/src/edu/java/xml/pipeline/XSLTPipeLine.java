package edu.java.xml.pipeline;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;

public class XSLTPipeLine {

	/** 
	 * @param args List of XSLT files
	 * @throws TransformerException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws TransformerException, ParserConfigurationException, SAXException, IOException {
		// TODO Auto-generated method stub
		String xmlSource="C:/applications/workspacesvn/algorithm/src/edu/java/xml/pipeline/source.xml";
		String[] xsltFiles = {"C:/applications/workspacesvn/algorithm/src/edu/java/xml/pipeline/xslt1.xslt", 
							"C:/applications/workspacesvn/algorithm/src/edu/java/xml/pipeline/xslt2.xslt"};
		
		//Pipe using transformerHandler
		SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		
		Result result = new StreamResult(System.out);
		
		Transformer transfomer = factory.newTransformer(new StreamSource(new FileInputStream(xsltFiles[0])));
		if (xsltFiles.length ==1) {
			transfomer.transform(new StreamSource(new FileInputStream(xmlSource)), result);
		} else {
			//create first handler
			TransformerHandler handler = factory.newTransformerHandler(new StreamSource(new FileInputStream(xsltFiles[1])));
			TransformerHandler firstHandler = handler;
			//set the content handler to be the next handler
			for (int i=2; i< xsltFiles.length; i++) {
				TransformerHandler nextHandler = factory.newTransformerHandler(new StreamSource(new FileInputStream(xsltFiles[i])));
				handler.setResult(new SAXResult(nextHandler));
				handler = nextHandler;
			}
			// last handler output is to the Result
			handler.setResult(result);
			
			//transform to the first handler
			System.out.println("Pipe line using transfomer handler");
			transfomer.transform(new StreamSource(new FileInputStream(xmlSource)), new SAXResult(firstHandler));

		}
		
		
		//Pipe using filter
		//http://java.sun.com/j2ee/1.4/docs/tutorial/doc/   Concatenating Transformations with a Filter Chain 
		SAXParserFactory saxFactory= SAXParserFactory.newInstance();
		SAXParser p = saxFactory.newSAXParser();
		XMLReader reader = p.getXMLReader();
		
		SAXTransformerFactory tFactory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
		
		
		
		//first filter parent is reader
		//the rest is the previous filter
		//set the result for the last one
		XMLFilter filter = tFactory.newXMLFilter(new StreamSource(new FileInputStream(xsltFiles[0])));
		filter.setParent(reader);
		for (int i=1; i< xsltFiles.length; i++) {
			XMLFilter nextFilter = tFactory.newXMLFilter(new StreamSource(new FileInputStream(xsltFiles[i])));
			nextFilter.setParent(filter);
			filter =nextFilter;
		}

		Result filterResult = new StreamResult(System.out);
		//transform to the last filter
		SAXSource tranformSource = new SAXSource(filter,new InputSource(xmlSource));
		Transformer t = tFactory.newTransformer();
		System.out.println("Pipeline using Filter chain");
		t.transform(tranformSource, filterResult);
		
	}

}
