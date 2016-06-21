package by.igorshavlovsky.wpsc.lib.std;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Method;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.var.PtrVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class ParamValMethod extends Method {
	
	public ParamValMethod() {
		super("param_val");
	}
	
	@Override
	public Var call(Call call) {
		if (call.getParamsCount() != 1) {
			invalidParamsCount(call.getParamsCount());
		}
		Var var = call.getParam(0);
		if (var.getVarType() != VarType.INTEGER) {
			invalidParamType(0, var);
		}
		Call parent = call.getParentCall();
		if (parent == null) {
			throw new RunException("No parent call!");
		}
		
		Var result = parent.getParam(((Long)(var.getValue())).intValue());
		if (result.getVarType() != VarType.PTR) {
			throw new RunException("Param is not a pointer");
		}
		
		return ((PtrVar)result).getValue();
		//return call.getRootCall().getParamResult(((Long)(var.getValue())).intValue());
	}

}