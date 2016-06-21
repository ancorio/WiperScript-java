package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Preprocessor;
import by.igorshavlovsky.wpsc.exec.Script;
import by.igorshavlovsky.wpsc.exec.StackEntry;
import by.igorshavlovsky.wpsc.exec.VarsScope;

public class PtrVar extends Var<PtrVar, Var> {

	private String name;
	private VarsScope scope;

	public PtrVar(String name, VarsScope scope) {
		this.name = name;
		this.scope = scope;
		if (getValue() == null) {
			setValue(new NullVar());
		}
	}

	@Override
	public VarType getVarType() {
		return VarType.PTR;
	}

	@Override
	public Var convertTo(VarType type) {
		switch (type) {
			case PTR:
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
	public PtrVar copy() {
		return new PtrVar(name, scope);
	}

	@Override
	public Var getValue() {
		return scope.getVarsPrivate().get(name);
	}

	@Override
	public void setValue(Var value) {
		scope.getVarsPrivate().put(name, value);
	}
}