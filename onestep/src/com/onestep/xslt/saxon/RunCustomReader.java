package com.onestep.xslt.saxon;

import java.io.File;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.onestep.xslt.reader.CustomReader;

public class RunCustomReader {
	public static void main(String[] args) {

		String xmlSource = "src/com/onestep/xslt/saxon/profile-report.xml";
		String[] xsltFiles = { "src/com/onestep/xslt/saxon/profile.xslt" };
		
		transform(xmlSource, xsltFiles);


	}

	private static void transform(String xmlSource, String[] xsltFiles) {



		try {
			
			//Get the default reader
			XMLReader reader = XMLReaderFactory.createXMLReader();
														

			//set source, result
			SAXSource tranformSource = new SAXSource(new InputSource(xmlSource));
			Result filterResult = new StreamResult(new File("output.rtf"));
			
			XMLReader realReader = new CustomReader(reader);
			//read XSLT from customized reader
			SAXSource xsltSource = new SAXSource(realReader, new InputSource(xsltFiles[0]));

			//create SAXON factory and transfomer
			SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", RunSaxon.class.getClassLoader());
			Transformer t = factory.newTransformer(xsltSource);
			//do the job
			t.transform(tranformSource, filterResult);


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
