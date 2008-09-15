package edu.java.concurrent.ch1;

import java.applet.Applet;

public class ParticleApplet extends Applet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6138301834791749137L;
	
	protected Thread[] threads = null;
	
	protected  ParticleCanvas canvas = new ParticleCanvas(200);
	
	@Override
	public void init() {
		add(canvas);
	}
	
	protected Thread makeThread(final Particle p) {
		return new Thread( new Runnable() {
			
			public void run() {
				try {
					for(;;) {
						p.move();
						canvas.repaint();
						Thread.sleep(200);
					}
				} catch (InterruptedException e) {
					return;
				}
				
			}
			
		});
				
	}
	@Override
	public synchronized void start() {
		int n =20;
		if (threads == null) {
			Particle[] p = new Particle[n];

			for (int i=0;i<n;i++) {
				p[i] = new Particle(50,50);
			}

			canvas.setP(p);
			
			threads = new Thread[n];
			for (int i=0; i< n; i++) {
				threads[i] = makeThread(p[i]);
				threads[i].start();
			}
			
		}
	}
	
	public synchronized void stop() {
		if (threads != null) {
			for (Thread t: threads) {
				t.interrupt();
			}
			threads = null;
		}
	}
	
	
}
