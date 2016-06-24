package by.igorshavlovsky.wpsc.exec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;
import by.igorshavlovsky.wpsc.var.PtrVar;
import by.igorshavlovsky.wpsc.var.Var;

public class Scope {
	
	private Run run;
	
	public Scope(Run run) {
		super();
		this.run = run;
	}
	
	private Map<String, Var> vars = new HashMap<String, Var>(8);

	public Map<String, Var> getVarsPrivate() {
		return vars;
	}
	
	public PtrVar getVarPtr(String name) {
		return new PtrVar(run, name, this, false);
	}
	
	private Map<String, MethodOperation> methods = new HashMap<String, MethodOperation>(8);

	public Map<String, MethodOperation> getMethods() {
		return methods;
	}

	public void setMethods(Map<String, MethodOperation> methods) {
		this.methods = methods;
	}
	
	public void loadMethod(MethodOperation method) {
		getMethods().put(method.getName(), method);
	}
	
	public void loadMethods(List <MethodOperation> methods) {
		for (MethodOperation method : methods) {
			getMethods().put(method.getName(), method);
		}
	}
}
