package by.igorshavlovsky.wpsc.exec;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.lib.std.I2FMethod;
import by.igorshavlovsky.wpsc.lib.std.I2StrMethod;
import by.igorshavlovsky.wpsc.lib.std.IfMethod;
import by.igorshavlovsky.wpsc.lib.std.RoundMethod;
import by.igorshavlovsky.wpsc.lib.std.Str2IMethod;
import by.igorshavlovsky.wpsc.lib.std.TruncMethod;
import by.igorshavlovsky.wpsc.preproc.Operation;
import by.igorshavlovsky.wpsc.preproc.SeqOperation;
import by.igorshavlovsky.wpsc.preproc.operation.method.BreakException;
import by.igorshavlovsky.wpsc.preproc.operation.method.MethodOperation;
import by.igorshavlovsky.wpsc.var.PtrVar;
import by.igorshavlovsky.wpsc.var.Var;

public class Run {

	private List<Call> stack = new ArrayList<>(64);
	
	private List <Scope> scopes = new ArrayList<>(64);
	
	private void loadStdLib() {
		Scope scope = getRootScope();
		/*scope.loadMethod(new I2StrMethod());
		scope.loadMethod(new TruncMethod());
		scope.loadMethod(new RoundMethod());
		scope.loadMethod(new Str2IMethod());
		scope.loadMethod(new I2FMethod());
		scope.loadMethod(new IfMethod());
		*/
	}
	
	private void test(String script) {
	//	System.out.println(call(new CustomMethod("Main", script), new ArrayList<Var>(), null, true));
	}

	public Run() {
		super();
		
		pushScope();
		loadStdLib();
		/*
		InputStream in = null;
		try {
			in = new FileInputStream("./a.wsc");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int i;
			while ((i = in.read()) >= 0) {
				out.write(i);
			}
			String script = out.toString("UTF-8");
			test(script);
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		*/

		/*
		test("5+(6*10)");
		
		test("method(\"show\",{" +
				 "	param(0);" +
				 "});" + 
		     "method(\"show_more\",{" +
				 "	show(\"X\"+ show(\"R\") + param(0));" +
				 "});" + 
				 "show_more(\"show me\");");
		
		
		test("123;2342;");

		test("method(\"b2s\"," +
				 "	if(param(0), \"TRUE\", \"FALSE\");" +
				 ");" + 
				 "b2s(2=1);");
		
		test("method(\"b2s\"," +
				 "	if(param(0), \"TRUE\", \"FALSE\");" +
				 ");" + 
				 "b2s(1=1);");
				 
		test("2345/23");
		test("5=6");
		test("7=7");
		test("70/10=7");
		test("20 > 20");
		test("20 >= 20");
		test("20 < 20");
		test("20 = 20");
		test("20 != 20");
		test("20 <= 20");
		test("20 <= 200");
		test("20 = 200");
		test("20 >= 200");
		test("20 > 200");
		test("20 < 200");
		test("20 != 200");

		test("if (1=2, {\"OK\"}, {\"Bad\"})");
		
		test("if (round(3*2.4)=7, {\"yes\"}, {\"no\"})");
		test("if (round(3*2.5)=8, \"yes\", \"no\")");
		test("if (round(3*2.5)=7, \"yes\", \"no\")");
		test("if (trunc(3*2.4)=8, \"yes\", \"no\")");
		test("if (trunc(3*2.4)=7, \"yes\", \"no\")");
		test("if (trunc(3*2.5)=8, \"yes\", \"no\")");
		test("if (trunc(3*2.5)=7, \"yes\", \"no\")");

		test("2345/23 * 5 + 4");

				test("i2f(str2i(i2str(round(4.5) + trunc(4.5) + str2i(\"100\"))))");
		test("111+222");
		test("Hello world!, 123456;");
		test("\"Hello world\"");
		
		test("1234567890123");
		test("123456.7890123");
		test("111+222;");
		test("1.11+2.22");
		test("\"Hello \"+ \" world!\"");
		test("1+2+3+4+5+6+7+8+9");
		*/
	}

	public MethodOperation getMethodByName(String methodName) {
		for (int i = scopes.size() - 1; i >= 0; i--) {
			Scope scope = scopes.get(i);
			MethodOperation result = scope.getMethods().get(methodName);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	public Var call() {
		try {
			return stack.get(stack.size() - 1).call();
		} catch (BreakException e) {
			System.err.println("Unhandeled break");
		} catch (Exception e) {
			System.err.println("Unhandeled exception");
		}
		return null;
	}
	
	public Call push(MethodOperation method, List <Var> params) {
		Call call = new Call(this, method, params, getCurrentScope());
		stack.add(call);
		return call;
	}
	
	public void pop() {
		stack.remove(stack.size() - 1);
	}
	
	public Var call(MethodOperation method, List <Var> params, boolean needsScope) {
		if (needsScope) {
			pushScope();
		}
		push(method, params);
		Var result = call();
		pop();
		if (needsScope) {
			popScope();
		}
		return result;
	}

	public String getStackTrace() {
		if (stack.size() == 0) {
			return "<EMPTY>";
		}
		StringBuilder builder = new StringBuilder();
		int ord = 0;
		for (Call call : stack) {
			if (ord > 0) {
				builder.append('\n');
			}
			builder.append(ord++).append(": ").append(call.getMethod().getName()).append("<-").append(call.getParamsList());
		}
		return builder.toString();
	}

	public List<Call> getStack() {
		return stack;
	}
	
	@Override
	public String toString() {
		return getStackTrace();
	}

	public Scope getRootScope() {
		return scopes.get(0);
	}

	public Scope getCurrentScope() {
		return scopes.get(scopes.size() - 1);
	}

	public Scope pushScope() {
		Scope result = new Scope(this);
		scopes.add(result);
		return result;
	}
	

	public Scope popScope() {
		Scope result = new Scope(this);
		return scopes.remove(scopes.size() - 1);
	}

}