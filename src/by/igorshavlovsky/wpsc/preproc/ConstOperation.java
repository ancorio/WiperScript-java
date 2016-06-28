package by.igorshavlovsky.wpsc.preproc;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class ConstOperation extends Operation {
	
	private Var value;

	public ConstOperation(Var value, ScriptLink link) {
		super(link);
		this.value = value;
	}

	@Override
	public Var resolve(Call call) {
		if (value.getVarType() == VarType.BLOCK) {
			BlockVar block = value.asBlock();
			if (block.getCall() == null) {
				block = block.copy();
				block.setCall(call);
			}
			return block;
		}
		return value;
	}
	
	@Override
	public String toString() {
		switch(value.getVarType()) {
			case BLOCK: {
				return "{" + value.asBlock().getOperation() + "}";
			}
			case BOOLEAN: {
				return "" + value.asBoolean().booleanValue() + "";
			}
			case FLOAT: {
				return "" + value.asFloat().doubleValue() + "";
			}
			case NULL: {
				return "NULL";
			}
			case INTEGER: {
				return "" + value.asInteger().longValue() + "";
			}
			case STRING: {
				return "'" + value.asString().stringValue() + "'";
			}
			case PTR: {
				return "PTR!!!!";
			}
		}
		return value.getDetails();
	}

}
