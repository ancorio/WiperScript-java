package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.CustomMethod;
import by.igorshavlovsky.wpsc.exec.Method;
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
		Var var = call.getParamResult(0);
		if (var.getVarType() != VarType.STRING) {
			invalidParamType(0, var);
		}
		call.root().getMethods().put((String)var.getValue(), new CustomMethod((String)var.getValue(), call.getParamContent(1)));
		return new NullVar();
	}

}