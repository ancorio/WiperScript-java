package by.igorshavlovsky.wpsc.exec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.PtrVar;


public class StackEntry {
	
	private Map<String, Method> methods = new HashMap<String, Method>(8);

	public Map<String, Method> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, Method> methods) {
		this.methods = methods;
	}
	
	public void loadMethod(Method method) {
		getMethods().put(method.getName(), method);
	}
	
	public void loadMethods(List <Method> methods) {
		for (Method method : methods) {
			getMethods().put(method.getName(), method);
		}
	}

}
