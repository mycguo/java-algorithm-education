package com.onestep.xslt.saxon;

import java.io.File;
import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.onestep.xslt.reader.CustomReader;

public class Streaming {
	public static void main(String[] args) {

		String xmlSource = "com/onestep/xslt/saxon/profile-report.xml";
		String[] xsltFiles = { "com/onestep/xslt/saxon/profile.xslt" };

		transform(xmlSource, xsltFiles);

	}

	private static void transform(String xmlSource, String[] xsltFiles) {
		// Pipe using transformerHandler
		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
		File file = new File("file.rtf");
		System.out.println("Output File: " + file.getAbsolutePath());
		Result result = new StreamResult((new File("file.rtf")));

		try {

			SAXParserFactory saxFactory = SAXParserFactory.newInstance();
			saxFactory.setNamespaceAware(false);
			// saxFactory.setValidating(false);
			XMLReader xmlReader = saxFactory.newSAXParser().getXMLReader();
			xmlReader = new CustomReader(xmlReader);

			TransformerHandler handler = factory
					.newTransformerHandler(new SAXSource(xmlReader, new InputSource(Streaming.class.getClassLoader().getResourceAsStream(xsltFiles[0]))));

			handler.setResult(result);

			InputStream stream = Streaming.class.getClassLoader().getResourceAsStream(xmlSource);
			handler.getTransformer().transform(new SAXSource(new InputSource(stream)), result);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
