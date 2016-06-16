package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.exec.Script;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class IfMethod extends Method {
	
	public IfMethod() {
		super("if");
	}
	
	@Override
	public Var call(Call call) {
		if (call.getParamsCount() != 3) {
			invalidParamsCount(call.getParamsCount());
		}
		Var var = call.getParam(0);
		Var thenBlock = call.getParam(1);
		Var elseBlock = call.getParam(2);
		if (var.getVarType() != VarType.BOOLEAN) {
			invalidParamType(0, var);
		}
		if (thenBlock.getVarType() != VarType.BLOCK) {
			invalidParamType(1, thenBlock);
		}
		if (elseBlock.getVarType() != VarType.BLOCK) {
			invalidParamType(2, elseBlock);
		}
		if (((Boolean)var.getValue()).booleanValue()) {
			return call.executeBlock("@then", (Script)thenBlock.getValue());
		} else {
			return call.executeBlock("@else", (Script)elseBlock.getValue());
		}
	}

}

