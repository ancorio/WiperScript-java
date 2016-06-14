package by.igorshavlovsky.wpsc.exec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.var.Var;

public class Call extends MethodContainer {
	
	private Run run;
	
	private Method method;
	
	private String params;
	
	private Map<String, Var> vars = new HashMap<String, Var>(8);

	public Call(Run run, Method method, String params) {
		super();
		this.run = run;
		this.method = method;
		this.params = params;
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

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
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
	
	private List <Param> paramsList = null;
	
	public void parseParamsList() {
		if (paramsList != null) {
			return;
		}
		paramsList = new ArrayList<Param>(4);
		Script paramsScript = Preprocessor.prepare(params, false);
		String source = paramsScript.getExecutable();
		int start = 0;
		int l = source.length();
		int paramOrd = 0;
		int bracketLvl = 0;
		for (int i = 0; i < l; i++) {
			char c = source.charAt(i);
			if (c == '('  && !paramsScript.getStringIndexes().contains(Integer.valueOf(i))) {
				bracketLvl++;
			}
			if (c == ')'  && !paramsScript.getStringIndexes().contains(Integer.valueOf(i))) {
				bracketLvl--;
			}
			if (bracketLvl == 0 && source.charAt(i) == ',' && !paramsScript.getStringIndexes().contains(Integer.valueOf(i))) {
				paramsList.add(new Param(paramOrd++, this, source.substring(start, i)));
				start = i + 1;
			}
		}
		if (start + 1 < l) {
			paramsList.add(new Param(paramOrd++, this, source.substring(start, l)));
		}
	}
	
	public void runAllParams() {
		parseParamsList();
		for (Param p : paramsList) {
			p.getResult();
		}
	}
	
	
	public int getParamsCount() {
		parseParamsList();
		return paramsList.size();
	}
	
	public String getOriginalContent() {
		return params;
	}
	
	public String getParamContent(int index) {
		parseParamsList();
		if (index < getParamsCount()) {
			return paramsList.get(index).getContent();
		} else {
			throw new RunException("Method " + method.getName() + " is called with " + getParamsCount() + " params, but asks for " + index);
		}
	}
	
	public Var getParamResult(int index) {
		parseParamsList();
		if (index < getParamsCount()) {
			return paramsList.get(index).getResult();
		} else {
			throw new RunException("Method " + method.getName() + " is called with " + getParamsCount() + " params, but asks for " + index);
		}
	}

}
