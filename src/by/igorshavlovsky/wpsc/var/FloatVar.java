package by.igorshavlovsky.wpsc.var;

public class FloatVar extends Var<FloatVar, Double> {

	private double value;

	public FloatVar(double value) {
		this(null, value);
	}
	
	public FloatVar(String name, double value) {
		super(name);
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
	public FloatVar copy() {
		return new FloatVar(value);
	}

	@Override
	public Double getValue() {
		return Double.valueOf(value);
	}

	@Override
	public void setValue(Double value) {
		this.value = value.doubleValue();  
	}

}
