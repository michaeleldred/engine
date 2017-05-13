/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.HashMap;
import java.util.Map;

import bracketunbracket.theengine.resources.FileLoader;
import bracketunbracket.theengine.resources.ResourceLoader;
import bracketunbracket.theengine.resources.ResourceManager;

/**
 * @author Michael Eldred
 */
public class ShaderLoader implements ResourceLoader {

	private Map<String,String> frags = new HashMap<String,String>();
	private Map<String,String> verts = new HashMap<String,String>();
	
	private final Renderer renderer;
	
	public ShaderLoader( Renderer renderer ) {
		this.renderer = renderer;
	}
	
	@Override
	public void create(ResourceManager manager, Map<String, String> vals) throws Exception {
		if( vals.get( "type" ).equalsIgnoreCase( "vert" ) ) {
			String shader = FileLoader.loadFilenameAsString( "Shaders/" + vals.get( "filename" ) );
			verts.put( vals.get( "name" ) , shader );
		} else if( vals.get( "type" ).equalsIgnoreCase( "frag" ) ) {
			String shader = FileLoader.loadFilenameAsString( "Shaders/" + vals.get( "filename" ) );
			frags.put( vals.get( "name" ) , shader );
		} else if( vals.get( "type" ).equalsIgnoreCase( "shader" ) ) {
			Shader s = renderer.context.newShader( verts.get( vals.get( "vert" ) ) , frags.get( vals.get( "frag" ) ) );
			renderer.shaders.put( vals.get( "name" ) , s );
		}
	}
}
