package by.igorshavlovsky.wpsc.preproc;

public class PreprocessorException extends Exception {

	public PreprocessorException(String message, Preprocessor preprocessor) {
		super(message);
		System.err.println(preprocessor.getInput());
		for (int i = 0; i < preprocessor.getI(); i++) {
			System.err.print(" ");
		}
		System.err.println("^");
		System.err.println(message);
		System.err.println();
	}

}
