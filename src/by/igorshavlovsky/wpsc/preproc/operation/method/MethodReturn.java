package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodReturn extends MethodOperation {
	
	public MethodReturn() {
		super("return", null);
	}
	
	@Override
	public Var resolve(Call call) {
		if (call.getParamsCount() == 0) {
			throw new ReturnException(null, call);
		} else {
			throw new ReturnException(call.getParamUnwrapped(0), call);
		}
	}

	public boolean isBlockScope() {
		return false;
	}
	
}
