package by.igorshavlovsky.wpsc.exec;

import by.igorshavlovsky.wpsc.preproc.Operation;

public class RunException extends RuntimeException {

	

	public RunException(String arg0, Run run, Operation op) {
		super(arg0);
		System.err.print(arg0);
		System.err.println();
	}

}
