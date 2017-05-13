/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Eldred
 */
public class RenderCommand {
	public Texture texture;
	public Shader shader;
	
	/**
	 * The list of the objects to be rendered by this command.
	 */
	private final List<RenderObject> objects = new ArrayList<RenderObject>( MAX_OBJECTS );
	
	/**
	 * The location of the next object to be added.
	 */
	private int curr = 0;
	
	/**
	 * The maximum amount of render objects to be rendered by this command
	 */
	public final static int MAX_OBJECTS = 512;
	
	/**
	 * Resets this render command
	 */
	public void clear() {
		texture = null;
		shader = null;
		objects.clear();
	}
	
	public boolean isFull() {
		return curr >= MAX_OBJECTS;
	}
	
	public List<RenderObject> getObjects() {
		return objects;
	}

	public void add( RenderObject obj ) {
		
		if( objects.size() >= curr ) { 
			objects.add( new RenderObject( null , null , 0 , 0 , 0 ) );
		}
		
		objects.get( curr ).set( obj );
		objects.get( curr ).position = obj.getAbsolutePosition();
		
		curr++;
	}
}
