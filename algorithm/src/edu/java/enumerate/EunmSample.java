package edu.java.enumerate;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map.Entry;

/**
 * http://www.ibm.com/developerworks/java/library/j-tiger04195/index.html?S_TACT=105AGX02&S_CMP=EDU
 * @author cguo
 *
 */
public class EunmSample {
	
	public enum Size {
		
		Small(0.1), Miedum(0.5), Large(0.9);
		double factor;
		Size(double factor) {
			this.factor = factor;
		}
		
	}
	
	public enum ConstantSpecificBody {
		Small {
			public double getFactor() {
				return 0.8;
			}
		},
		Midium {
			public double getFactor() {
				return 1.0;
			}
		},
		Large;
		
		public double getFactor() {
			return 1.2;
		}
	}
	
	/**
	 * Page 165 Effective Java
	 * @param args
	 */
	public interface Operation {
		double apply(double x, double y);
	}
	
	public enum BasicOperation implements Operation {
		PLUS('+') {
			public double apply(double x, double y) {
				return x +y;
			}
		},
		MINUS('-') {
			public double apply(double x, double y) {
				return x -y;
			}
		},
		MULTIPLOY('*') {
			public double apply(double x, double y) {
				return x * y;
			}
		},
		DIVIDE('/') {
			public double apply(double x, double y) {
				return x / y;
			}
		};
		
		BasicOperation(char sign) {
			this.sign = sign;
		}
		@Override
		public String toString() {
			return sign+"";
		}
		private char sign;
		
	}
	
	private static <T extends Enum<T> & Operation> void test(Class<T> opSet, double x, double y) {
		for (Operation op: opSet.getEnumConstants()) {
			System.out.printf("%f %s %f = %f%n", x, op,y,op.apply(x, y));
		}
	}
		
	
	
	public  static void main(String[] args) {
		Size[] all = Size.values();
		for (Size s: all) {
			System.out.println(" name: " + s.name() + " ordinal: " + s.ordinal());
		}

		ConstantSpecificBody[] c = ConstantSpecificBody.values();
		for (ConstantSpecificBody s: c) {
			System.out.println(" name: " + s.name() + " ordinal: " + s.ordinal() + " factor: " + s.getFactor());
		}
		
		EnumMap<Size,Double> map = new EnumMap<Size, Double>(Size.class);
		map.put(Size.Small, 0.1);
		map.put(Size.Small, 0.2);
		map.put(Size.Miedum,0.5);
		
		for (Entry<Size,Double> e :   map.entrySet()) {
			System.out.println("name: " + e.getKey() + " value:" + e.getValue());
		}
		
		EnumSet<BasicOperation> set = EnumSet.allOf(BasicOperation.class);
		double x=2, y=1;
		for (BasicOperation o: set) {			
			System.out.println(x + o.toString() + y + "=" + o.apply(x, y));
		}
		
		test(BasicOperation.class,3, 2);
		
	}

}
