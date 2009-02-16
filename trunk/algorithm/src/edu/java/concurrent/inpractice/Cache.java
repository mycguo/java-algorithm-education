package edu.java.concurrent.inpractice;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
/**Building an efficient, scalable cache, Page 108 Concurrency in Practice
 * 
 * @author CGuo
 *
 * @param <A>
 * @param <V>
 */
public class Cache<A, V> implements Computable<A, V> {
	private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
	private final Computable<A, V> c;

	public Cache(Computable<A, V> c) {
		this.c = c;
	}

	public V compute(final A args) {

		Future<V> result = cache.get(args);
		if (result == null) {
			Callable<V> eval = new Callable<V>() {
				public V call() throws Exception {
					return c.compute(args);
				};
			};
			FutureTask<V> ft = new FutureTask<V>(eval);
			// put it to cache, even before running

			result = cache.putIfAbsent(args, ft);
			// run it only when the key is not there
			if (result == null) {
				result = ft;
				ft.run();
			}
		}

		try {
			// get will wait for the response
			V value = result.get();
			return value;

		} catch (InterruptedException e) {
			cache.remove(args);
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			cache.remove(args);
			e.printStackTrace();
		}

		return null;

	}

}
