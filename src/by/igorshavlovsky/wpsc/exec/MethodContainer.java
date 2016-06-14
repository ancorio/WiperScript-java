package by.igorshavlovsky.wpsc.exec;

import java.util.HashMap;
import java.util.Map;

public class MethodContainer {
	
	private Map<String, Method> methods = new HashMap<String, Method>(8);

	public Map<String, Method> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, Method> methods) {
		this.methods = methods;
	}

}
