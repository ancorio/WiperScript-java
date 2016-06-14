package by.igorshavlovsky.wpsc.exec;

import by.igorshavlovsky.wpsc.var.Var;

public class Param {
	
	private int ordinal;

	private Call call;
	
	private String content;
	
	private Var result = null;
	
	
	
	public Param(int ordinal, Call call, String content) {
		super();
		this.ordinal = ordinal;
		this.call = call;
		this.content = content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Var getResult() {
		if (result != null) {
			return result;
		}
		result = call.getRun().call(new CustomMethod("Param #" + ordinal + " for (" + call.getMethod().getName() + ")", content), ""); 
		return result;
	}
}
