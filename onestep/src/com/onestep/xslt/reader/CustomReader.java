package com.onestep.xslt.reader;

/*
Java and XSLT
By Eric M.Burke
1st Edition September 2001 
ISBN: 0-596-00143-6
*/


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;


/**
 * A utility class that parses a Comma Separated Values (CSV) file
 * and outputs its contents using SAX2 events. The format of CSV that
 * this class reads is identical to the export format for Microsoft
 * Excel. For simple values, the CSV file may look like this:
 * <pre>
 * a,b,c
 * d,e,f
 * </pre>
 * Quotes are used as delimiters when the values contain commas:
 * <pre>
 * a,"b,c",d
 * e,"f,g","h,i"
 * </pre>
 * And double quotes are used when the values contain quotes. This parser
 * is smart enough to trim spaces around commas, as well.
 *
 * @author Eric M. Burke
 */
public class CustomReader extends XMLFilterImpl {

		public CustomReader(XMLReader parent) {
			super(parent);
			this.setContentHandler(parent.getContentHandler());
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes atts) throws SAXException {
			if (!localName.equals("import-schema")) {
				super.startElement(uri, localName, qName, atts);
			} else
				System.out.println("skipping start");
			
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if (!localName.equals("import-schema")) {
				super.endElement(uri, localName, qName);
			} else
				System.out.println("skipping end");
			
		}


}


