package by.igorshavlovsky.wpsc.var;

abstract public class Var <B extends Var<B, V>, V> {
	
	private String name;
	
	public Var(String name) {
		super();
		this.name = name;
	}
	
	abstract public VarType getVarType();
	abstract public Var convertTo(VarType type);
	abstract public String stringValue();
	abstract public B copy();
	abstract public V getValue();
	abstract public void setValue(V value);
	public String getDetails() {
		return getVarType().toString() + "(" + getValue() + ")";
	}
	public String toString() {
		return getDetails();
	}
	public boolean isDecimal() {
		switch (getVarType()) {
			case INTEGER:
			case FLOAT:
				return true;
		}
		return false;
	}
	
}
