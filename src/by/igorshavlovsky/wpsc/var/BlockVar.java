package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.exec.Scope;
import by.igorshavlovsky.wpsc.preproc.ListOperation;

public class BlockVar extends Var<BlockVar> {

	private ListOperation operation;

	public BlockVar(Run run, ListOperation operation) {
		super(run);
		this.operation = operation;
	}

	@Override
	public VarType getVarType() {
		return VarType.BLOCK;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case BLOCK:
				return copy();
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}
	
	
	public ListOperation getOperation() {
		return operation;
	}

	@Override
	public BlockVar copy() {
		return new BlockVar(run, operation);
	}

	@Override
	protected String value() {
		return "{" + operation + "}";
	}
	
	private Call call = null;

	public Call getCall() {
		return call;
	}

	public void setCall(Call call) {
		this.call = call;
	}

}