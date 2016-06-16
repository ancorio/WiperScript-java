package by.igorshavlovsky.wpsc.exec;

public class SubScript extends Script {
	
	private int start;
	
	private int length;
	
	public SubScript(Script script, int start, int length) {
		super(script.executable, script.stringIndexes);
		this.start = start;
		this.length = length;
	}


	protected int convertedLength() {
		return length;
	}

	protected int convertedStart() {
		return start;
	}
	

}
