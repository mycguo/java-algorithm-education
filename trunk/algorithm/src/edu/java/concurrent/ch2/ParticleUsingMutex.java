package edu.java.concurrent.ch2;

import java.awt.Graphics;
import java.util.Random;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class ParticleUsingMutex {

	protected int x;
	protected int y;
	protected final Random rng = new Random();
	protected final Mutex mutex = new Mutex(); 
	
	public ParticleUsingMutex(int initialX, int initialY) {
		x = initialX;
		y = initialY;
	}
	
	public void move() {
		try {
			mutex.acquire();
			try {
				x += rng.nextInt(10) -5;
				y += rng.nextInt(20) -10;
			} finally {
				mutex.release();
			}
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			
		}
	}
	
	public void draw(Graphics g) {
		int lx,ly;
		try {
			mutex.acquire();
			try {
				lx=x;ly=y;
			} finally {
				mutex.release();
			}
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
		
		g.drawRect(lx, ly, 10, 10);
	}	
	
}
