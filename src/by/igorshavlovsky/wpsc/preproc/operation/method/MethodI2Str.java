package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.StringVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodI2Str extends MethodOperation {
	
	public MethodI2Str() {
		super("i2str", null);
	}
	
	@Override
	public Var resolve(Call call) {
		return new StringVar(call.getRun(), String.valueOf(call.getParamUnwrapped(0).asInteger().longValue()));
	}
	
}
