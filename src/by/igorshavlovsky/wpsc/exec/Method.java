package by.igorshavlovsky.wpsc.exec;

import by.igorshavlovsky.wpsc.var.Var;

abstract public class Method {
	
	private String name;

	public Method(String name) {
		super();
		this.name = name;
	}
	
	abstract public Var call(Call call);

	public String getName() {
		return name;
	}

	public String toString() {
		return getName();
	}
	
}
