/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import java.util.HashMap;
import java.util.Map;

import org.luaj.vm2.LuaValue;

/**
 * @author Michael Eldred
 */
public abstract class ScriptRunner {
	
	final Map< String , Script > scripts = new HashMap< String , Script >();
	
	public abstract LuaValue run( String scriptname );
	public abstract void load( String scriptname , String script );
	
	public void add( String name , Script script ) {
		scripts.put( name , script );
	}
	
}
