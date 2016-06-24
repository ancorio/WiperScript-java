package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.exec.RunException;

abstract public class Var <B extends Var<B>> {
	
	protected Run run;
	
	public Var(Run run) {
		super();
		this.run = run;
	}

	abstract public VarType getVarType();
	abstract public Var convertTo(VarType type);
	abstract public B copy();
	abstract protected String value();
	
	public Var unwrap() {
		return this;
	}
	
	public String getDetails() {
		return getVarType().toString() + "(" + value() + ")";
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

	public FloatVar asFloat() {
		if (getVarType() == VarType.FLOAT) {
			return (FloatVar)this;
		}
		throw new RunException(this.getDetails() + " is not a Float", run, null);
	}

	public IntegerVar asInteger() {
		if (getVarType() == VarType.INTEGER) {
			return (IntegerVar)this;
		}
		throw new RunException(this.getDetails() + " is not a Integer", run, null);
	}

	public BooleanVar asBoolean() {
		if (getVarType() == VarType.BOOLEAN) {
			return (BooleanVar)this;
		}
		throw new RunException(this.getDetails() + " is not a Boolean", run, null);
	}

	public StringVar asString() {
		if (getVarType() == VarType.STRING) {
			return (StringVar)this;
		}
		throw new RunException(this.getDetails() + " is not a String", run, null);
	}
	

	public BlockVar asBlock() {
		if (getVarType() == VarType.BLOCK) {
			return (BlockVar)this;
		}
		throw new RunException(this.getDetails() + " is not a Block", run, null);
	}

	public Run getRun() {
		return run;
	}
	
}
