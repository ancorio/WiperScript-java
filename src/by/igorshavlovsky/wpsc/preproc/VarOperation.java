package by.igorshavlovsky.wpsc.preproc;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.Var;

public class VarOperation extends Operation {
	
	public static enum  VarScope {
		LOCAL,
		GLOBAL
	}
	

	public VarOperation(ScriptLink link, VarScope scope, Operation resolver) {
		super(link);
		this.scope = scope;
		this.resolver = resolver;
	}

	private VarScope scope;
	private Operation resolver;
	
	@Override
	public Var resolve(Call call) {
		Var result = resolver.resolve(call);
		switch (result.getVarType()) {
			case INTEGER: {
				if (scope == VarScope.GLOBAL) {
					throw new RunException("Global scope is now available for method params.", call.getRun(), this);
				}
				long val = result.asInteger().longValue();
				if (val == 0) {
					return new IntegerVar(call.getRun(), call.getParamsList().size());
				} else {
					return call.getParamUnwrapped((int)(val - 1));
				}
			}
			case STRING: {
				if (scope == VarScope.GLOBAL) {
					return call.getRun().getRootScope().getVarPtr(result.asString().stringValue());
				} else {
					return call.getScope().getVarPtr(result.asString().stringValue());
				}
			}
			default: {
				throw new RunException("Unsupported var resolver type: " + result.getVarType(), call.getRun(), this);
			}
		}
	}
	
	@Override
	public String toString() {
		if (scope == VarScope.GLOBAL) {
			return "$!" + resolver;
		}
		return "$" + resolver;
	}

}
