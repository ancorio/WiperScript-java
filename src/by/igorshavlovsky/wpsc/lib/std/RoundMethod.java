package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class RoundMethod extends Method {
	
	public RoundMethod() {
		super("round");
	}
	
	@Override
	public Var call(Call call) {
		return new IntegerVar(call.getRun(), (long)(0.5 + call.getParamUnwrapped(0).asFloat().doubleValue()));
	}

}
