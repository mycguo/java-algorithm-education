package edu.java.xml.reflection;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Understand Proxy class
 * http://www.javaworld.com/javaworld/jw-11-2000/jw-1110-proxy.html
 * 
 * @author CGuo
 * 
 */

public class ViewProxy implements InvocationHandler {
	private Map<String,String> map;

	public static void main(String[] argc) {
		HashMap<String,String> identity = new HashMap<String,String>();
		IPerson person = (IPerson) ViewProxy.newInstance(identity,
				new Class[] { IPerson.class });
		person.setName("Charles Guo");

		IEmployee empolyee = (IEmployee) Proxy.newProxyInstance(ViewProxy.class.getClassLoader(), new Class[] {IEmployee.class}, new ViewProxy(identity));
		empolyee.setPhoneNumber("408-7466425");

		System.out.println(empolyee.getName());

	}

	public static Object newInstance(Map<String,String> map, Class<?>[] interfaces) {
		return Proxy.newProxyInstance(ViewProxy.class.getClassLoader(),
				interfaces, new ViewProxy(map));
	}

	public ViewProxy(Map<String, String> map) {
		this.map = map;
	}

	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		String methodName = m.getName();
		if (methodName.startsWith("get")) {
			String name = methodName.substring(methodName.indexOf("get") + 3);
			return map.get(name);
		} else if (methodName.startsWith("set")) {
			String name = methodName.substring(methodName.indexOf("set") + 3);
			map.put(name, (String) args[0]);
			return null;
		} else if (methodName.startsWith("is")) {
			String name = methodName.substring(methodName.indexOf("is") + 2);
			return (map.get(name));
		}
		return null;
	}

	public interface IPerson {
		public String getName();

		public String getAddress();

		public String getPhoneNumber();

		public void setName(String name);

		public void setAddress(String address);

		public void setPhoneNumber(String phoneNumber);
	}

	public interface IEmployee extends IPerson {
		public String getSSN();

		public String getDepartment();

		public Float getSalary();

		public void setSSN(String ssn);

		public void setDepartment(String department);

		public void setSalary(String salary);
	}

	public interface IManager extends IEmployee {
		public String getTitle();

		public String[] getDepartments();

		public void setTitle(String title);

		public void setDepartments(String[] departments);
	}
}
