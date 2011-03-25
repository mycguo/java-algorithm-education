package edu.java.concurrent.ch1;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

public class ParticleCanvas extends Canvas {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8165991126131169209L;
	
	private Particle[] particles = new Particle[0];
	
	ParticleCanvas(int size) {
		setSize(new Dimension(size,size));
	}
	
	protected synchronized void setP(Particle[] p) {
		particles = p;
	}

	protected synchronized Particle[] getP() {
		return particles;
	}
	
	@Override
	public void paint(Graphics g) {
		Particle[] p = getP();
		for (Particle pi: p) {
			pi.draw(g);
		}
	}
}
