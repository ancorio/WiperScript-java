package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Run;

public class NullVar extends Var<NullVar> {
	
	public NullVar(Run run) {
		super(run);
	}

	@Override
	public VarType getVarType() {
		return VarType.NULL;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case NULL:
				return copy();
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}
	
	@Override
	public NullVar copy() {
		return new NullVar(run);
	}

	protected String value() {
		return "";
	}
}
