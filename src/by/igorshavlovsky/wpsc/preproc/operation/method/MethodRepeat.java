package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodRepeat extends MethodOperation {
	
	public MethodRepeat() {
		super("repeat", null);
	}
	
	@Override
	public Var resolve(Call call) {
		BlockVar operation = call.getParamUnwrapped(0).asBlock();
		while (true) {
			try {
				operation.getOperation().resolve(call.getBlockScope(this));				
			} catch (Exception e) {
				break;
			}
		}
		return new NullVar(call.getRun());
	}

	public boolean isBlockScope() {
		return false;
	}
	
}
