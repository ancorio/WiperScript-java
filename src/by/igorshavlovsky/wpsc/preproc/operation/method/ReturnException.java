package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.Var;

public class ReturnException extends RuntimeException {

	private Var result;
	
	private Call call;

	public ReturnException(Var result, Call call) {
		super();
		this.result = result;
	}

	public Var getResult() {
		return result;
	}

	public void setResult(Var result) {
		this.result = result;
	}

	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}
	
}
