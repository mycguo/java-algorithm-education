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
 * Option tracking information class.
 *
 * @author Dennis M. Sosnoski
 * @version 1.0
 */



public class OptionTrack
{
	/** Map of stock symbols to tracking information. */
	protected static HashMap s_symbolMap = new HashMap();
	
	/** Option stock symbol. */
	protected String m_stockSymbol;
	
	/** Time of last trade. */
	protected String m_lastTime;
	
	/** Call option flag. */
	protected boolean m_isCall;
	
	/** Option strike price. */
	protected int m_strikePrice;
	
	/** Month of expiration. */
	protected int m_expireMonth;
	
	/** Highest price for trade. */
	protected double m_highPrice;
	
	/** Lowest price for trade. */
	protected double m_lowPrice;
	
	/** Last price for trade. */
	protected double m_lastPrice;
	
	/** Total number of options traded. */
	protected int m_totalOptions;
	
	/** Total dollar volume of all trades. */
	protected int m_totalDollars;
	
	protected OptionTrack(String sym, boolean call, int month, int strike) {
		m_stockSymbol = sym;
		m_isCall = call;
		m_expireMonth = month;
		m_strikePrice = strike;
		m_lowPrice = Double.MAX_VALUE;
	}
	
	public String getSymbol() {
		return m_stockSymbol;
	}
	
	public boolean isCall() {
		return m_isCall;
	}
	
	public String getLastTime() {
		return m_lastTime;
	}
	
	public int getStrikePrice() {
		return m_strikePrice;
	}
	
	public int getExpireMonth() {
		return m_expireMonth;
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
	
	public int getOptionVolume() {
		return m_totalOptions;
	}
	
	public int getDollarVolume() {
		return m_totalDollars;
	}
	
	public static OptionTrack getTrack(String sym, boolean call, int month,
		int strike) {
		
		// build a key for this option; this is arbitrary, but unique
		String key = sym + (call ? '<' : '>') + month + ':' + strike;
		OptionTrack track = (OptionTrack)s_symbolMap.get(key);
		if (track == null) {
			track = new OptionTrack(sym, call, month, strike);
			s_symbolMap.put(key, track);
		}
		return track;
	}
	
	public static void recordTrade(String sym, boolean call, int month,
		int strike, String time, double price, int options) {
		OptionTrack track = getTrack(sym, call, month, strike);
		track.m_lastTime = time;
		if (track.m_highPrice < price) {
			track.m_highPrice = price;
		}
		if (track.m_lowPrice > price) {
			track.m_lowPrice = price;
		}
		track.m_lastPrice = price;
		track.m_totalOptions += options;
		track.m_totalDollars += options*price;
	}
}
