package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Run;

public class BooleanVar extends Var<BooleanVar> {

	private boolean value;
	
	public BooleanVar(Run run, boolean value) {
		super(run);
		this.value = value;
	}

	@Override
	public VarType getVarType() {
		return VarType.BOOLEAN;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case BOOLEAN:
				return copy();
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public BooleanVar copy() {
		return new BooleanVar(run, value);
	}
	
	public boolean booleanValue() {
		return value;
	}

	protected String value() {
		return String.valueOf(value);
	}

}