package com.ccr.xsd;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class RunCCR {
	public static void main(String[] args) {

		String xmlSource = "com/ccr/xsd/JohnSmith.xml";
		String[] xsltFiles = { "com/ccr/xsd/ccr.xsl" };
		
		transform(xmlSource, xsltFiles);


	}

	private static void transform(String xmlSource, String[] xsltFiles) {
		// Pipe using transformerHandler
		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", RunCCR.class.getClassLoader());
		
		File file = new File("JohnSmith.html");
		Result result = new StreamResult(file);

		try {
			InputStream stream = RunCCR.class.getClassLoader().getResourceAsStream(xsltFiles[0]);
			Templates template = factory.newTemplates(new StreamSource(stream));
			Transformer transfomer = template.newTransformer();
			if (xsltFiles.length == 1) {
				stream = RunCCR.class
				.getClassLoader().getResourceAsStream(xmlSource);
				if (stream == null)
					System.out.println("input source is null");
				
				transfomer.transform(new StreamSource(stream),
						result);
				
				System.out.println("Succeed in creating file: " + file.getAbsolutePath());
			} else {
				// create first handler
				TransformerHandler handler = factory
						.newTransformerHandler(new StreamSource(
								new FileInputStream(xsltFiles[1])));
				TransformerHandler firstHandler = handler;
				// set the content handler to be the next handler
				for (int i = 2; i < xsltFiles.length; i++) {
					TransformerHandler nextHandler = factory
							.newTransformerHandler(new StreamSource(
									new FileInputStream(xsltFiles[i])));
					handler.setResult(new SAXResult(nextHandler));
					handler = nextHandler;
				}
				// last handler output is to the Result
				handler.setResult(result);

				// transform to the first handler
				System.out.println("Pipe line using transfomer handler");
				transfomer.transform(new StreamSource(new FileInputStream(
						xmlSource)), new SAXResult(firstHandler));

			}


		} catch (Error e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
