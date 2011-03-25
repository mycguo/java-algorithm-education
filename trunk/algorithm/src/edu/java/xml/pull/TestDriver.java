/*
 * Copyright (c) 2002 Sosnoski Software Solutions, Inc.
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

/**
 * Test driver class. Just creates a parser and parses the sample document,
 * then prints stock summary information at the end.
 *
 * @author Dennis M. Sosnoski
 * @version 1.0
 */

public class TestDriver
{
	/**
	 * Main method to run test.
	 */
	
	public static void main(String[] argv) {
		try {
			
			// use XPP2 pull parser
			PullHandler handler = new PullHandler();
			handler.parse(new FileReader("c:/gig4/algorithm/src/edu/java/xml/pull/trades.xml"));
			
			// report information from document
			System.out.println("Completed parse");
			StockTrack stock = StockTrack.getTrack("SUNW");
			System.out.println(" SUNW traded " + stock.getShareVolume() +
				" shares at prices ranging from " + stock.getLowPrice() +
				" to " + stock.getHighPrice());
			stock = StockTrack.getTrack("MSFT");
			System.out.println(" MSFT traded " + stock.getShareVolume() +
				" shares at prices ranging from " + stock.getLowPrice() +
				" to " + stock.getHighPrice());
			OptionTrack option = OptionTrack.getTrack("SUNW", true, 9, 100);
			System.out.println(" SUNW September 100 call traded options on " +
				option.getOptionVolume() + " shares at prices ranging from " + 
				option.getLowPrice() + " to " + option.getHighPrice());
			AgentTrack agent = AgentTrack.getTrack("ABT");
			System.out.println(" ABT direct seller: " + 
				agent.getSellerDirectCount() + ", direct buyer: " +
				agent.getBuyerDirectCount() + ", seller agent: " +
				agent.getSellerAgentCount() + ", buyer agent: " +
				agent.getBuyerAgentCount());
				
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
			System.exit(0);
		}
	}
}
