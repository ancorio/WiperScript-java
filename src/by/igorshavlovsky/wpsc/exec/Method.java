package by.igorshavlovsky.wpsc.exec;

import by.igorshavlovsky.wpsc.var.Var;

abstract public class Method {
	
	private String name;

	public Method(String name) {
		super();
		this.name = name;
	}
	
	protected void invalidParamsCount(int count) {
		throw new RunException("Invalid params count (" + count + ") for method: " + name);
	}

	protected void invalidParamType(int i, Var var) {
		throw new RunException("Invalid param (" + i + ") type: " + var + " for method: " + name);
	}
	
	abstract public Var call(Call call);

	public String getName() {
		return name;
	}

	public String toString() {
		return getName();
	}
	
}
