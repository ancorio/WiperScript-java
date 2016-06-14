package by.igorshavlovsky.wpsc.exec;

import by.igorshavlovsky.wpsc.var.BooleanVar;
import by.igorshavlovsky.wpsc.var.FloatVar;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.StringVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.VarType;

public enum Operator {
	PLUS {
		public String toString() {
			return "+";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				switch (right.getVarType()) {
					case FLOAT:
					case INTEGER:
						break;
					default:
						operatorUndefined(this, left, right);
						return null;
				}
			} else {
				switch (left.getVarType()) {
					case INTEGER:
					case FLOAT: {
						switch (right.getVarType()) {
							case INTEGER:
							case FLOAT: {
								Number leftVal = left == null ? Long.valueOf(0L) : (Number)left.getValue();
								Number rightVal = (Number)right.getValue();
								if (left.getVarType() == VarType.FLOAT || right.getVarType() == VarType.FLOAT) {
									return new FloatVar(leftVal.doubleValue() + rightVal.doubleValue());
								} else {
									return new IntegerVar(leftVal.longValue() + rightVal.longValue());
								}
							}
							default:
								operatorUndefined(this, left, right);
								break;
						}
						break;
					}
					case STRING:
						if (right.getVarType() == VarType.STRING) {
							return new StringVar((String)left.getValue() + (String)right.getValue());
						} else {
							operatorUndefined(this, left, right);
						}
						break;
					default:
						operatorUndefined(this, left, right);
						break;
				}
			}
			return null;
		}

		@Override
		public boolean detect(String string) {
			return string.charAt(0) == '+';
		}

		@Override
		public int length() {
			return 1;
		}
	},
	MINUS {
		public String toString() {
			return "-";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				switch (right.getVarType()) {
					case FLOAT:
					case INTEGER:
						break;
					default:
						operatorUndefined(this, left, right);
						return null;
				}
			} else {
				switch (left.getVarType()) {
					case INTEGER:
					case FLOAT: {
						switch (right.getVarType()) {
							case INTEGER:
							case FLOAT: {
								Number leftVal = left == null ? Long.valueOf(0L) : (Number)left.getValue();
								Number rightVal = (Number)right.getValue();
								if (left.getVarType() == VarType.FLOAT || right.getVarType() == VarType.FLOAT) {
									return new FloatVar(leftVal.doubleValue() - rightVal.doubleValue());
								} else {
									return new IntegerVar(leftVal.longValue() - rightVal.longValue());
								}
							}
							default:
								operatorUndefined(this, left, right);
								break;
						}
						break;
					}
					default:
						operatorUndefined(this, left, right);
						break;
				}
			}
			return null;
		}

		@Override
		public boolean detect(String string) {
			return string.charAt(0) == '-';
		}

		@Override
		public int length() {
			return 1;
		}
	}, EQUAL {
		public String toString() {
			return "=";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			if (left.getVarType().equals(right.getVarType())) {
				if (left.getVarType() == VarType.NULL) {
					operatorUndefined(this, left, right);
				}
				return new BooleanVar(left.getValue().equals(right.getValue()));
			}
			operatorUndefined(this, left, right);
			return null;
		}

		@Override
		public boolean detect(String string) {
			return string.charAt(0) == '=';
		}

		@Override
		public int length() {
			return 1;
		}
	}, EQUAL_OR_LESS {
		public String toString() {
			return "<=";
		}
		
		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			if (left.isDecimal() && right.isDecimal()) {
				Number leftVal = (Number)left.getValue();
				Number rightVal = (Number)right.getValue();
				return new BooleanVar(leftVal.doubleValue() <= rightVal.doubleValue());
			}
			operatorUndefined(this, left, right);
			return null;
		}

		@Override
		public boolean detect(String string) {
			return string.equals("<=");
		}

		@Override
		public int length() {
			return 2;
		}
	}, EQUAL_OR_GATHER {
		public String toString() {
			return ">=";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			if (left.isDecimal() && right.isDecimal()) {
				Number leftVal = (Number)left.getValue();
				Number rightVal = (Number)right.getValue();
				return new BooleanVar(leftVal.doubleValue() >= rightVal.doubleValue());
			}
			operatorUndefined(this, left, right);
			return null;
		}

		@Override
		public boolean detect(String string) {
			return string.equals(">=");
		}

		@Override
		public int length() {
			return 2;
		}
	}, NOT_EQUAL {
		public String toString() {
			return "!=";
		}

		@Override
		public Var run(Var left, Var right) {
			BooleanVar var = (BooleanVar) EQUAL.run(left, right);
			return new BooleanVar(!var.getValue().booleanValue());
		}

		@Override
		public boolean detect(String string) {
			return string.equals("!=");
		}

		@Override
		public int length() {
			return 2;
		}
	}, LESS {
		public String toString() {
			return "<";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			if (left.isDecimal() && right.isDecimal()) {
				Number leftVal = (Number)left.getValue();
				Number rightVal = (Number)right.getValue();
				return new BooleanVar(leftVal.doubleValue() < rightVal.doubleValue());
			}
			operatorUndefined(this, left, right);
			return null;
		}

		@Override
		public boolean detect(String string) {
			return !string.equals("<=") && string.charAt(0) == '<';
		}

		@Override
		public int length() {
			return 1;
		}
	}, GATHER {
		public String toString() {
			return ">";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			if (left.isDecimal() && right.isDecimal()) {
				Number leftVal = (Number)left.getValue();
				Number rightVal = (Number)right.getValue();
				return new BooleanVar(leftVal.doubleValue() > rightVal.doubleValue());
			}
			operatorUndefined(this, left, right);
			return null;
		}

		@Override
		public boolean detect(String string) {
			return !string.equals(">=") && string.charAt(0) == '>';
		}

		@Override
		public int length() {
			return 1;
		}
	}, MUL {
		public String toString() {
			return "*";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			if (left.isDecimal() && right.isDecimal()) {
				Number leftVal = (Number)left.getValue();
				Number rightVal = (Number)right.getValue();
				if (left.getVarType() == VarType.FLOAT || right.getVarType() == VarType.FLOAT) {
					return new FloatVar(leftVal.doubleValue() * rightVal.doubleValue());
				} else {
					return new IntegerVar(leftVal.longValue() * rightVal.longValue());
				}
			}
			operatorUndefined(this, left, right);
			return null;
		}

		@Override
		public boolean detect(String string) {
			return string.charAt(0) == '*';
		}

		@Override
		public int length() {
			return 1;
		}
	}, DIV {
		public String toString() {
			return "/";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			if (left.isDecimal() && right.isDecimal()) {
				Number leftVal = (Number)left.getValue();
				Number rightVal = (Number)right.getValue();
				if (left.getVarType() == VarType.FLOAT || right.getVarType() == VarType.FLOAT) {
					return new FloatVar(leftVal.doubleValue() / rightVal.doubleValue());
				} else {
					return new IntegerVar(leftVal.longValue() / rightVal.longValue());
				}
			}
			operatorUndefined(this, left, right);
			return null;
		}

		@Override
		public boolean detect(String string) {
			return string.charAt(0) == '/';
		}

		@Override
		public int length() {
			return 1;
		}
	};
	abstract public Var run(Var left, Var right);
	abstract public boolean detect(String string);
	abstract public int length();
	
	private static void operatorUndefined(Operator op, Var leftVar, Var rightVar) {
		String msg;
		if (leftVar == null) {
			msg = "Operator " + op + " cannot work with left null for right " + leftVar;
		} else {
			msg = "Operator " + op + " is not defined for left " + leftVar + " and right " + rightVar;
		}
		throw new RunException(msg);
	}
	
}
