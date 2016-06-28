package by.igorshavlovsky.wpsc.preproc.operation.method;

import by.igorshavlovsky.wpsc.preproc.Operation;

public class BreakException extends RuntimeException {

	private Operation operation;

	public BreakException(Operation operation) {
		super();
		this.operation = operation;
	}
	
	
	
}
