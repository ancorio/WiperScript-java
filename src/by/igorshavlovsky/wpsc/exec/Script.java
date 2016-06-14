package by.igorshavlovsky.wpsc.exec;

import java.util.Set;


public class Script {
	
	private Set <Integer> stringIndexes;
	
	private String executable;

	public Script(Set<Integer> stringIndexes, String executable) {
		super();
		this.stringIndexes = stringIndexes;
		this.executable = executable;
	}

	public String toString() {
		return "EXEC: " + executable;
	}

	public Set<Integer> getStringIndexes() {
		return stringIndexes;
	}

	public void setStringIndexes(Set<Integer> stringIndexes) {
		this.stringIndexes = stringIndexes;
	}

	public String getExecutable() {
		return executable;
	}

	public void setExecutable(String executable) {
		this.executable = executable;
	}
	
}
