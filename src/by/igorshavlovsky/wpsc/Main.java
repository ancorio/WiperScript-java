package by.igorshavlovsky.wpsc;

import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.preproc.Preprocessor;
import by.igorshavlovsky.wpsc.preproc.PreprocessorException;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodI2Str;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;

public class Main {

	private static void test(String script, Run env) throws PreprocessorException {
		Preprocessor parser = new Preprocessor(env);
		MethodOperation operation = new MethodOperation("", null);
		parser.parseFlatten(parser.flatten(script, true), operation);
		System.out.println("S:\n" + script);
		System.out.println("P: " + operation);
		System.out.println("R: " + env.call(operation, null, true));
	}
	
	public static void main(String[] args) {
		try {
			Run env = new Run();
			env.getRootScope().loadMethod(new MethodI2Str());
			test("@!ttt{$1};", env);
			test("ttt(123, 5353,\"\", \"asdasd\" + \"sdsafd\");", env);
			test("1+2+3+4+(5-5)", env);
			test("{123+232}", env);
			test("\"111\"+\"222\"", env);
			test("111+222", env);
			test("2+2*2", env);
			test("1+2*3/4", env);
			test(" 1 \n+\n\n 1\n\n ", env);
			test(" 3 \n+\n\n 2\n\n ; 3 \n+\n\n 3\n\n ; 5 \n+\n\n 1\n\n ; 5 \n+\n\n 5\n\n ", env);
			test("$x=5; $x = -10 + $x *4; $x", env);
			test("@sum{$1+$2};\nsum(4, 5)", env);
			test("@sum{$1+$2};\nsum(\"asd\", \"asdasd\")", env);
			test("@count{\"total params count: \" + i2str($0)};\ncount(\"one\",\"two\",\"three\",\"four\",\"five\");", env);
			test("@printer{$($1 + 1)};printer(2,\"one\",\"two\",\"three\",\"four\",\"five\");", env);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
