package by.igorshavlovsky.wpsc.preproc;

import java.lang.Character.Subset;
import java.util.ArrayList;
import java.util.List;

import com.sun.org.apache.bcel.internal.generic.LLOAD;

import by.igorshavlovsky.wpsc.exec.Operator;
import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.var.BlockVar;
import by.igorshavlovsky.wpsc.var.FloatVar;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.StringVar;
import sun.awt.geom.AreaOp.SubOp;

public class Preprocessor {
	
	private Run run;
	
	public Preprocessor(Run run) {
		super();
		this.run = run;
	}

	public String flatten(String string, boolean finalize) throws PreprocessorException {
		StringBuilder result = new StringBuilder();
		boolean inString = false;
		boolean escape = false;
		int l = string.length();
		for (int i = 0; i < l; i++) {
			char c = string.charAt(i);
			if (inString) {
				switch (c) {
					case '"':
						if (escape) {
							escape = false;
						} else {
							inString = false;
						}
						break;
					case '\\':
						escape = !escape;
						break;
					case 't':
					case 'n':
					case 'r':
						if (escape) {
							escape = false;
						}
						break;
					default:
						if (escape) {
							throw new PreprocessorException("Unknown escape character: " + c, this);
						}
						break;
				}
				result.append(c);
			} else {
				boolean shouldAppend;
				switch (c) {
					case '"':
						inString = true;
						shouldAppend = true;
						break;
					case '\t':
					case '\n':
					case '\r':
					case ' ':
						shouldAppend = false;
						break;
					default:
						shouldAppend = true;
						break;
				}
				if (shouldAppend) {
					result.append(c);
				}
			}
		}
		if (inString) {
			throw new PreprocessorException("String constant is not finished", this);
		}
		if (finalize && result.charAt(result.length() - 1) != ';') {
			result.append(';');
		}
		return result.toString();
	}
	
	private String input;
	private int i;
	
	private char get() {
		return input.charAt(i);
	}

	private boolean hasNext() {
		return i + 1 < input.length();
	}

	private boolean hasCurrent() {
		return i < input.length();
	}
	
	private boolean inString = false;
	private boolean escaped = false;
	private int bracketLvl = 0;
	private int blockLvl = 0;
	
	private StringBuilder lookupStringBuilder = null;
	
	private boolean next() throws PreprocessorException {
		if (hasCurrent()) {
			boolean needStringAppend = true;
			boolean result = false;
			if (inString && escaped) {
				switch (get()) {
					case 'r':
					case 'n':
					case 't':
					case '"':
					case '\\':
						break;
					default:
						throw new PreprocessorException("Unsupported escape symbol: " + get(), this);
						
				}
			}
			switch (get()) {
				case '"': {
					if (inString) {
						if (!escaped) {
							inString = false;
							needStringAppend = false;
							if (lookup == Lookup.STRING) {
								result = true;
							}
						}
					} else {
						inString = true;
					}
					break;
				}
				case '\\':
					if (inString) {
						escaped = !escaped;
						if (escaped) {
							needStringAppend = false;
						}
					}
					break;
				case '{': {
					if (!inString) {
						blockLvl++;
					}
					break;
				}
				case '}': {
					if (!inString) {
						blockLvl--;
						if (lookup == Lookup.BLOCK && blockLvl == lookupBlockLvl) {
							result = true;
						}
					}
					break;
				}
				case '(': {
					if (!inString && blockLvl == 0) {
						bracketLvl++;
					}
					break;
				}
				case ')': {
					if (!inString && blockLvl == 0) {
						bracketLvl--;
					}
					if (lookup == Lookup.BRACKET && bracketLvl == lookupBracketLvl) {
						result = true;
					}
					break;
				}
				case '\r':
				case '\n':
				case '\t':
					throw new PreprocessorException("Unescaped symbol inside string constant", this);
			}
			if (lookup != null) {
				if (lookup == Lookup.STRING) {
					if (needStringAppend) {
						lookupStringBuilder.append(get());
					}
				} else {
					if (result) {
						i++;
						return result;
					}
					lookupStringBuilder.append(get());
				}
			}
			i++;
			return result;
		} else {
			if (lookup != null) {
				throw new PreprocessorException("Failed to lookup for " + lookup, this);
			}
		}
		i++;
		return false;
	}
	
	private static enum Lookup {
		BRACKET,
		STRING,
		BLOCK
	};
	
	private Lookup lookup = null;
	
	private List<Operation> operations = new ArrayList<>(256);
	
	private ListOperation result = null;
	
	private void flush(int beginIndex, int endIndex) throws PreprocessorException {
		if (operations.size() == 0) {
			return;
		}
		SeqOperation op = new SeqOperation(new ScriptLink(input, beginIndex, endIndex), operations);
		op.validate(this);
		result.addOperation(op);
		operations.clear();
	}
	
	//Warning! string MUST be flatten!	
	public void parseFlatten(String string, ListOperation result) throws PreprocessorException {
		this.result = result;
		input = string;
		i = 0;
		while (hasCurrent()) {
			nextSeq();
		}
		result = null;
	}

	private void nextDecimal() throws PreprocessorException {
		int start = i;
		boolean hasDot = false;;
		StringBuilder result = new StringBuilder();
		boolean shouldBreak = false;
		while (hasCurrent()) {
			switch (get()) {
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
					result.append(get());//TODO optimize
					break;
				case '.':
					result.append(get());
					if (hasDot) {
						throw new PreprocessorException("Cannot parse decimal (two dots)", this);
					} else {
						hasDot = true;
					}
					break;
				default:
					shouldBreak = true;
			}
			if (shouldBreak) {
				break;
			}
			next();
		}
		try {
			if (hasDot) {
				operations.add(new VarOperation(new FloatVar(run, Double.parseDouble(result.toString())), new ScriptLink(input, start, i)));
			} else {
				operations.add(new VarOperation(new IntegerVar(run, Long.parseLong(result.toString())), new ScriptLink(input, start, i)));
			}
		} catch (Exception e) {
			throw new PreprocessorException("Cannot parse decimal", this);
		}
	}

	private void nextOperator() throws PreprocessorException {
		if (!hasNext()) {
			throw new PreprocessorException("Operator at the end of the script", this);
		}
		String str = input.substring(i, i + 2);
		OperatorOperation operation = null;
		for (Operator op : Operator.values()) {
			if (op.detect(str)) {
				operation = new OperatorOperation(op, new ScriptLink(input, i, i + op.length()));
				for (int i = 0; i < op.length(); i++) {
					next();
				}
				break;
			}
		}
		if (operation == null) {
			throw new PreprocessorException("Cannot detect operator", this);
		} else {
			operations.add(operation);
		}
	}
	
	private void nextSeq() throws PreprocessorException {
		int begin = i;
		while (hasCurrent()) {
			switch (get()) {
				case ';':
					flush(begin, i);
					next();
					return;
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
					nextDecimal();
					break;
				}
				case '+':
				case '-':
				case '*':
				case '/':
				case '<':
				case '>':
				case '=':
				case '!':
					nextOperator();
					break;
				case '"': {
					ScriptLink link = lookupFor(Lookup.STRING);
					operations.add(new VarOperation(new StringVar(run, lookupStringBuilder.toString()), link));
					break;
				}
				case '{': {
					ScriptLink link = lookupFor(Lookup.BLOCK);
					ListOperation operation = new ListOperation(link);
					Preprocessor p = new Preprocessor(run);
					p.parseFlatten(lookupStringBuilder.toString(), operation);
					operations.add(new VarOperation(new BlockVar(run, operation), link));
					break;
				}
				default:
					throw new PreprocessorException("Unknown operation", this);
			}
		}
		flush(begin, i);
	}
	
	private int lookupBracketLvl = 0;
	private int lookupBlockLvl = 0;
	
	private ScriptLink lookupFor(Lookup lookup) throws PreprocessorException {
		int start = i;
		next();
		this.lookup = lookup;
		lookupStringBuilder = new StringBuilder();
		lookupBracketLvl = bracketLvl - 1;
		lookupBlockLvl = blockLvl - 1;

		
		while (!next()) {
		}
		

		lookup = null;
		return new ScriptLink(input, start, i);
	}

	
}

//const(int, str, lambd, var)
//()
//method()
//method{};
//oper */
//oper -+
//oper <>==
//oper=
