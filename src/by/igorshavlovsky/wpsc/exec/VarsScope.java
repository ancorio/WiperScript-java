package by.igorshavlovsky.wpsc.exec;

import java.util.HashMap;
import java.util.Map;

import by.igorshavlovsky.wpsc.var.PtrVar;
import by.igorshavlovsky.wpsc.var.Var;

public class VarsScope {
	
	private Map<String, Var> vars = new HashMap<String, Var>(8);

	public Map<String, Var> getVarsPrivate() {
		return vars;
	}
	
	public PtrVar getVarPtr(String name) {
		return new PtrVar(name, this);
	}
}
