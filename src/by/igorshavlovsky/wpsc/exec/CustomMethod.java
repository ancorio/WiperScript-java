package by.igorshavlovsky.wpsc.exec;

import sun.org.mozilla.javascript.internal.ast.ForInLoop;
import by.igorshavlovsky.wpsc.var.FloatVar;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.NullVar;
import by.igorshavlovsky.wpsc.var.StringVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public class CustomMethod extends Method {

	private String roughtScript;

	public CustomMethod(String name, String roughtScript) {
		super(name);
		this.roughtScript = roughtScript;
	}
	
	private Script script;
	private int i = 0;

	@Override
	public Var call(Call call) {
		super.call(call);
		//System.out.println("Calling method " + getName() + " from stack:\n" + call.getRun().getStackTrace());
		script = Preprocessor.prepare(roughtScript, true);
		//System.out.println(script);
		if (script.getExecutable().length() == 0) {
			return new NullVar();
		}
		run(call);
		if (result == null) {
			result = new NullVar();
		}
		return result;
	}
	
	private void printIssue(String text) {
		System.err.println(script.getExecutable());
		for(int k = 0; k < i; k++) {
			System.err.print(' ');
		}
		System.err.println('^');
		System.err.println(text);
		throw new RunException(text);
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
		String s = script.getExecutable();
		boolean escape = false;
		boolean inString = true;
		int l = s.length();
		for (; i < l; i++) {
			char c = s.charAt(i);
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
	
	private Var nextDecimal() {
		StringBuilder builder = new StringBuilder(8);
		String s = script.getExecutable();
		int l = s.length();
		boolean gotDot = false;
		boolean needMore = true;
		while (i < s.length()) { 
			char c = s.charAt(i++);
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
			return new FloatVar(Double.parseDouble(builder.toString()));
		} else {
			return new IntegerVar(Long.parseLong(builder.toString()));
		}
	}
	
	private String methodName() {
		StringBuilder result = new StringBuilder(16);
		String s = script.getExecutable();
		int l = s.length();
		while (i < s.length()) {
			char c = s.charAt(i++);
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
					result.append(c);
					break;
				}
				case '(': {
					return result.toString();
				}
			}
		}
		printIssue("Cannot find params list for method");
		return null;
	}
	private String methodParams() {
		int start = i;
		String s = script.getExecutable();
		int l = s.length();
		int bracketLvl = 0;
		while (i < l) {
			char c = s.charAt(i);
			if (c == '('  && !script.getStringIndexes().contains(Integer.valueOf(i))) {
				bracketLvl++;
			}
			if (c == ')'  && !script.getStringIndexes().contains(Integer.valueOf(i))) {
				bracketLvl--;
			}
			i++;
			if (bracketLvl < 0) {
				return s.substring(start, i - 1);
			}
		}
		printIssue("Cannot find method's params list finishing ')'");
		return null;
	}

	private Var executeMethod(Call call) {
		String methodName = methodName();
		String params = methodParams();
		Method method = call.getRun().getMethodByName(methodName);
		Var result = call.getRun().call(method, params);
		//System.out.println(methodName + " " + params);
		return result;
	}
	
	
	private Operator operator = null;
	private Var leftVar = null;
	private Var result = null;
	private Var lastVar = null;
	
	private void run(Call call) {
		//System.out.println("Running script: " + roughtScript);
		String s = script.getExecutable();
		int l = s.length();
		while (i < l) {
			char c = script.getExecutable().charAt(i);
			switch (c) {
				case '"': {
					needSeparator();
					lastVar = new StringVar(nextString());
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
					lastVar = nextDecimal();
					mergeOperator();
					break;
				}
				case ';': {
					mergeOperator();
					if (lastVar != null && lastVar.getVarType() != VarType.NULL) {
						result = lastVar;
					}
					result = lastVar;
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
			
			}
		}
		if (result == null) {
			result = lastVar;
		}
		if (result == null) {
			result = new NullVar();
		}
	}

	private void detectOperator() {
		String s = script.getExecutable();
		int l = s.length();
		if (i == l - 1) {
			printIssue("Operator at the end of the script");
		}
		mergeOperator();
		leftVar = lastVar;
		lastVar = null;
		
		String str = s.substring(i, i + 2);

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
