package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.var.FloatVar;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class TruncMethod extends Method {
	
	public TruncMethod() {
		super("trunc");
	}
	
	@Override
	public Var call(Call call) {
		Var var = call.getParamUnwrapped(0);
		return new IntegerVar(call.getRun(), (long)var.asFloat().doubleValue());
	}

}
