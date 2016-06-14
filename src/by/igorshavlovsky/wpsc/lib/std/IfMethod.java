package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class IfMethod extends Method {
	
	public IfMethod() {
		super("if");
	}
	
	@Override
	public Var call(Call call) {
		if (call.getParamsCount() != 3) {
			invalidParamsCount(call.getParamsCount());
		}
		Var var = call.getParamResult(0);
		if (var.getVarType() == VarType.BOOLEAN) {
			if (((Boolean)var.getValue()).booleanValue()) {
				return call.getParamResult(1);
			} else {
				return call.getParamResult(2);
			}
		}
		invalidParamType(0, var);
		return null;
	}

}

