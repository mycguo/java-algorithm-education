package edu.effective.java.construct;

/**
 * Example of how to use BuilderPattern
 * The Key: construct using builder, and builder return builder after each call
 * @author CGuo
 *
 */
public class NutritionFacts {
	
	private final int servingSize;
	private final int servings;
	private final int calories;
	
	public int getServingSize() {
		return servingSize;
	}

	public int getServings() {
		return servings;
	}

	public int getCalories() {
		return calories;
	}
	
	public NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
	}
	
	
	public static class Builder {
		private  int servingSize;
		private  int servings;
		
		//assign to default value
		private  int calories =0;
		
		public Builder(int servingSize, int serving) {
			this.servingSize = servingSize;
			this.servings = serving;
		}
		
		Builder calories(int cal) {
			calories = cal;
			return this;
		}
		
		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}
	
	public static void main(String[] args) {
		NutritionFacts coca = new NutritionFacts.Builder(100,20).calories(100).build();
		System.out.println(" coca calories: " + coca.getCalories());
	}

}
