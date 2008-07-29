package edu.effective.java.generics;

import java.util.HashMap;
import java.util.Map;

//typesafe heterogeneous container pattern
public class Favorites {
	private Map<Class<?>, Object> favorites = new HashMap<Class<?>, Object>();
	
	public <T> void putFavorite(Class<T> type, T instance) {
		if (type == null)
			throw new NullPointerException(" type is null");
		favorites.put(type, type.cast(instance));
	}
	
	public <T> T getFavoriate(Class<T> type) {
		return type.cast(favorites.get(type));
	}
}
