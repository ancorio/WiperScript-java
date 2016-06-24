package by.igorshavlovsky.wpsc.preproc;

import java.util.ArrayList;
import java.util.List;

import by.igorshavlovsky.wpsc.exec.Call;
import by.igorshavlovsky.wpsc.var.Var;

public class ListOperation extends Operation {
	
	protected List <Operation> operations = new ArrayList<>(8);
		
	public ListOperation(ScriptLink link) {
		super(link);
	}
	
	public void addOperation(Operation operation) {
		operations.add(operation);
	}

	@Override
	public Var resolve(Call call) {
		Var result = null;
		for (Operation operation : operations) {
			result = operation.resolve(call);
		}
		return result;
	}
	

	public String getScript() {
		return "TODO";
	}

	public List<Operation> getOperations() {
		return operations;
	}
	
	@Override
	public String toString() {
		return "List: " + operations + ";";
	}

}
