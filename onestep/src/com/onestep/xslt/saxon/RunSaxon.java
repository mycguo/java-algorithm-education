package com.onestep.xslt.saxon;

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


public class RunSaxon {
	public static void main(String[] args) {

		String xmlSource = "com/onestep/xslt/saxon/profile-report.xml";
		String[] xsltFiles = { "com/onestep/xslt/saxon/profile.xslt" };
		
		transform(xmlSource, xsltFiles);


	}

	private static void transform(String xmlSource, String[] xsltFiles) {
		// Pipe using transformerHandler
		SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance("net.sf.saxon.TransformerFactoryImpl", RunSaxon.class.getClassLoader());

		Result result = new StreamResult(System.out);

		try {
			InputStream stream = RunSaxon.class.getClassLoader().getResourceAsStream(xsltFiles[0]);
			Templates template = factory.newTemplates(new StreamSource(stream));
			Transformer transfomer = template.newTransformer();
			//transfomer.setParameter("SV_BaseOutputFileName", "dummy");
			if (xsltFiles.length == 1) {
				stream = RunSaxon.class
				.getClassLoader().getResourceAsStream(xmlSource);
				if (stream == null)
					System.out.println("input source is null");
				
				transfomer.transform(new StreamSource(stream),
						result);
				
				System.out.println("Succeed!");
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


		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}