package by.igorshavlovsky.wpsc.var;

public class NullVar extends Var<NullVar, Object> {
	
	public NullVar() {
		this(null);
	}
	
	public NullVar(String name) {
		super(name);
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
			case FLOAT:
				return new FloatVar(0.0);
			case INTEGER:
				return new IntegerVar(0);
			case STRING:
				return new StringVar("");
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public String stringValue() {
		return "";
	}

	@Override
	public NullVar copy() {
		return new NullVar();
	}

	@Override
	public Object getValue() {
		return null;
	}

	@Override
	public void setValue(Object value) {
	}
	
}
