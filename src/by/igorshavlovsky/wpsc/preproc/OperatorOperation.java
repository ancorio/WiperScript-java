package by.igorshavlovsky.wpsc.preproc;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Operator;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.var.Var;

public class OperatorOperation extends Operation {

	private Operator operator;
	
	public OperatorOperation(Operator operator, ScriptLink link) {
		super(link);
		this.operator = operator;
	}

	public Var resolve(Call call) {
		throw new RunException("Operator operations cannot be called by themselfes, it's SeqOperation's job.", call.getRun(), this);
	}
	
	@Override
	public boolean isOperator() {
		return true;
	}
	
	public Operator getOperator() {
		return operator;
	}
	
	@Override
	public String toString() {
		return "+";
	}

}
