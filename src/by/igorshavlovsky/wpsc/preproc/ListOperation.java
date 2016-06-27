package by.igorshavlovsky.wpsc.preproc;

import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.impl.activation.NameServiceStartThread;

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
		return result.unwrap();
	}
	

	public String getScript() {
		return toString();
	}

	public List<Operation> getOperations() {
		return operations;
	}
	
	@Override
	public String toString() {
		if (operations.size() == 0) {
			return "-";
		}
		if (operations.size() == 1) {
			return operations.get(0).toString();
		}
		StringBuilder result = new StringBuilder();
		boolean f = false;
		for (Operation op : operations) {
			if (f) {
				result.append(' ');
			} else {
				f = true;
			}
			result.append(op.toString()).append(';');
		}
		return result.toString();
	}

}
