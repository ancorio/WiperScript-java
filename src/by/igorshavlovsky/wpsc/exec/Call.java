package by.igorshavlovsky.wpsc.exec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.preproc.Operation;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;
import by.igorshavlovsky.wpsc.var.Var;

public class Call {
	
	private Run run;
	
	private MethodOperation operation;

	private List <Var> paramsList;
	
	private Scope scope;

	public Call(Run run, MethodOperation operation, List <Var> paramsList, Scope scope) {
		super();
		this.run = run;
		this.operation = operation;
		this.paramsList = paramsList;
		this.scope = scope;
	}

	public Run getRun() {
		return run;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public Var call() {
		return operation.resolve(this);
	}
	
	
	public int getParamsCount() {
		return paramsList.size();
	}
	
	public Var getParamUnwrapped(int index) {
		if (index < getParamsCount()) {
			return paramsList.get(index).unwrap();
		} else {
			throw new RunException("Operation " + operation.getName() + " is called with " + getParamsCount() + " params, but asks for " + index, getRun(), null);
		}
	}

	public String toString() {
		return "Call: " + (operation.getName() == null ? operation.getScript() : operation.getName()) + " Params: " + paramsList;
	}

/*
	public Var executeBlock(MethodOperation operation, boolean fromParent) {
		if (fromParent) {
			return run.call(operation, parentCall.getParamsList(), parentCall.getParentCall(), false);
		} else {
			return run.call(operation, paramsList, parentCall == null ? this : parentCall, false);			
		}
		
	}
	*/

	public List<Var> getParamsList() {
		return paramsList;
	}

	public Scope getScope() {
		return scope;
	}

	public MethodOperation getMethod() {
		return operation;
	}
	
}
