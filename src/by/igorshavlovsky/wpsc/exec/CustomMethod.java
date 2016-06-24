package by.igorshavlovsky.wpsc.exec;

import java.util.ArrayList;
import java.util.List;

import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.FloatVar;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.StringVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.PtrVar;
import by.igorshavlovsky.wpsc.var.VarType;

public class CustomMethod extends Method {

	private String roughtScript;

	public CustomMethod(String name, String roughtScript) {
		super(name);
		this.roughtScript = roughtScript;
		this.script = null;//Preprocessor.prepare(roughtScript, true);
	}
	public CustomMethod(String name, Script script) {
		super(name);
		roughtScript = script.getScript();
		this.script = script;
	}
	

	private Script script;
	private int i = 0;

	@Override
	public Var call(Call call) {
		//System.out.println("Calling method " + getName() + " from stack:\n" + call.getRun().getStackTrace());
		
		//System.out.println(script);
		if (script.getLength() == 0) {
			return new NullVar(call.getRun());
		}
		run(call);
		if (result == null) {
			result = new NullVar(call.getRun());
		}
		return result;
	}
	
	private void printIssue(String text) {
		System.err.println(script.getScript());
		for(int k = script.getStart(); k < i; k++) {
			System.err.print(' ');
		}
		System.err.println('^');
		throw new RunException(text, null, null); //!TODO!
	}
	
	private void needSeparator() {
		if (lastVar == null) {
			return;
		}
		printIssue("Expected separator berween two expressions");
	}
	
	private String nextString() {
		StringBuilder result = new StringBuilder();
		i++;
		String sourceString = script.getSource();
		boolean escape = false;
		int lastIndex = script.getLastIndex();
		for (; i < lastIndex; i++) {
			char c = sourceString.charAt(i);
			switch (c) {
			case '"':
				if (escape) {
					escape = false;
					result.append('"');
				} else {
					i++;
					return result.toString();
					
				}
				break;
			case '\\':
				if (escape) {
					result.append('\\');
				}
				escape = !escape;
				break;
			case 'r':
				if (escape) {
					escape = false;
					result.append('\r');
				} else {
					result.append('r');
				}
				break;
			case 'n':
				if (escape) {
					escape = false;
					result.append('\n');
				} else {
					result.append('n');
				}
				break;
			case 't':
				if (escape) {
					escape = false;
					result.append('\t');
				} else {
					result.append('t');
				}
				break;
			default:
				result.append(c);
				break;
			}
		}
		printIssue("String constant is not finished");
		return null;
	}
	
	private Var nextBlock(Call call) {
		StringBuilder result = new StringBuilder();
		i++;
		int bracketLevel = 0;
		String sourceString = script.getSource();
		int lastIndex = script.getLastIndex();
		int start = i;
		while (i < lastIndex) {
			char c = sourceString.charAt(i);
			switch (c) {
			case '{':
				if (!script.isInString(i)) {
					bracketLevel++;
				}
				break;
			case '}':
				if (!script.isInString(i)) {
					bracketLevel--;
				}
				break;
			}
			i++;
			if (bracketLevel < 0) {
				//return new BlockVar(call.getRun(), sourceString.substring(start, i - 1));
			}
		}
		printIssue("Block is not finished");
		return null;
	}
	
	private Var nextDecimal(Call call) {
		StringBuilder builder = new StringBuilder(8);
		String sourceString = script.getSource();
		int lastIndex = script.getLastIndex();
		boolean gotDot = false;
		boolean needMore = true;
		while (i < lastIndex) { 
			char c = sourceString.charAt(i++);
			switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
					builder.append(c);
					break;
				case '.': {
					if (gotDot) {
						printIssue("Decimal constant contains 2 dots.");
					} else {
						builder.append(c);
						gotDot = true;
					}
					break;
				}
				default:
					i--;
					needMore = false;
					break;
			}
			if (!needMore) {
				break;
			}
		}
		if (gotDot) {
			return new FloatVar(call.getRun(), Double.parseDouble(builder.toString()));
		} else {
			return new IntegerVar(call.getRun(),Long.parseLong(builder.toString()));
		}
	}
	
	private String methodName() {
		String sourceString = script.getSource();
		int lastIndex = script.getLastIndex();
		int startIndex = i;
		while (i < lastIndex) {
			char c = sourceString.charAt(i++);
			switch (c) {
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9':
				case 'a': 
				case 'b': 
				case 'c': 
				case 'd': 
				case 'e': 
				case 'f': 
				case 'g': 
				case 'h': 
				case 'i': 
				case 'j': 
				case 'k': 
				case 'l': 
				case 'm': 
				case 'n':
				case 'o': 
				case 'p': 
				case 'q': 
				case 'r': 
				case 's': 
				case 't': 
				case 'u': 
				case 'v': 
				case 'w': 
				case 'x': 
				case 'y': 
				case 'z':
				case '_': {
					break;
				}
				case '(': {
					return sourceString.substring(startIndex, i - 1);
				}
				default: {
					printIssue("Invalid symbol in method name: " + c);
				}
			}
		}
		printIssue("Cannot find params list for method");
		return null;
	}
	private Script methodParams() {
		int start = i;
		String sourceScring = script.getSource();
		int lastIndex = script.getLastIndex();
		int bracketLvl = 0;
		int subLvl = 0;
		while (i < lastIndex) {
			char c = sourceScring.charAt(i);
			switch (c) {
				case '{': {
					if (!script.isInString(i)) {
						subLvl++;
					}
					break;
				}
				case '}': {
					if (!script.isInString(i)) {
						subLvl--;
					}
					break;
				}
				case '(': {
					if (!script.isInString(i) && subLvl == 0) {
						bracketLvl++;
					}
					break;
				}
				case ')': {
					if (!script.isInString(i) && subLvl == 0) {
						bracketLvl--;
					}
					break;
				}
			}
			i++;
			if (bracketLvl < 0 && subLvl == 0) {
				return new SubScript(script, start, i - 1 - start);
			}
		}
		printIssue("Cannot find method's params list finishing ')'");
		return null;
	} 
	private Script nextBracket() {
		int start = i;
		String sourceScring = script.getSource();
		int lastIndex = script.getLastIndex();
		int bracketLvl = 0;
		while (i < lastIndex) {
			char c = sourceScring.charAt(i);
			if (c == '('  && !script.isInString(i)) {
				bracketLvl++;
			}
			if (c == ')'  && !script.isInString(i)) {
				bracketLvl--;
			}
			i++;
			if (bracketLvl < 0) {
				return new SubScript(script, start, i - 1 - start);
			}
		}
		printIssue("Cannot find list finishing ')'");
		return null;
	} 
	
	private List <Var> paramVars(Script paramsScript, Call call) {
		List <Var> result = new ArrayList<>();
		String sourceString = paramsScript.getSource();
		int startIndex = paramsScript.getStart();
		int lastIndex = paramsScript.getLastIndex();
		int paramOrd = 0;
		int bracketLvl = 0;
		for (int i = startIndex; i < lastIndex; i++) {
			char c = sourceString.charAt(i);
			if (c == '('  && !paramsScript.isInString(i)) {
				bracketLvl++;
			}
			if (c == ')'  && !paramsScript.isInString(i)) {
				bracketLvl--;
			}
			if (bracketLvl == 0 && sourceString.charAt(i) == ',' && !paramsScript.isInString(i)) {
				Script s = new SubScript(paramsScript, startIndex, i - startIndex);
				//result.add(call.executeBlock("@" + call.getMethod().getName() + "'s param #" + result.size(), s, false));
				startIndex = i + 1;
			}
		}
		if (paramsScript.getLastIndex() > 0) {
			Script s = new SubScript(paramsScript, startIndex, lastIndex - startIndex);
			//result.add(call.executeBlock("@" + call.getMethod().getName() + "'s param #" + result.size(), s, false));
		}
		return result;
	}

	private Var executeMethod(Call call) {
		String methodName = methodName();
		Script params = methodParams();
		List <Var> paramVars = paramVars(params, call);
		//Method method = call.getRun().getMethodByName(methodName);
		
		//Var result = call.getRun().call(method, paramVars, call, true);
		
		//System.out.println(methodName + " " + params);
		//return result;
		return null;
	}
	
	@Override
	public String toString() {
		return getName() + ": ";// + Preprocessor.prepare(roughtScript, true).getScript();
	}
	
	private Operator operator = null;
	private Var leftVar = null;
	private Var result = null;
	private Var lastVar = null;
	
	private void run(Call call) {
		i = script.getStart();
		System.out.println("Running script: " + script.getScript());

		
		String sourceString = script.getSource();
		int lastIndex = script.getLastIndex();
		while (i < lastIndex) {
			char c = sourceString.charAt(i);
			switch (c) {
				case '"': {
					needSeparator();
					lastVar = new StringVar(call.getRun(), nextString());
					mergeOperator();
					break;
				}
				case '{': {
					needSeparator();
					lastVar = nextBlock(call);
					mergeOperator();
					break;
				}
				case '$': {
					needSeparator();
					i++;
					lastVar = nextPtr(call);//NO UNWRAP, OPERATOR WILL UNWRAP WHEN NEEDS IT!
					mergeOperator();
					break;
				}
				case '#': {
					needSeparator();
					i++;
					lastVar = nextPtr(call);
					mergeOperator();
					break;
				}
				case '0':
				case '1':
				case '2':
				case '3':
				case '4':
				case '5':
				case '6':
				case '7':
				case '8':
				case '9': {
					needSeparator();
					lastVar = nextDecimal(call);
					mergeOperator();
					break;
				}
				case ';': {
					mergeOperator();
					if (lastVar != null && lastVar.getVarType() != VarType.NULL) {
						result = lastVar;
					}
					result = lastVar;
					lastVar = null;
					i++;
					break;
				}
				case '+':
				case '=':
				case '>':
				case '<':
				case '!':
				case '-':
				case '*':
				case '/': {
					detectOperator();
					break;
				}
				case 'a': 
				case 'b': 
				case 'c': 
				case 'd': 
				case 'e': 
				case 'f': 
				case 'g': 
				case 'h': 
				case 'i': 
				case 'j': 
				case 'k': 
				case 'l': 
				case 'm': 
				case 'n':
				case 'o': 
				case 'p': 
				case 'q': 
				case 'r': 
				case 's': 
				case 't': 
				case 'u': 
				case 'v': 
				case 'w': 
				case 'x': 
				case 'y': 
				case 'z':  {
					needSeparator();
					lastVar = executeMethod(call);
					mergeOperator();
					break;
				}
				case '(':
					needSeparator();
					i++;
					Script script = nextBracket();
					//lastVar = call.executeBlock("()", script, false); //TODO decide!
					mergeOperator();
					break;
				default:
					printIssue("Invalid operation");
					break;
			
			}
		}
		if (result == null) {
			result = lastVar;
		}
		if (result == null) {
			result = new NullVar(call.getRun());
		}

		//System.out.println("Script: " + script.getScript() + " Result:" + result + "\t\t\t" + call);
	}

	private PtrVar nextPtr(Call call) {
		String sourceString = script.getSource();
		int lastIndex = script.getLastIndex();
		if (i >= lastIndex) {
			printIssue("Var access operator at the end of the line");
		}
		boolean global = false;
		if (sourceString.charAt(i) == '!') {
			global = true;
			i++;
		}
		int startIndex = i;
		while (i < lastIndex) {
			boolean shouldBreak = true;
			char c = sourceString.charAt(i);
			switch (c) {
				case 'a': 
				case 'b': 
				case 'c': 
				case 'd': 
				case 'e': 
				case 'f': 
				case 'g': 
				case 'h': 
				case 'i': 
				case 'j': 
				case 'k': 
				case 'l': 
				case 'm': 
				case 'n':
				case 'o': 
				case 'p': 
				case 'q': 
				case 'r': 
				case 's': 
				case 't': 
				case 'u': 
				case 'v': 
				case 'w': 
				case 'x': 
				case 'y': 
				case 'z': 
				case '_':  {
					i++;
					shouldBreak = false;
					break;
				}
			}
			if (shouldBreak) {
				break;
			}
		}
		if (startIndex == i) {
			printIssue("Var access operator at the end of the line");
		}
		Scope entry;
		if (global) {
			entry = call.getRun().getRootScope();
		} else {
			entry = call.getScope();
		}
		return entry.getVarPtr(sourceString.substring(startIndex, i));
	}
	private void detectOperator() {
		String sourceString = script.getSource();
		int lastIndex = script.getLastIndex();
		if (i >= lastIndex - 1) {
			printIssue("Operator at the end of the script");
		}
		mergeOperator();
		leftVar = lastVar;
		lastVar = null;
		
		String str = sourceString.substring(i, i + 2);

		for (Operator op : Operator.values()) {
			if (op.detect(str)) {
				operator = op;
				i += operator.length();
			}
		}
	}

	private void mergeOperator() {
		if (operator != null) {
			if (lastVar == null) {
				printIssue("Operator " + operator + " expects right expression");
			} else {
				executeOperator();
			}
		}
	}
	
	private void executeOperator() {
		Var rightVar = lastVar;
		try {
			lastVar = operator.run(leftVar, lastVar);			
		} catch (Exception e) {
			printIssue(e.toString());
		}
		
		operator = null;
	}

}
