package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Preprocessor;
import by.igorshavlovsky.wpsc.exec.Script;

public class BlockVar extends Var<BlockVar, Script> {

	private Script value;

	public BlockVar(String value) {
		this(null, value);
	}
	
	public BlockVar(String name, String value) {
		super(name);
		this.value = Preprocessor.prepare(value, true);
	}

	@Override
	public VarType getVarType() {
		return VarType.BLOCK;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case BLOCK:
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
	public BlockVar copy() {
		return new BlockVar(value.getScript());
	}

	@Override
	public Script getValue() {
		return value;
	}

	@Override
	public void setValue(Script value) {
		this.value = value;
	}
}