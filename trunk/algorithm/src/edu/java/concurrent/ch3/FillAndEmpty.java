package edu.java.concurrent.ch3;

import java.util.concurrent.Exchanger;

import EDU.oswego.cs.dl.util.concurrent.Rendezvous;

public class FillAndEmpty {

	static final int SIZE = 1024;
	Rendezvous exchanger = new Rendezvous(2);
	protected byte readByte() { return "empty".getBytes()[1];};
	protected void useByte(byte b) {
		//nothing
	}
	
	public void start() {
		new Thread(new FillingLoop()).start();
		new Thread(new EmptyingLoop()).start();
	}
	
	
	class FillingLoop implements Runnable {
		public void run() {
			byte[] buffer = new byte[SIZE];
			int position =0;
			
			try {
				for (;;) {
					if (position == SIZE) {
						buffer = (byte[]) (exchanger.rendezvous(buffer));
						position =0;
					}
					buffer[position++] = readByte();
				}
			} catch(Exception e) {};
		}
	}
	
	class EmptyingLoop implements Runnable {
		public void run() {
			//
		};
	}
	
	
}
