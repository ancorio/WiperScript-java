package by.igorshavlovsky.wpsc.exec;

import by.igorshavlovsky.wpsc.var.Var;

public class Param {
	
	private int ordinal;

	private Call call;
	
	private Var result = null;
	
	public Param(int ordinal, Call call, Var result) {
		super();
		this.ordinal = ordinal;
		this.call = call;
		this.result = result;
	}

	public int getOrdinal() {
		return ordinal;
	}
	
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}
	
	public String toString() {
		return result.getDetails();
	}

	public Var getResult() {
		return result;
	}
}

