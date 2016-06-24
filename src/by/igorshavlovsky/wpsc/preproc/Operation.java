package by.igorshavlovsky.wpsc.preproc;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.Var;

public abstract class Operation {
	
	private ScriptLink link;
	
	public Operation(ScriptLink link) {
		super();
		this.link = link;
	}

	public abstract Var resolve(Call call);
	
	public boolean isOperator() {
		return false;
	}

	public ScriptLink getLink() {
		return link;
	}

	public void setLink(ScriptLink link) {
		this.link = link;
	}
	
	
	
}
