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
import java.util.*;

/**
 * Stock tracking information class.
 *
 * @author Dennis M. Sosnoski
 * @version 1.0
 */

public class StockTrack
{
	/** Map of stock symbols to tracking information. */
	protected static HashMap s_symbolMap = new HashMap();
	
	/** Stock symbol. */
	protected String m_stockSymbol;
	
	/** Time of last trade. */
	protected String m_lastTime;
	
	/** Highest price for trade. */
	protected double m_highPrice;
	
	/** Lowest price for trade. */
	protected double m_lowPrice;
	
	/** Last price for trade. */
	protected double m_lastPrice;
	
	/** Total number of shares traded. */
	protected int m_totalShares;
	
	/** Total dollar volume of all trades. */
	protected int m_totalDollars;
	
	protected StockTrack(String sym) {
		m_stockSymbol = sym;
		m_lowPrice = Double.MAX_VALUE;
	}
	
	public String getSymbol() {
		return m_stockSymbol;
	}
	
	public String getLastTime() {
		return m_lastTime;
	}
	
	public double getHighPrice() {
		return m_highPrice;
	}
	
	public double getLowPrice() {
		return m_lowPrice;
	}
	
	public double getLastPrice() {
		return m_lastPrice;
	}
	
	public int getShareVolume() {
		return m_totalShares;
	}
	
	public int getDollarVolume() {
		return m_totalDollars;
	}
	
	public static StockTrack getTrack(String sym) {
		StockTrack track = (StockTrack)s_symbolMap.get(sym);
		if (track == null) {
			track = new StockTrack(sym);
			s_symbolMap.put(sym, track);
		}
		return track;
	}
	
	public static void recordTrade(String sym, String time, double price,
		int shares) {
		StockTrack track = getTrack(sym);
		track.m_lastTime = time;
		if (track.m_highPrice < price) {
			track.m_highPrice = price;
		}
		if (track.m_lowPrice > price) {
			track.m_lowPrice = price;
		}
		track.m_lastPrice = price;
		track.m_totalShares += shares;
		track.m_totalDollars += shares*price;
	}
}
