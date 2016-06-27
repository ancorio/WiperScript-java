package by.igorshavlovsky.wpsc.preproc;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodDefineOperation extends Operation {

	public static enum Scope {
		LOCAL,
		GLOBAL
	}

	private Scope scope;
	private String name;
	private BlockVar block;

	public MethodDefineOperation(Scope scope, String name, BlockVar block, ScriptLink link) {
		super(link);
		this.scope = scope;
		this.name = name;
		this.block = block;
	}

	@Override
	public Var resolve(Call call) {
		switch (scope) {
			case LOCAL:
				call.getScope().loadMethod(new MethodOperation(name, block.getOperation().getOperations(), block.getOperation().getLink()));
				break;
			case GLOBAL:
				call.getRun().getRootScope().loadMethod(new MethodOperation(name, block.getOperation().getOperations(), block.getOperation().getLink()));
				break;
		}
		return new NullVar(call.getRun());
	}

	@Override
	public String toString() {
		return "@" + (scope == Scope.GLOBAL ? "!" : "") + name + "{" + block.getOperation() + "}";
	}

}
