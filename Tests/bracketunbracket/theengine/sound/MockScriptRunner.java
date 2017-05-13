/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

/**
 * @author Michael Eldred
 */
public class MockScriptRunner extends ScriptRunner {

	public String lastScript = null;
	
	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.ScriptRunner#run(java.lang.String)
	 */
	@Override
	public void run( String scriptname) {
		System.out.println( scriptname );
		lastScript = scriptname;
	}

	@Override
	public void load(String scriptname, String script) {
		
	}

}
