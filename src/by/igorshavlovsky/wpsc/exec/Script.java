package by.igorshavlovsky.wpsc.exec;

import java.util.Set;


public class Script {
	
	protected Set <Integer> stringIndexes;
	
	protected String executable;

	public Script(String executable, Set<Integer> stringIndexes) {
		super();
		this.stringIndexes = stringIndexes;
		this.executable = executable;
	}

	public String toString() {
		return "EXEC: " + getScript();
	}

	protected int convertedIndex(int index) {
		return index;
	}

	protected int convertedLength() {
		return executable.length();
	}

	protected int convertedStart() {
		return 0;
	}
	
	public boolean isInString(int index) {
		return stringIndexes.contains(Integer.valueOf(convertedIndex(index)));
	}

	public int getLength() {
		return convertedLength();
	}

	public int getLastIndex() {
		return getStart() + getLength();
	}

	public String getSource() {
		return executable;
	}

	public String getScript() {
		return executable.substring(getStart(), getLastIndex());
	}

	public final int getStart() {
		return convertedStart();
	}
	
}
