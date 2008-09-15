package edu.java.concurrent.ch1;

import java.awt.Graphics;
import java.util.Random;

public class Particle {

	protected int x;
	protected int y;
	protected final Random random = new Random();
	
	public Particle(int initX, int initY) {
		x = initX;
		y=  initY;
	}
	
	public synchronized void move() {
		x += random.nextInt(10) -5;
		y += random.nextInt(20) -10;
	}
	
	public void draw (Graphics g) {
		int lx, ly;
		synchronized(this) {
			lx =x;
			ly =y;
		}
		g.drawRect(lx, ly, 10, 10);
	}
}
