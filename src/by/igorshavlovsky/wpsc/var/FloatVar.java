package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Run;

public class FloatVar extends Var<FloatVar> {

	private double value;

	public FloatVar(Run run, double value) {
		super(run);
		this.value = value;
	}

	@Override
	public VarType getVarType() {
		return VarType.FLOAT;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case STRING:
				throw new VarException("Cannot cast " + getDetails() + " to " + type + ". Use formatting macroses.");
			case FLOAT:
				return copy();
			case INTEGER:
				throw new VarException("Cannot cast " + getDetails() + " to " + type + ". Use rounding macroses.");
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public FloatVar copy() {
		return new FloatVar(run, value);
	}

	public double doubleValue() {
		return value;
	}
	
	protected String value() {
		return String.valueOf(value);
	}
}
