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
 * Buyer and seller agent tracking information class.
 *
 * @author Dennis M. Sosnoski
 * @version 1.0
 */

public class AgentTrack
{
	/** Map of party symbols to tracking information. */
	protected static HashMap s_symbolMap = new HashMap();
	
	/** Company symbol. */
	protected String m_companySymbol;
	
	/** Agent for seller count. */
	protected int m_sellerAgentCount;
	
	/** Agent for buyer count. */
	protected int m_buyerAgentCount;
	
	/** Direct seller count. */
	protected int m_sellerDirectCount;
	
	/** Direct buyer count. */
	protected int m_buyerDirectCount;
	
	protected AgentTrack(String sym) {
		m_companySymbol = sym;
	}
	
	public String getSymbol() {
		return m_companySymbol;
	}
	
	public int getSellerAgentCount() {
		return m_sellerAgentCount;
	}
	
	public int getBuyerAgentCount() {
		return m_buyerAgentCount;
	}
	
	public int getSellerDirectCount() {
		return m_sellerDirectCount;
	}
	
	public int getBuyerDirectCount() {
		return m_buyerDirectCount;
	}
	
	public static AgentTrack getTrack(String sym) {
		AgentTrack track = (AgentTrack)s_symbolMap.get(sym);
		if (track == null) {
			track = new AgentTrack(sym);
			s_symbolMap.put(sym, track);
		}
		return track;
	}
	
	public static void recordAgent(String sym, boolean agent,
		boolean seller) {
		AgentTrack track = getTrack(sym);
		if (agent) {
			if (seller) {
				track.m_sellerAgentCount++;
			} else {
				track.m_buyerAgentCount++;
			}
		} else {
			if (seller) {
				track.m_sellerDirectCount++;
			} else {
				track.m_buyerDirectCount++;
			}
		}
	}
}
