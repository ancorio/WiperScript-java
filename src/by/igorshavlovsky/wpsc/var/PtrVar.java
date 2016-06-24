package by.igorshavlovsky.wpsc.var;

import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.exec.Scope;

public class PtrVar extends Var<PtrVar> {

	private String name;
	private Scope scope;
	private boolean noUnwrap;

	public PtrVar(Run run, String name, Scope scope, boolean noUnwrap) {
		super(run);
		this.name = name;
		this.scope = scope;
		this.noUnwrap = noUnwrap;
	}

	@Override
	public VarType getVarType() {
		return VarType.PTR;
	}

	@Override
	public Var unwrap() {
		if (noUnwrap) {
			return new PtrVar(run, name, scope, false);
		}
		return scope.getVarsPrivate().get(name);
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
	public PtrVar copy() {
		return new PtrVar(run, name, scope, noUnwrap);
	}

	protected String value() {
		return ">" + unwrap().getDetails();
	}
}