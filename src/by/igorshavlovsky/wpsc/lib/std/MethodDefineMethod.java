package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.CustomMethod;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class MethodDefineMethod extends Method {
	
	public MethodDefineMethod() {
		super("method");
	}
	
	@Override
	public Var call(Call call) {
		if (call.getParamsCount() != 2) {
			invalidParamsCount(call.getParamsCount());
		}
		Var name = call.getParam(0);
		if (name.getVarType() != VarType.STRING) {
			invalidParamType(0, name);
		}
		Var var1 = call.getParam(1);
		if (var1.getVarType() != VarType.BLOCK) {
			invalidParamType(0, var1);
		}
		BlockVar block = (BlockVar)var1;
		call.root().getMethods().put((String)name.getValue(), new CustomMethod((String)name.getValue(), block.getValue()));
		return new NullVar();
	}

}