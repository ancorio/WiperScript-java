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
		if (call.getParamsCount() != 1) {
			invalidParamsCount(call.getParamsCount());
		}
		Var var = call.getParam(0);
		if (var.getVarType() == VarType.FLOAT) {
			Double d = (Double) var.getValue();
			return new IntegerVar((long)(0.5 + d.doubleValue()));
		}
		invalidParamType(0, var);
		return null;
	}

}
