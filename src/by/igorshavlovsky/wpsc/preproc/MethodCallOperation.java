package by.igorshavlovsky.wpsc.preproc;

import java.util.ArrayList;
import java.util.List;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodCallOperation extends ListOperation {
	
	private String methodName;
	
	public MethodCallOperation(String methodName, ScriptLink link) {
		super(link);
		this.methodName = methodName;
	}
	
	public MethodCallOperation(String methodName, List <Operation> operations, ScriptLink link) {
		super(link);
		this.methodName = methodName;
		for (Operation operation : operations) {
			addOperation(operation);
		}
	}

	public String getMethodName() {
		return methodName;
	}

	@Override
	public Var resolve(Call call) {
		Var result = null;
		MethodOperation method = call.getRun().getMethodByName(methodName);
		if (method == null) {
			throw new RunException("Cannot find method: " + methodName, call.getRun(), this);
		}
		List<Var> params = new ArrayList<>(operations.size());
		for (Operation operation : operations) {
			Var param = operation.resolve(call);
			if (param == null) {
				throw new RunException("Param operation returned null", call.getRun(), operation);
			}
			params.add(param.unwrap());
		}
		result = call.getRun().call(method, params, true);
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean f = false;
		for (Operation op : operations) {
			if (f) {
				result.append(", ");
			} else {
				f = true;
			}
			result.append(op.toString());
		}
		return methodName + "(" + result.toString() + ")";
	}
	
}
