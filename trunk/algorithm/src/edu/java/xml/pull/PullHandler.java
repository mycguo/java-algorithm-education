/*
 * Copyright (c) 2000-2001 Sosnoski Software Solutions, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package edu.java.xml.pull;
import java.io.*;
import java.util.*;

import org.gjt.xpp.*;

/**
 * XPP2 event pull parser handler for trade history information stream.
 *
 * @author Dennis M. Sosnoski
 * @version 1.0
 */

public class PullHandler
{
	/** Name of element containing stock trade. */
	public static String STOCK_ELEMENT_NAME = "stock-trade";
	
	/** Name of element containing option trade. */
	public static String OPTION_ELEMENT_NAME = "option-trade";
	
	/** Name of element containing stock symbol. */
	protected String SYMBOL_ELEMENT_NAME = "symbol";
	
	/** Name of element containing trade price. */
	protected String PRICE_ELEMENT_NAME = "price";
	
	/** Name of element containing quantity  */
	protected String QUANTITY_ELEMENT_NAME = "quantity";
	
	/** Name of element containing option type ("call" or "put") */
	protected String TYPE_ELEMENT_NAME = "option-type";
	
	/** Name of element containing strike price. */
	protected String STRIKE_ELEMENT_NAME = "strike-price";
	
	/** Name of element containing expiration month. */
	protected String EXPIRE_ELEMENT_NAME = "expiration-month";
	
	/** Name of element containing trade price. */
	protected String TRADE_ELEMENT_NAME = "trade-price";
	
	/** Name of element containing tracking information. */
	public static String TRACKING_ELEMENT_NAME = "tracking";
	
	/** Name of attribute supplying tracking identifier. */
	protected static String ID_ATTRIBUTE_NAME = "id";
	
	/** Name of element containing timestamp. */
	protected static String TIME_ELEMENT_NAME = "time";
	
	/** Name of element containing seller information. */
	protected static String SELLER_ELEMENT_NAME = "seller";
	
	/** Name of element containing buyer information. */
	protected static String BUYER_ELEMENT_NAME = "buyer";
	
	/** Name of participant identifier attribute. */
	protected static String IDENT_ATTRIBUTE_NAME = "ident";
	
	/** Name of participant type attribute. */
	protected static String TYPE_ATTRIBUTE_NAME = "type";
	
	/** Name of element containing exchange identifier. */
	protected static String EXCHANGE_ELEMENT_NAME = "exchange";
	
	/** Parser in use. */
	protected XmlPullParser m_parser;
	
	/** Start tag from document. */
	protected XmlStartTag m_startTag;
	
	/** Start tag from document. */
	protected XmlEndTag m_endTag;
	
	/** Content buffer for text accumulation. */
	protected StringBuffer m_buffer;
	
	/**
	 * Constructor. Builds the shared objects used for parsing.
	 *
	 * @throws XmlPullParserException if error creating parser
	 */
	
	public PullHandler() throws XmlPullParserException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		m_parser = factory.newPullParser();
		m_startTag = factory.newStartTag();
		m_endTag = factory.newEndTag();
		m_buffer = new StringBuffer();
	}
	
	/**
	 * Parse start of element from document. Ignores character data to next
	 * start or end tag, but throws exception if an end tag is seen before a
	 * start tag, or if the start tag seen does not match the expected name.
	 *
	 * @param tag element name expected
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException if expected element not found, or if
	 * other parse error
	 */

	protected void parseStartTag(String tag) 
		throws IOException, XmlPullParserException {
		while (true) {
			switch (m_parser.next()) {
				
				case XmlPullParser.START_TAG:
					m_parser.readStartTag(m_startTag);
					if (m_startTag.getLocalName().equals(tag)) {
						return;
					}
					// fall through for error handling
				
				case XmlPullParser.END_TAG:
				case XmlPullParser.END_DOCUMENT:
					throw new XmlPullParserException
						("Missing expected start tag " + tag);

			}
		}
	}
	
	/**
	 * Parse end of element from document. Collects character data to the end
	 * tag and returns it with whitespace stripped. Throws an exception if a 
	 * start tag is seen before an end tag, or if the end tag seen does not 
	 * match the expected name.
	 *
	 * @param tag element name expected
	 * @return content text with whitespace stripped
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException if expected element not found, or if
	 * other parse error
	 */

	protected String parseEndTag(String tag) 
		throws IOException, XmlPullParserException {
		m_buffer.setLength(0);
		while (true) {
			switch (m_parser.next()) {
				
				case XmlPullParser.CONTENT:
					m_buffer.append(m_parser.readContent());
					break;
				
				case XmlPullParser.END_TAG:
					m_parser.readEndTag(m_endTag);
					if (m_endTag.getLocalName().equals(tag)) {
						return m_buffer.toString().trim();
					}
					// fall through for error handling
				
				case XmlPullParser.START_TAG:
				case XmlPullParser.END_DOCUMENT:
					throw new XmlPullParserException
						("Missing expected end tag " + tag);

			}
		}
	}
	
	/**
	 * Parse element with text content, returning the content with whitespace
	 * trimmed. Throws an error if the element start tag is not the next tag
	 * seen, or if any child elements are present in the element.
	 *
	 * @param tag element name expected
	 * @return content text with whitespace stripped
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException if expected element not found, or if
	 * other parse error
	 */

	protected String parseElementContent(String tag) 
		throws IOException, XmlPullParserException {
		parseStartTag(tag);
		return parseEndTag(tag);
	}
	
	/**
	 * Get attribute value from current start tag. Throws an error if the
	 * attribute value is not found in the start tag.
	 *
	 * @param name attribute name to be found
	 * @return attribute value text
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException if attribute not present
	 */

	protected String attributeValue(String name) 
		throws IOException, XmlPullParserException {
		String value = m_startTag.getAttributeValueFromName(null, name);
		if (value == null) {
			throw new XmlPullParserException("Missing attribute " + name);
		} else {
			return value;
		}
	}
	
	/**
	 * Parse and record tracking information. Uses known structure of
	 * tracking information fragment to get the information, recording the
	 * agent information directly and returning the tracking information as
	 * a whole.
	 *
	 * @return tracking information
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException if parse error
	 */

	protected TrackingData parseTracking() 
		throws IOException, XmlPullParserException {
		
		// read id attribute from root element start tag
		TrackingData data = new TrackingData();
		parseStartTag(TRACKING_ELEMENT_NAME);
		data.m_id = attributeValue(ID_ATTRIBUTE_NAME);
		
		// read time as content of its own element
		data.m_time = parseElementContent(TIME_ELEMENT_NAME);
		
		// read seller agent information
		parseStartTag(SELLER_ELEMENT_NAME);
		data.m_seller = attributeValue(IDENT_ATTRIBUTE_NAME);
		data.m_isDirectSeller = 
			"direct".equals(attributeValue(TYPE_ATTRIBUTE_NAME));
		parseEndTag(SELLER_ELEMENT_NAME);
		
		// read buyer agent information
		parseStartTag(BUYER_ELEMENT_NAME);
		data.m_buyer = attributeValue(IDENT_ATTRIBUTE_NAME);
		data.m_isDirectBuyer = 
			"direct".equals(attributeValue(TYPE_ATTRIBUTE_NAME));
		parseEndTag(BUYER_ELEMENT_NAME);
		
		// read exchange identifier as content of its own element
		data.m_exchange = parseElementContent(EXCHANGE_ELEMENT_NAME);
		
		// finish with closing tag for root element
		parseEndTag(TRACKING_ELEMENT_NAME);
		
		// record agent information before returning
		AgentTrack.recordAgent(data.m_buyer, !data.m_isDirectBuyer, false);
		AgentTrack.recordAgent(data.m_seller, !data.m_isDirectSeller, true);
		return data;
	}
	
	/**
	 * Parse and record stock trade information. Uses known structure of
	 * stock trade fragment to get the information, then reports it directly.
	 *
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException if parse error
	 */

	protected void parseStockTrade() 
		throws IOException, XmlPullParserException {
		String symbol = parseElementContent(SYMBOL_ELEMENT_NAME);
		TrackingData tracking = parseTracking();
		double price = 
			Double.parseDouble(parseElementContent(PRICE_ELEMENT_NAME));
		int shares = 
			Integer.parseInt(parseElementContent(QUANTITY_ELEMENT_NAME));
		StockTrack.recordTrade(symbol, tracking.m_time, price, shares);
	}
	
	/**
	 * Parse and record option trade information. Uses known structure of
	 * option trade fragment to get the information, then reports it directly.
	 *
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException if parse error
	 */

	protected void parseOptionTrade()
		throws IOException, XmlPullParserException {
		String symbol = parseElementContent(SYMBOL_ELEMENT_NAME);
		TrackingData tracking = parseTracking();
		boolean call = parseElementContent(TYPE_ELEMENT_NAME).equals("call");
		int strike = 
			Integer.parseInt(parseElementContent(STRIKE_ELEMENT_NAME));
		int month = 
			Integer.parseInt(parseElementContent(EXPIRE_ELEMENT_NAME));
		double trade = 
			Double.parseDouble(parseElementContent(TRADE_ELEMENT_NAME));
		int shares = 
			Integer.parseInt(parseElementContent(QUANTITY_ELEMENT_NAME));
		OptionTrack.recordTrade(symbol, call, month, strike, tracking.m_time, 
			trade, shares);
	}

	/**
	 * Parse and process trade history information stream.
	 *
	 * @param in XML document reader
	 * @throws IOException if error reading document
	 * @throws XmlPullParserException on document parse error
	 */

	public void parse(Reader in)
		throws IOException, XmlPullParserException {
		
		// set document source for parse
		m_parser.reset();
		m_parser.setInput(in);
		
		// main pull parsing loop
		byte type;
		while ((type = m_parser.next()) != XmlPullParser.END_DOCUMENT) {
			
			// ignore everything other than a start tag
			if (type == XmlPullParser.START_TAG) {
				
				// process the start tags we're interested in
				m_parser.readStartTag(m_startTag);
				String lname = m_startTag.getLocalName();
				if (lname.equals(STOCK_ELEMENT_NAME)) {
					parseStockTrade();
				} else if (lname.equals(OPTION_ELEMENT_NAME)) {
					parseOptionTrade();
				}
				
			}
		}
	}
}
