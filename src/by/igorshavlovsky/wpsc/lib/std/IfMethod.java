package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.exec.Script;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.BooleanVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class IfMethod extends Method {
	
	public IfMethod() {
		super("if");
	}
	
	@Override
	public Var call(Call call) {
		BooleanVar var = call.getParamUnwrapped(0).asBoolean();
		BlockVar thenBlock = call.getParamUnwrapped(1).asBlock();
		BlockVar elseBlock = call.getParamUnwrapped(2).asBlock();
		/*if (((Boolean)var.getValue()).booleanValue()) {
			return call.executeBlock("@then", (Script)thenBlock.getValue(), true);
		} else {
			return call.executeBlock("@else", (Script)elseBlock.getValue(), true);
		}*/
		
		//!TODO
		return null;
	}

}

