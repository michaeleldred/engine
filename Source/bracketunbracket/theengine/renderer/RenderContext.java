/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.HashMap;
import java.util.Map;

import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael Eldred
 */
public abstract class RenderContext {
	
	private final Map<String,Image> images = new HashMap<String,Image>();
	
	public abstract void render();
	
	public abstract void execute( RenderCommand command );
	
	public abstract Texture create( String filename );
	
	public abstract void setOffset( Vector2 offset );
	
	public abstract Shader newShader( String vert , String frag );
	
	public void addImage( String name , Image image ) {
		image.name = name;
		images.put( name , image );
	}
	
	public Image getImage( String name ) {
		return images.get( name );
	}

	public abstract void setGameWindow(GameWindow gameWin);
}
