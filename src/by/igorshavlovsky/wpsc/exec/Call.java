package by.igorshavlovsky.wpsc.exec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.var.Var;

public class Call extends StackEntry {
	
	private Run run;
	
	private Method method;

	private List <Var> paramsList;
	
	private Call parentCall;
	
	public Call(Run run, Method method, List <Var> paramsList, Call parentCall, VarsScope varsScope) {
		super();
		this.run = run;
		this.method = method;
		this.paramsList = paramsList;
		this.parentCall = parentCall;
		this.varsScope = varsScope;
	}

	public Run getRun() {
		return run;
	}

	public void setRun(Run run) {
		this.run = run;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Var call() {
		return method.call(this);
	}
	
	
	public int getParamsCount() {
		return paramsList.size();
	}
	
	public Var getParam(int index) {
		if (index < getParamsCount()) {
			return paramsList.get(index);
		} else {
			throw new RunException("Method " + method + " is called with " + getParamsCount() + " params, but asks for " + index);
		}
	}

	public StackEntry root() {
		
		return run.getStack().get(0);
	}

	public String toString() {
		return "Call: " + method.getName() + " Params: " + paramsList;
	}

	public Call getRootCall() {
		return null;
	}

	public Var executeBlock(String name, Script block, boolean fromParent) {
		if (fromParent && parentCall != null) {
			return run.call(new CustomMethod(name, block), parentCall.getParamsList(), parentCall.getParentCall(), false);
		} else {
			return run.call(new CustomMethod(name, block), paramsList, parentCall == null ? this : parentCall, false);			
		}
		
	}

	public List<Var> getParamsList() {
		return paramsList;
	}

	public Call getParentCall() {
		return parentCall;
	}

	public void setParentCall(Call parentCall) {
		this.parentCall = parentCall;
	}
	
	public StackEntry getVarProvider() {
		if (parentCall == null) {
			return this;
		} else {
			return parentCall;
		}
	}
	
	private VarsScope varsScope;

	public VarsScope getVarsScope() {
		return varsScope;
	}
	
	
}
