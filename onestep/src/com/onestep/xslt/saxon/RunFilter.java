package com.onestep.xslt.saxon;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.onestep.xslt.reader.DecoHandler;

public class RunFilter {
	public static void main(String[] args) {

		String xmlSource = "com/onestep/xslt/saxon/profile-report.xml";
		String[] xsltFiles = { "com/onestep/xslt/saxon/profile-import.xslt" };
		
		transform(xmlSource, xsltFiles);


	}

	private static void transform(String xmlSource, String[] xsltFiles) {
		// Pipe using filter
		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", RunSaxon.class.getClassLoader());


		try {
			
			SAXParserFactory saxFactory= SAXParserFactory.newInstance();
			SAXParser p = saxFactory.newSAXParser();
			XMLReader reader = p.getXMLReader();
			reader.setContentHandler(new DecoHandler(new DefaultHandler()));
			
									
			
			Result filterResult = new StreamResult(System.out);

			SAXSource tranformSource = new SAXSource(new InputSource(xmlSource));
			Transformer t = factory.newTransformer(new StreamSource(RunFilter.class.getClassLoader().getResourceAsStream(xsltFiles[0])));
			
			t.transform(tranformSource, filterResult);


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
