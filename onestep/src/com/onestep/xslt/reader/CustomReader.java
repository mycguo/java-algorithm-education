package com.onestep.xslt.reader;

/*
Java and XSLT
By Eric M.Burke
1st Edition September 2001 
ISBN: 0-596-00143-6
*/


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.helpers.AttributesImpl;
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

    // an empty attribute for use with SAX
    private static final Attributes EMPTY_ATTR = new AttributesImpl();

    /**
     * Parse a CSV file. SAX events are delivered to the ContentHandler
     * that was registered via <code>setContentHandler</code>.
     *
     * @param input the comma separated values file to parse.
     */
    public void parse(InputSource input) throws IOException,
            SAXException {
        // if no handler is registered to receive events, don't bother
        // to parse the CSV file
        ContentHandler ch = getContentHandler();
        if (ch == null) {
            return;
        }

        // convert the InputSource into a BufferedReader
        BufferedReader br = null;
        if (input.getCharacterStream() != null) {
            br = new BufferedReader(input.getCharacterStream());
        } else if (input.getByteStream() != null) {
            br = new BufferedReader(new InputStreamReader(
                    input.getByteStream()));
        } else if (input.getSystemId() != null) {
            java.net.URL url = new URL(input.getSystemId());
            br = new BufferedReader(new InputStreamReader(url.openStream()));
        } else {
            throw new SAXException("Invalid InputSource object");
        }

        ch.startDocument();
        
         // read each line of the file until EOF is reached
        String curLine = null;
        while ((curLine = br.readLine()) != null) {
        	
            curLine = curLine.trim();
            if (curLine.length() > 0) {
            	if (curLine.contains("import-schema")) 
            		continue;
            	else 
            		super.characters(curLine.toCharArray(),0,100);

                // create the <line> element
                ch.startElement("","","line",EMPTY_ATTR);
                // output data from this line
                parseLine(curLine, ch);
                // close the </line> element
                ch.endElement("","","line");
            }
        }

        // emit </csvFile>
        ch.endElement("","","csvFile");
        ch.endDocument();
    }

    // Break an individual line into tokens. This is a recursive function
    // that extracts the first token, then recursively parses the
    // remainder of the line.
    private void parseLine(String curLine, ContentHandler ch)
        throws IOException, SAXException {

        String firstToken = null;
        String remainderOfLine = null;
        int commaIndex = locateFirstDelimiter(curLine);
        if (commaIndex > -1) {
            firstToken = curLine.substring(0, commaIndex).trim();
            remainderOfLine = curLine.substring(commaIndex+1).trim();
        } else {
            // no commas, so the entire line is the token
            firstToken = curLine;
        }

        // remove redundant quotes
        firstToken = cleanupQuotes(firstToken);

        // emit the <value> element
        ch.startElement("","","value",EMPTY_ATTR);
        ch.characters(firstToken.toCharArray(), 0, firstToken.length());
        ch.endElement("","","value");

        // recursively process the remainder of the line
        if (remainderOfLine != null) {
            parseLine(remainderOfLine, ch);
        }
    }

    // locate the position of the comma, taking into account that
    // a quoted token may contain ignorable commas.
    private int locateFirstDelimiter(String curLine) {
        if (curLine.startsWith("\"")) {
            boolean inQuote = true;
            int numChars = curLine.length();
            for (int i=1; i<numChars; i++) {
                char curChar = curLine.charAt(i);
                if (curChar == '"') {
                    inQuote = !inQuote;
                } else if (curChar == ',' && !inQuote) {
                    return i;
                }
            }
            return -1;
        } else {
            return curLine.indexOf(',');
        }
    }

    // remove quotes around a token, as well as pairs of quotes
    // within a token.
    private String cleanupQuotes(String token) {
        StringBuffer buf = new StringBuffer();
        int length = token.length();
        int curIndex = 0;

        if (token.startsWith("\"") && token.endsWith("\"")) {
            curIndex = 1;
            length--;
        }

        boolean oneQuoteFound = false;
        boolean twoQuotesFound = false;

        while (curIndex < length) {
            char curChar = token.charAt(curIndex);
            if (curChar == '"') {
                twoQuotesFound = (oneQuoteFound) ? true : false;
                oneQuoteFound = true;
            } else {
                oneQuoteFound = false;
                twoQuotesFound = false;
            }

            if (twoQuotesFound) {
                twoQuotesFound = false;
                oneQuoteFound = false;
                curIndex++;
                continue;
            }

            buf.append(curChar);
            curIndex++;
        }

        return buf.toString();
    }
}

/**
 * An abstract class that implements the SAX2 XMLReader interface. The
 * intent of this class is to make it easy for subclasses to act as
 * SAX2 XMLReader implementations. This makes it possible, for example, for
 * them to emit SAX2 events that can be fed into an XSLT processor for
 * transformation.
 *
 * @author Eric M. Burke
 */
abstract class AbstractXMLReader implements org.xml.sax.XMLReader {
    private Map featureMap = new HashMap();
    private Map propertyMap = new HashMap();
    private EntityResolver entityResolver;
    private DTDHandler dtdHandler;
    private ContentHandler contentHandler;
    private ErrorHandler errorHandler;

    /**
     * The only abstract method in this class. Derived classes can parse
     * any source of data and emit SAX2 events to the ContentHandler.
     */
    public abstract void parse(InputSource input) throws IOException,
            SAXException;

    public boolean getFeature(String name)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        Boolean featureValue = (Boolean) this.featureMap.get(name);
        return (featureValue == null) ? false
                : featureValue.booleanValue();
    }

    public void setFeature(String name, boolean value)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        this.featureMap.put(name, new Boolean(value));
    }

    public Object getProperty(String name)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        return this.propertyMap.get(name);
    }

    public void setProperty(String name, Object value)
            throws SAXNotRecognizedException, SAXNotSupportedException {
        this.propertyMap.put(name, value);
    }

    public void setEntityResolver(EntityResolver entityResolver) {
        this.entityResolver = entityResolver;
    }

    public EntityResolver getEntityResolver() {
        return this.entityResolver;
    }

    public void setDTDHandler(DTDHandler dtdHandler) {
        this.dtdHandler = dtdHandler;
    }

    public DTDHandler getDTDHandler() {
        return this.dtdHandler;
    }

    public void setContentHandler(ContentHandler contentHandler) {
        this.contentHandler = contentHandler;
    }

    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    public void parse(String systemId) throws IOException, SAXException {
        parse(new InputSource(systemId));
    }
}


