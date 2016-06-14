package by.igorshavlovsky.wpsc.exec;

public class RunException extends RuntimeException {

	public RunException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RunException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public RunException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public RunException(String arg0) {
		super(arg0);
		System.err.print(arg0);
		System.err.println();
		// TODO Auto-generated constructor stub
	}

	public RunException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

}
