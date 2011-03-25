package edu.java.concurrent.inpractice;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class PreLoader {

	private final FutureTask<ProductInfo> future = new FutureTask<ProductInfo>(
			new Callable<ProductInfo>() {
				public ProductInfo call() {
					return loadProductInfo();
				}
			});

	public ProductInfo loadProductInfo() {
		try {
			return future.get();
		} catch (ExecutionException e) {

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return null;
	}

	public class ProductInfo {
	}

	{
		// load something
	}

}
