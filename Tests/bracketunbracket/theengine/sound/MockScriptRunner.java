/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

/**
 * @author Michael Eldred
 */
public class MockScriptRunner extends ScriptRunner {

	public String lastScript = null;
	
	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.ScriptRunner#run(java.lang.String)
	 */
	@Override
	public LuaValue run( String scriptname ) {
		System.out.println( scriptname );
		lastScript = scriptname;
		return CoerceJavaToLua.coerce( new ScriptedSound( scriptname ) );
	}

	@Override
	public void load(String scriptname, String script) {
		
	}

}
