package by.igorshavlovsky.wpsc;

import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.preproc.Preprocessor;
import by.igorshavlovsky.wpsc.preproc.PreprocessorException;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;

public class Main {

	private static void test(String script, Run env) throws PreprocessorException {
		Preprocessor parser = new Preprocessor(env);
		MethodOperation operation = new MethodOperation("debug_call", null);
		parser.parseFlatten(parser.flatten(script, true), operation);
		System.out.println("S: " + env.call(operation, null, true));
	}
	
	public static void main(String[] args) {
		try {
			Run env = new Run();
			test("{123+232}", env);
			test("\"111\"+\"222\"", env);
			test("111+222", env);
			test("2+2*2", env);
			test("1+2*3/4", env);
			test("1+1", env);
			test(" 1 \n+\n\n 1\n\n ", env);
			test(" 3 \n+\n\n 2\n\n ; 3 \n+\n\n 3\n\n ; 5 \n+\n\n 1\n\n ; 5 \n+\n\n 5\n\n ", env);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
