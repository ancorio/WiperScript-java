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
		return call.getParamUnwrapped(0).asInteger().convertTo(VarType.FLOAT);
	}

}
