package by.igorshavlovsky.wpsc.preproc.operation.method;

import java.util.Date;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodLog extends MethodOperation {
	
	public MethodLog() {
		super("log", null);
	}
	
	@Override
	public Var resolve(Call call) {
		for (int i = 0; i < call.getParamsCount(); i++) {
			System.out.println(new Date() + ": " + call.getParamUnwrapped(i));
		}
		return new NullVar(call.getRun());
	}

	public boolean isBlockScope() {
		return false;
	}
	
}
