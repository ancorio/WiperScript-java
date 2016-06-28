package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodWhile extends MethodOperation {
	
	public MethodWhile() {
		super("while", null);
	}
	
	@Override
	public Var resolve(Call call) {
		BlockVar cond = call.getParamUnwrapped(0).asBlock();
		BlockVar exec = call.getParamUnwrapped(1).asBlock();
		while (cond.getOperation().resolve(call.getBlockScope(this)).asBoolean().booleanValue()) {
			try {
				exec.getOperation().resolve(call.getBlockScope(this));				
			} catch (BreakException e) {
				break;
			}
		}
		return new NullVar(call.getRun());
	}

	public boolean isBlockScope() {
		return false;
	}
	
}
