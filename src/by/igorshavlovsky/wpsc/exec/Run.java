package by.igorshavlovsky.wpsc.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.igorshavlovsky.wpsc.lib.std.I2FMethod;
import by.igorshavlovsky.wpsc.lib.std.I2StrMethod;
import by.igorshavlovsky.wpsc.lib.std.IfMethod;
import by.igorshavlovsky.wpsc.lib.std.MethodDefineMethod;
import by.igorshavlovsky.wpsc.lib.std.ParamMethod;
import by.igorshavlovsky.wpsc.lib.std.RoundMethod;
import by.igorshavlovsky.wpsc.lib.std.Str2IMethod;
import by.igorshavlovsky.wpsc.lib.std.TruncMethod;
import by.igorshavlovsky.wpsc.var.Var;

public class Run extends MethodContainer {

	private List<Call> stack = new ArrayList<>(64);
	
	//@import();
	//@param();
	//@params_count();
	//@method()@end;
	//@return();
	//@return_append();
	//@style()???
	//@var()
	//@var_set()
	//@var_get()
	//@if()
	//@else
	//@endif

	//eq(A, B)
	//not(A)
	//comp(A, B)
	//or(A, B...)
	//and(A, B...)
	//d2i()
	//i2d()
	//str()
	//str_len()
	//{
	//}
	
	private void loadStdLib() {
		loadMethod(new I2StrMethod());
		loadMethod(new TruncMethod());
		loadMethod(new RoundMethod());
		loadMethod(new Str2IMethod());
		loadMethod(new I2FMethod());
		loadMethod(new IfMethod());
		loadMethod(new MethodDefineMethod());
		loadMethod(new ParamMethod());
	}
	
	
	private void test(String script) {
		System.out.println(call(new CustomMethod("Main", script), new ArrayList<Var>(), null));
	}

	public Run() {
		super();
		
		loadStdLib();
		

		test("5+6*10");
		test("5+(6*10)");
		
		test("method(\"show\",{" +
				 "	param(0);" +
				 "});" + 
		     "method(\"show_more\",{" +
				 "	show(\"X\"+ show(\"R\") + param(0));" +
				 "});" + 
				 "show_more(\"show me\");");
		
		/*
		
		test("123;2342;");

		test("method(\"b2s\"," +
				 "	if(param(0), \"TRUE\", \"FALSE\");" +
				 ");" + 
				 "b2s(2=1);");
		
		test("method(\"b2s\"," +
				 "	if(param(0), \"TRUE\", \"FALSE\");" +
				 ");" + 
				 "b2s(1=1);");
				 
				 */
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
		test("\"Hello!\\\" Voktor!\"");
		test("1234567890123");
		test("123456.7890123");
		test("111+222");
		test("1.11+2.22");
		test("\"Hello \"+ \" world!\"");
		test("1+2+3+4+5+6+7+8+9");
	}
	
	public Method getMethodByName(String methodName) {
		for (int i = stack.size() - 1; i >= 0; i--) {
			Call call = stack.get(i);
			Method result = call.getMethods().get(methodName);
			if (result != null) {
				return result;
			}
		}
		Method result = getMethods().get(methodName);
		if (result != null) {
			return result;
		}
		throw new RunException("Cannot find method " + methodName);
	}
	
	public Var call() {
		return stack.get(stack.size() - 1).call();
	}
	
	public Call push(Method method, List <Var> params, Call parentCall) {
		Call call = new Call(this, method, params, parentCall);
		stack.add(call);
		return call;
	}
	
	public void pop() {
		stack.remove(stack.size() - 1);
	}

	
	public Var call(Method method, List <Var> params, Call parentCall) {
		push(method, params, parentCall);
		Var result = call();
		pop();
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
}
