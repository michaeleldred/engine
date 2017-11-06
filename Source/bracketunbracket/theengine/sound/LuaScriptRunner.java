package bracketunbracket.theengine.sound;

import java.io.InputStream;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaClosure;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import bracketunbracket.theengine.resources.FileLoader;

public class LuaScriptRunner extends ScriptRunner {

	private Globals globals;
	
	public LuaScriptRunner() {
		globals = JsePlatform.standardGlobals();
	}
	
	public void addLib( String source ) throws Exception {
		InputStream is = FileLoader.loadFilenameAsStream( source );
		
		LuaValue module = globals.load( is , "sound" , "bt" , globals );
		if( module != null ) {
			globals.get( "package" ).get( "preload" ).set( "sound" , module );
			globals.get( "require" ).call( "sound" );
		}
		
		is.close();
	}
	
	public void addObject( String name , Object obj ) {
		globals.rawset( name , CoerceJavaToLua.coerce( obj ) );
	}
	
	@Override
	public LuaValue run( String scriptname ) {
		LuaScript s = (LuaScript)scripts.get( scriptname );
		
		LuaClosure c = (LuaClosure)globals.load( s.script );
		LuaValue retVal = c.call();
		return retVal;
	}

	@Override
	public void load(String scriptname, String script) {
		add( scriptname , new LuaScript( script ) );		
	}

}
