package com.onestep.xslt.saxon;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.onestep.xslt.reader.CustomReader;

public class RunCustomReader {
	public static void main(String[] args) {

		String xmlSource = "src/com/onestep/xslt/saxon/profile-report.xml";
		String[] xsltFiles = { "src/com/onestep/xslt/saxon/profile.xslt" };
		
		transform(xmlSource, xsltFiles);


	}

	private static void transform(String xmlSource, String[] xsltFiles) {
		// Pipe using filter
		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", RunSaxon.class.getClassLoader());


		try {
			
			SAXParserFactory saxFactory= SAXParserFactory.newInstance();
			SAXParser p = saxFactory.newSAXParser();
			XMLReader reader = p.getXMLReader();
											
			
			Result filterResult = new StreamResult(new File("output.rtf"));

			SAXSource tranformSource = new SAXSource(new InputSource(xmlSource));
			
			SAXSource xsltSource = new SAXSource(new CustomReader(reader), new InputSource(xsltFiles[0]));
			
			Transformer t = factory.newTransformer(xsltSource);
			
			t.transform(tranformSource, filterResult);


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
