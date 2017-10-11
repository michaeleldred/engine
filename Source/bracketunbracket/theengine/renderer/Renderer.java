/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The main object in a window that draws stuff to the screen.
 * 
 * @author Michael Eldred
 */
public class Renderer {
	
	public final List<RenderObject> objects = new ArrayList<RenderObject>();
	public Map<String,Shader> shaders = new HashMap<String,Shader>();
	
	public final RenderContext context;
	
	/**
	 * A list of objects that have already had their children added to the
	 * master list of objects to be drawn 
	 */
	private final List<RenderObject> added = new ArrayList<RenderObject>();
	
	public final Camera camera = new Camera();
	
	/**
	 * Compares the sprites, objects with lower layers will be first in the list.
	 */
	private Comparator<RenderObject> spriteSorter = new Comparator<RenderObject>() {

		@Override
		public int compare( RenderObject o1, RenderObject o2 ) {
			return o1.layer - o2.layer;
		}
	};
	
	public Renderer( RenderContext context ) {
		this.context = context;
	}
	
	public void render() {
		// Don't bother drawing if there are no objects
		if( objects.size() <= 0 )
			return;
		
		// Sort the sprites
		Collections.sort( objects , spriteSorter );
		
		// TODO: Remove new
		// Create the initial command to add objects to
		RenderCommand command = new RenderCommand();
		command.texture = objects.get( 0 ).image == null ? null : objects.get( 0 ).image.texture;
		
		for( int i = 0; i < objects.size(); i++ ) {
			RenderObject obj = objects.get( i );
			
			// First check for children to draw before this
			if( obj.children.size() > 0 && !added.contains( obj ) ) {
				
				// Sort the children before adding
				Collections.sort( obj.children , spriteSorter );
				
				// Add all of the children at the current spot
				for( int j = obj.children.size() - 1; j >= 0; j-- ) {
					objects.add( i , obj.children.get( j ) );
				}
				
				added.add( obj );
				
				// Reset i to do this again
				i--;
				continue;
			}
			
			// Check to see if we can stash the image, to improve the optimisation
			if( obj.imName != null && ( obj.image == null || ( !obj.imName.equals( obj.image.name ) ) ) ) {
				obj.image = context.getImage( obj.imName );
			}
			
			// Set the texture to null if there is no image
			Texture tex = obj.image == null ? null : obj.image.texture;
			
			// If there is a change in state, which is a new texture or shader,
			// or if the command is full, create a new render command
			if( command.texture != tex || command.shader != shaders.get( obj.effect ) || command.isFull() ) {
				context.execute( command );
				// TODO: remove new
				command = new RenderCommand();
				command.texture = tex;
				command.shader = shaders.get( obj.effect );
			}
			
			// Update the anumation
			for( Animation anim : obj.animations )
				anim.update( 0.016666667f );
			
			// Add the object to the command
			command.add( obj );
		}
		
		// Execute the last command
		context.execute( command );
		
		objects.clear();
		added.clear();
	}
	
	public void add( RenderObject obj ) {
		objects.add( obj );
	}
	
	public void remove( RenderObject obj ) {
		objects.remove( obj );
	}
}
