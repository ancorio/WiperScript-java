package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.exec.Script;

public class IntegerVar extends Var<IntegerVar> {

	private long value;

	public IntegerVar(Run run, long value) {
		super(run);
		this.value = value;
	}

	@Override
	public VarType getVarType() {
		return VarType.INTEGER;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case STRING:
				return new StringVar(run, String.valueOf(value));
			case FLOAT:
				return new FloatVar(run, (double)value);
			case INTEGER:
				return copy();
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public IntegerVar copy() {
		return new IntegerVar(run, value);
	}

	public long longValue() {
		return value;
	}

	protected String value() {
		return String.valueOf(value);
	}

}
