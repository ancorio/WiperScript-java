package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class I2StrMethod extends Method {
	
	public I2StrMethod() {
		super("i2str");
	}
	
	@Override
	public Var call(Call call) {
		if (call.getParamsCount() != 1) {
			invalidParamsCount(call.getParamsCount());
		}
		Var var = call.getParam(0);
		if (var.getVarType() == VarType.INTEGER) {
			return var.convertTo(VarType.STRING);
		}
		invalidParamType(0, var);
		return null;
	}

}
