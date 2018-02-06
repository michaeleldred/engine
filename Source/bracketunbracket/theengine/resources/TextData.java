package bracketunbracket.theengine.resources;

public abstract class TextData extends Data {
	protected String data = null;
	
	public String getData() {
		return data;
	}
	
	public abstract void load();
}