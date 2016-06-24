package by.igorshavlovsky.wpsc.preproc;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.var.Var;

public class VarOperation extends Operation {
	
	public static enum  Scope {
		NONE,
		LOCAL,
		GLOBAL
	}

	private Scope scope;
	private String name;
	private Var value;

	public VarOperation(Var value, ScriptLink link) {
		super(link);
		this.value = value;
		this.scope = Scope.NONE;
		this.name = null;
	}

	public VarOperation(Scope scope, String name, ScriptLink link) {
		super(link);
		this.scope = scope;
		this.name = name;
		this.value = null;
	}
	
	@Override
	public Var resolve(Call call) {
		switch (scope) {
			case NONE:
				if (this.value == null) {
					throw new RunException("Cannot find variable", call.getRun(), this);	
				}
				return value;
			case GLOBAL: {
				Var result = call.getRun().getRootScope().getVarPtr(name);
				if (result == null) {
					throw new RunException("Cannot find variable", call.getRun(), this);
				}
				return result;
			}
			case LOCAL: {
				Var result = call.getScope().getVarPtr(name);
				if (result == null) {
					throw new RunException("Cannot find variable", call.getRun(), this);
				}
				return result;
			}
				
		}
		throw new RunException("Cannot resolve variable", call.getRun(), this);
	}
	
	@Override
	public String toString() {
		if (value != null) {
			return value.getDetails();
		} else {
			return "[" + scope + ":" + name + "]";
		}
	}

}
