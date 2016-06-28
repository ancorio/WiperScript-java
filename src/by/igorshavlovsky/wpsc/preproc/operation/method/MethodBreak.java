package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodBreak extends MethodOperation {
	
	public MethodBreak() {
		super("break", null);
	}
	
	@Override
	public Var resolve(Call call) {
		throw new BreakException(this);
	}

	public boolean isBlockScope() {
		return false;
	}
	
}
