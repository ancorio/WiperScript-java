package by.igorshavlovsky.wpsc.preproc;

public class ScriptLink {

	private String script;
	private int beginIndex;
	private int endIndex;
	
	public ScriptLink(String script, int beginIndex, int endIndex) {
		super();
		this.script = script;
		this.beginIndex = beginIndex;
		this.endIndex = endIndex;
	}
	public int getBeginIndex() {
		return beginIndex;
	}
	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public String getScript() {
		return script;
	}
	public void setScript(String script) {
		this.script = script;
	}
	
	
}
