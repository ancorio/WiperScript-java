package by.igorshavlovsky.wpsc.var;

public class BooleanVar extends Var<BooleanVar, Boolean> {

	private boolean value;

	public BooleanVar(boolean value) {
		this(null, value);
	}
	
	public BooleanVar(String name, boolean value) {
		super(name);
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
			case NULL:
				return new NullVar();
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public String stringValue() {
		return String.valueOf(value);
	}

	@Override
	public BooleanVar copy() {
		return new BooleanVar(value);
	}

	@Override
	public Boolean getValue() {
		return Boolean.valueOf(value);
	}

	@Override
	public void setValue(Boolean value) {
		this.value = value.booleanValue();  
	}

}