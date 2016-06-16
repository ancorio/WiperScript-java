package by.igorshavlovsky.wpsc.exec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.var.Var;

public class Call extends MethodContainer {
	
	private Run run;
	
	private Method method;

	private List <Var> paramsList;
	
	private Call parentCall;
	
	private Map<String, Var> vars = new HashMap<String, Var>(8);
	
	public Call(Run run, Method method, List <Var> paramsList, Call parentCall) {
		super();
		this.run = run;
		this.method = method;
		this.paramsList = paramsList;
		this.parentCall = parentCall;
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

	public Map<String, Var> getVars() {
		return vars;
	}

	public void setVars(Map<String, Var> vars) {
		this.vars = vars;
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

	public MethodContainer root() {
		
		return run.getStack().get(0);
	}

	public String toString() {
		return "Call: " + method.getName() + " Params: " + paramsList;
	}

	public Call getRootCall() {
		return null;
	}

	public Var executeBlock(String name, Script block) {
		return run.call(new CustomMethod(name, block), paramsList, parentCall == null ? this : parentCall);
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
	
	
}
