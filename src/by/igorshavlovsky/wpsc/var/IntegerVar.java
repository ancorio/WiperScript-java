package by.igorshavlovsky.wpsc.var;

public class IntegerVar extends Var<IntegerVar, Long> {

	private long value;

	public IntegerVar(long value) {
		this(null, value);
	}
	
	public IntegerVar(String name, long value) {
		super(name);
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
				return new StringVar(String.valueOf(value));
			case FLOAT:
				return new FloatVar((double)value);
			case NULL:
				return new NullVar();
			case INTEGER:
				return copy();
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public String stringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IntegerVar copy() {
		return new IntegerVar(value);
	}

	@Override
	public Long getValue() {
		return Long.valueOf(value);
	}

	@Override
	public void setValue(Long value) {
		this.value = value.longValue();
	}


}
