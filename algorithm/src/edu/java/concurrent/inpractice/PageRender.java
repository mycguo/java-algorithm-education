package edu.java.concurrent.inpractice;

import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class PageRender {

	private final ExecutorService executor;
	PageRender(ExecutorService executor) {
		this.executor = executor;
	}
	void renderPage(CharSequence source) throws Throwable {
		List<ImageInfo> info = scanForImageInfo(source);
		CompletionService<ImageData> completionService = new ExecutorCompletionService<ImageData>(executor);
		for (final ImageInfo i: info) {
			completionService.submit(new Callable<ImageData>(){
				public ImageData call() {
					return i.downloadData();
				}
			});
		}
		
		renderText(source);
		
		
		try {
			for (int t=0,n=info.size();t<n;t++){
				Future<ImageData> f = completionService.take();
				ImageData data = f.get();
				renderImage(data);
				
			}
		} catch (InterruptedException e) {
			
		} catch (ExecutionException e) {
			throw e.getCause();
		}
	}
	
	private void renderImage(ImageData data) {
		// TODO Auto-generated method stub
		
	}
	private void renderText(CharSequence source) {
		// TODO Auto-generated method stub
		
	}
	private List<ImageInfo> scanForImageInfo(CharSequence source) {
		// TODO Auto-generated method stub
		return null;
	}

	private class ImageInfo {

		public ImageData downloadData() {
			// TODO Auto-generated method stub
			return null;
		}};
	private class ImageData {};
}
