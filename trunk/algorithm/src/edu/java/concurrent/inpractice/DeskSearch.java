package edu.java.concurrent.inpractice;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DeskSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>(1000);
		(new Thread(new FileCrawler(queue))).start();
		(new Thread(new Indexer(queue))).start();

	}
	
	public static class FileCrawler implements Runnable {
		private BlockingQueue<File> queue;
		public FileCrawler(BlockingQueue<File> queue) {
			this.queue = queue;
		}
		
		public void run() {
			crawl(new File("/"));	
		}
		
		public void crawl(File file) {
			File[] entries = file.listFiles();
			if (entries!=null) {
				for (File f: entries) {
					if (f.isDirectory())
						crawl(f);
					else {
						System.out.println("Craweling file: " + f.getAbsolutePath());
						queue.add(f);
					}
				}
			}
		}
	}
	
	public static class Indexer implements Runnable {
		private final BlockingQueue<File> queue;
		
		public Indexer(BlockingQueue<File> queue) {
			this.queue = queue;
		}
		
		public void run() {
			try {
				while (true)
					indexFile(queue.take());
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}

		private void indexFile(File take) {
			System.out.println("Indexing file: " + take.getAbsolutePath());
			
		}
		
	}
	

}
