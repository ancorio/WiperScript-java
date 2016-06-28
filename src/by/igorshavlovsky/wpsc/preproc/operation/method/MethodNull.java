package by.igorshavlovsky.wpsc.preproc.operation.method;

import java.util.Date;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodNull extends MethodOperation {
	
	public MethodNull() {
		super("null", null);
	}
	
	@Override
	public Var resolve(Call call) {
		return new NullVar(call.getRun());
	}

	public boolean isBlockScope() {
		return false;
	}
	
}
