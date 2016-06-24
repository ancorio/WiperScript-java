package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class Str2IMethod extends Method {
	
	public Str2IMethod() {
		super("str2i");
	}
	
	@Override
	public Var call(Call call) {
		return call.getParamUnwrapped(0).asString().convertTo(VarType.INTEGER);
	}

}
