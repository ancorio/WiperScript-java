package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class I2FMethod extends Method {
	
	public I2FMethod() {
		super("i2f");
	}
	
	@Override
	public Var call(Call call) {
		call.runAllParams();
		if (call.getParamsCount() != 1) {
			invalidParamsCount(call.getParamsCount());
		}
		Var var = call.getParamResult(0);
		if (var.getVarType() == VarType.INTEGER) {
			return var.convertTo(VarType.FLOAT);
		}
		invalidParamType(0, var);
		return null;
	}

}
