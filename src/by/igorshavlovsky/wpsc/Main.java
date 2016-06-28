package by.igorshavlovsky.wpsc;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import by.igorshavlovsky.wpsc.exec.Run;
import by.igorshavlovsky.wpsc.preproc.Preprocessor;
import by.igorshavlovsky.wpsc.preproc.PreprocessorException;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodBreak;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodI2Str;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodIf;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodLog;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodNull;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodReturn;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodWhile;

public class Main {

	private static void test(String script, Run env) throws PreprocessorException {
		Preprocessor parser = new Preprocessor(env);
		MethodOperation operation = new MethodOperation("", null);
		parser.parseFlatten(parser.flatten(script, true), operation);
		System.out.println("S:\n" + script);
		System.out.println("P: " + operation);
		System.out.println("R: " + env.call(operation, null, true));
	}
	
	private static void testFile(String path, Run env) throws PreprocessorException {
		InputStream in = null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			in = new FileInputStream(path);
			int i;
			while ((i = in.read()) >= 0) {
				out.write(i);
			}
		} catch (Exception e) {
		}
		String script = null;
		try {
			script = out.toString("UTF-8");
			in.close();
			in = null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			env.getRootScope().loadMethod(new MethodIf());
			env.getRootScope().loadMethod(new MethodWhile());
			env.getRootScope().loadMethod(new MethodBreak());
			env.getRootScope().loadMethod(new MethodLog());
			env.getRootScope().loadMethod(new MethodNull());
			env.getRootScope().loadMethod(new MethodReturn());
			/*test("@!ttt{$1};", env);
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
			*/

			//test("if(1==2, {\"yes\"}, {\"No\"})", env);
			
			//test("@testif{if($1==$2, {if($1 > 5, {99}, {88})}, {if($2 > 5, {$2}, {66})})};testif(6,8)", env);

			testFile("./yoa/while.yoa", env);
			testFile("./yoa/recursion.yoa", env);
			testFile("./yoa/methods.yoa", env);
			testFile("./yoa/if.yoa", env);
			testFile("./yoa/return.yoa", env);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
