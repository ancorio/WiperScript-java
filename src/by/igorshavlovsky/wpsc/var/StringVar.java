package by.igorshavlovsky.wpsc.var;

public class StringVar extends Var<StringVar, String> {

	private String value;

	public StringVar(String value) {
		this(null, value);
	}
	
	public StringVar(String name, String value) {
		super(name);
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
					return new FloatVar(val);
				} catch (NumberFormatException e) {
					throw new VarException("Cannot cast " + getDetails() + " to " + type);
				}
			case NULL:
				return new NullVar();
			case INTEGER:
				try {
					long val = Long.parseLong(value);
					return new IntegerVar(val);
				} catch (NumberFormatException e) {
					throw new VarException("Cannot cast " + getDetails() + " to " + type);
				}
		}
		throw new VarException("Cannot cast " + getDetails() + " to " + type);
	}

	@Override
	public String stringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StringVar copy() {
		return new StringVar(value);
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}
}
