package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.exec.Script;

public class StringVar extends Var<StringVar> {

	private String value;

	public StringVar(Run run, String value) {
		super(run);
		this.value = value;
	}

	@Override
	public VarType getVarType() {
		return VarType.STRING;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case STRING:
				return copy();
			case FLOAT:
				try {
					double val = Double.parseDouble(value);
					return new FloatVar(run, val);
				} catch (NumberFormatException e) {
					throw new VarException("Cannot cast " + getDetails() + " to " + type);
				}
			case INTEGER:
				try {
					long val = Long.parseLong(value);
					return new IntegerVar(run, val);
				} catch (NumberFormatException e) {
					throw new VarException("Cannot cast " + getDetails() + " to " + type);
				}
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public StringVar copy() {
		return new StringVar(run, value);
	}
	
	public String stringValue() {
		return value;
	}
	

	protected String value() {
		return value;
	}

}
