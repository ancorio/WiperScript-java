package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.BooleanVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.StringVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodIf extends MethodOperation {
	
	public MethodIf() {
		super("if", null);
	}
	
	@Override
	public Var resolve(Call call) {
		BooleanVar cond = call.getParamUnwrapped(0).asBoolean();
		if (cond.booleanValue()) {
			BlockVar then = call.getParamUnwrapped(1).asBlock();
			return then.getOperation().resolve(call.getBlockScope(this));
		} else {
			if (call.getParamsCount() < 3) {
				return new NullVar(call.getRun());
			}
			BlockVar elseBlock = call.getParamUnwrapped(2).asBlock();
			return elseBlock.getOperation().resolve(call.getBlockScope(this));
		}
	}

	public boolean isBlockScope() {
		return false;
	}
	
}
