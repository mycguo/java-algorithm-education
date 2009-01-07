package edu.java.xml.validation;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class ParserWithValidation {

	/**
	 * @param args
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

		String name = "C:/Documents and Settings/cguo/My Documents/Documents/documentation/qa.xmla";
		validateUsingSAX(name);
		validateUsingDOM(name);
	}

	/*
	 * It seems the SAX parsing validation is only limited to DTD, which is very
	 * strange, but it is true
	 */
	private static void validateUsingSAX(String name) throws ParserConfigurationException, SAXException, IOException {
		/*
		 * http://java.sun.com/j2se/1.5.0/docs/api/ 
		 * 
		 * javax.xml.parsers Class
		 * SAXParserFactory
		 * 
		 * public void setValidating(boolean validating)
		 * 
		 * Specifies that the parser produced by this code will validate
		 * documents as they are parsed. By default the value of this is set to
		 * false.
		 * 
		 * Note that "the validation" here means a validating parser as defined
		 * in the XML recommendation. In other words, it essentially just
		 * controls the DTD validation. (except the legacy two properties
		 * defined in JAXP 1.2. See here for more details.)
		 * 
		 * To use modern schema languages such as W3C XML Schema or RELAX NG
		 * instead of DTD, you can configure your parser to be a non-validating
		 * parser by leaving the setValidating(boolean) method false, then use
		 * the setSchema(Schema) method to associate a schema to a parser.
		 */
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new URL("http://gnd.fai.fujitsu.com/2004/xsd/FBFPage.xsd"));
			Validator v = schema.newValidator();
			System.out.println("Validating file: " + name);
			v.validate(new SAXSource(new InputSource( name )));
		} catch (SAXParseException spe) {
			String err = spe.toString() + "\n  Line number: "
					+ spe.getLineNumber() + "\nColumn number: "
					+ spe.getColumnNumber() + "\n Public ID: "
					+ spe.getPublicId() + "\n System ID: " + spe.getSystemId();
			System.out.println(err);
		}

		

	}

	/**
	 * Check the validation using DOM model
	 * It seems the default schema setting in the document doesn't work, has to manully set the schema souce
	 * @param name
	 */
	private static void validateUsingDOM(String name) {
		/*
		 * http://java.sun.com/j2ee/1.4/docs/tutorial/doc/
		 * 
		 */
		 final String JAXP_SCHEMA_LANGUAGE =
		    "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

		 final String W3C_XML_SCHEMA =
		    "http://www.w3.org/2001/XMLSchema"; 
		 
		 final String schemaSource = "http://gnd.fai.fujitsu.com/2004/xsd/FBFPage.xsd";
		 final String JAXP_SCHEMA_SOURCE =
		     "http://java.sun.com/xml/jaxp/properties/schemaSource";		 

		  DocumentBuilderFactory factory =
		      DocumentBuilderFactory.newInstance();
		  factory.setNamespaceAware(true);
		  //again, the validation here is only for DTD
		  //factory.setValidating(true);
		  
		  factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		  factory.setAttribute(JAXP_SCHEMA_SOURCE, schemaSource); 

		  
		  try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			try {
				System.out.println("Validating file: " + name + " using DOM");
				builder.parse(new File(name));
			} catch (SAXException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {

			e.printStackTrace();
		}
		try {
		  factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
		} 
		catch (IllegalArgumentException x) {
		  // Happens if the parser does not support JAXP 1.2
		 x.printStackTrace();
		} 

	}

}
