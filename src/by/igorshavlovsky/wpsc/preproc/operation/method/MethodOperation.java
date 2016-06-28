package by.igorshavlovsky.wpsc.preproc.operation.method;

import java.util.List;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.preproc.ListOperation;
import by.igorshavlovsky.wpsc.preproc.Operation;
import by.igorshavlovsky.wpsc.preproc.ScriptLink;
import by.igorshavlovsky.wpsc.var.Var;

public class MethodOperation extends ListOperation {

	private String name;
	
	public MethodOperation(String name, ScriptLink link) {
		super(link);
		this.name = name;
	}
	
	public MethodOperation(String name, List <Operation> operations, ScriptLink link) {
		super(link);
		this.name = name;
		for (Operation operation : operations) {
			addOperation(operation);
		}
	}
	
	public MethodOperation(String name, Operation operation, ScriptLink link) {
		super(link);
		this.name = name;
		addOperation(operation);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		if (name != null && name.length() > 0) {
			return name;
		} else {
			return super.toString();
		}
	}

	public boolean isBlockScope() {
		return true;
	}
	
	@Override
	public Var resolve(Call call) {
		Var result = null;
		for (Operation operation : operations) {
			try {
				result = operation.resolve(call);				
			} catch (ReturnException e) {
				if (!isBlockScope()) {
					throw e;
				}
				if (e.getResult() != null) {
					result = e.getResult(); 
				}
				break;
			}
		}
		return result.unwrap();
	}
	

}
