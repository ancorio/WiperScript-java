package by.igorshavlovsky.wpsc.preproc;

import java.util.List;

import sun.org.mozilla.javascript.internal.ast.ForInLoop;
import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.exec.Operator;
import by.igorshavlovsky.wpsc.exec.RunException;
import by.igorshavlovsky.wpsc.var.Var;

public class SeqOperation extends ListOperation {

	public SeqOperation(ScriptLink link, List<Operation> operations) {
		super(link);
		for (Operation operation : operations) {
			addOperation(operation);
		}
	}

	public Var resolve(Call call) {
		if (operations.size() == 0) {
			return null;
		}
		if (operations.size() == 1) {
			return operations.get(0).resolve(call);
		}
		return resolveAssigns(call, 0, operations.size());
	}
	
	private OperatorOperation opAt(int i) {
		Operation op = operations.get(i);
		if (op.isOperator()) {
			return (OperatorOperation)op;
		}
		return null;
	}
	
	private Var resolveAssigns(Call call, int start, int end) {
		int pos = start;
		while (pos < end) {
			OperatorOperation op = opAt(pos);
			if (op != null) {
				switch (op.getOperator()) {
					case ASSIGN: {
						Var right = resolveCompares(call, pos + 1, end);
						Var left = resolveAssigns(call, 0, pos);
						return op.getOperator().run(left, right);
					}
				}
			}
			pos++;
		}
		return resolveCompares(call, start, end);
	}
	
	private Var resolveCompares(Call call, int start, int end) {
		int pos = end - 1;
		while (pos >= start) {
			OperatorOperation op = opAt(pos);
			if (op != null) {
				switch (op.getOperator()) {
					case EQUAL:
					case EQUAL_OR_GATHER:
					case EQUAL_OR_LESS:
					case NOT_EQUAL:
					case LESS:
					case GATHER: {
						Var left = resolveCompares(call, 0, pos);
						Var right = resolveSums(call, pos + 1, end);
						return op.getOperator().run(left, right);
					}
				}

			}
			pos--;
		}
		return resolveSums(call, start, end);
	}
	
	private Var resolveSums(Call call, int start, int end) {
		if (start == end) {
			return null;
		}
		int pos = end - 1;
		while (pos >= start) {
			OperatorOperation op = opAt(pos);
			if (op != null) {
				switch (op.getOperator()) {
					case PLUS:
					case MINUS:{
						Var left = resolveSums(call, 0, pos);
						Var right = resolveMuls(call, pos + 1, end);
						return op.getOperator().run(left, right);
					}
				}
				
			
			}
			pos--;
		}
		return resolveMuls(call, start, end);
	}
	
	private Var resolveMuls(Call call, int start, int end) {
		if (start == end) {
			return null;
		}
		int pos = end - 1;
		while (pos >= start) {
			OperatorOperation op = opAt(pos);
			if (op != null) {
				switch (op.getOperator()) {
					case MUL:
					case DIV:{
						Var left = resolveMuls(call, 0, pos);
						Var right = operations.get(pos + 1).resolve(call);// this will resolve a constant/method/()
						return op.getOperator().run(left, right);
					}
				}
				
			
			}
			pos--;
		}
		return operations.get(start).resolve(call);// this will resolve a constant/method/()
	}
	
	
	public void validate(Preprocessor parser) throws PreprocessorException {
		Operation lastOperation = null;
		for (Operation operation : operations) {
			if (operation.isOperator()) {
				if (lastOperation != null) {
					if (lastOperation.isOperator()) {
						throw new PreprocessorException("Two operator operations in a row", parser);
					}
				} else {
					OperatorOperation op = (OperatorOperation)operation;
					if (op.getOperator() != Operator.MINUS) {
						throw new PreprocessorException("Only minus operator can stard in the beginning of the statement", parser);
					}
				}
			} else {
				if (lastOperation != null) {
					if (!lastOperation.isOperator()) {
						throw new PreprocessorException("Two non-operator operations in a row", parser);
					}
				}
			}
			lastOperation = operation;
		}
		if (lastOperation != null && lastOperation.isOperator()) {
			throw new PreprocessorException("Operator at the end of the statement", parser);
		}
	}
	
	@Override
	public String toString() {
		return operations.toString();
	}


}
