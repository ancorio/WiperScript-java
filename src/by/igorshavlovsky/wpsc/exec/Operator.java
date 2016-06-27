package by.igorshavlovsky.wpsc.exec;

import by.igorshavlovsky.wpsc.var.BooleanVar;
import by.igorshavlovsky.wpsc.var.FloatVar;
import by.igorshavlovsky.wpsc.var.IntegerVar;
import by.igorshavlovsky.wpsc.var.StringVar;
import by.igorshavlovsky.wpsc.var.Var;
import by.igorshavlovsky.wpsc.var.PtrVar;
import by.igorshavlovsky.wpsc.var.VarType;

public enum Operator {
	PLUS {
		public String toString() {
			return "+";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
				return null;
			}
			left = left.unwrap();
			right = right.unwrap();
			if (left.isDecimal() || right.isDecimal()) {
				if (left.getVarType() == VarType.INTEGER && right.getVarType() == VarType.INTEGER) {
					return new IntegerVar(right.getRun(), left.asInteger().longValue() + right.asInteger().longValue());
				}
				if (left.isDecimal() && right.isDecimal()) {
					double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
					double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
					return new FloatVar(right.getRun(), lv - rv);
				}
			}
			if (left.getVarType() == VarType.STRING && right.getVarType() == VarType.STRING) {
				return new StringVar(right.getRun(), left.asString().stringValue() + right.asString().stringValue());
			}
			operatorUndefined(this, left, right);
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
				operatorUndefined(this, left, right);
				return null;
			}
			left = left.unwrap();
			right = right.unwrap();
			if ((left == null || left.getVarType() == VarType.INTEGER) && right.getVarType() == VarType.INTEGER) {
				return new IntegerVar(right.getRun(), (left == null ? 0L : left.asInteger().longValue()) - right.asInteger().longValue());
			}
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new FloatVar(right.getRun(), lv - rv);
			}
			operatorUndefined(this, left, right);
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
			return "==";
		}

		@Override
		public Var run(Var left, Var right) {
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			left = left.unwrap();
			right = right.unwrap();
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new BooleanVar(right.getRun(), lv > rv);
			}
			if (left.getVarType() == VarType.STRING && right.getVarType() == VarType.STRING) {
				return new BooleanVar(right.getRun(), left.asString().stringValue().equals(right.asString().stringValue()));
			}

			if (left.getVarType() == VarType.BOOLEAN && right.getVarType() == VarType.BOOLEAN) {
				return new BooleanVar(right.getRun(), left.asBoolean().booleanValue() == right.asBoolean().booleanValue());
			}

			operatorUndefined(this, left, right);
			return null;
		}
		
		@Override
		public boolean detect(String string) {
			return string.equals("==");
		}

		@Override
		public int length() {
			return 1;
		}
	}, ASSIGN {
		public String toString() {
			return "=";
		}
		@Override
		public Var run(Var left, Var right) {
			if (left.getVarType() != VarType.PTR) {
				operatorUndefined(this, left, right);
			}
			right = right.unwrap();
			left.asPtr().getScope().getVarsPrivate().put(left.asPtr().getName(), right);
			return right;
		}

		@Override
		public boolean detect(String string) {
			return string.charAt(0) == '=' && string.charAt(1) != '=';
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
			left = left.unwrap();
			right = right.unwrap();
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new BooleanVar(right.getRun(), lv <= rv);
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
			left = left.unwrap();
			right = right.unwrap();
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new BooleanVar(right.getRun(), lv >= rv);
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
			if (left == null) {
				operatorUndefined(this, left, right);
			}
			left = left.unwrap();
			right = right.unwrap();
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new BooleanVar(right.getRun(), lv != rv);
			}
			operatorUndefined(this, left, right);
			return null;
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
			left = left.unwrap();
			right = right.unwrap();
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new BooleanVar(right.getRun(), lv < rv);
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
			left = left.unwrap();
			right = right.unwrap();
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new BooleanVar(right.getRun(), lv > rv);
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
			left = left.unwrap();
			right = right.unwrap();
			if (left.getVarType() == VarType.INTEGER && right.getVarType() == VarType.INTEGER) {
				return new IntegerVar(right.getRun(), left.asInteger().longValue() * right.asInteger().longValue());
			}
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new FloatVar(right.getRun(), lv * rv);
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
			left = left.unwrap();
			right = right.unwrap();
			if (left.getVarType() == VarType.INTEGER && right.getVarType() == VarType.INTEGER) {
				return new IntegerVar(right.getRun(), left.asInteger().longValue() / right.asInteger().longValue());
			}
			if (left.isDecimal() && right.isDecimal()) {
				double lv = left.getVarType() == VarType.INTEGER ? left.asInteger().longValue() : left.asFloat().doubleValue();
				double rv = right.getVarType() == VarType.INTEGER ? right.asInteger().longValue() : right.asFloat().doubleValue();	
				return new FloatVar(right.getRun(), lv / rv);
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
		throw new RunException(msg, null, null);
	}
	
}
