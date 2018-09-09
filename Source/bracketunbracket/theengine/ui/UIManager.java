/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.renderer.GameWindow;

/**
 * @author Michael Eldred
 */
public class UIManager {
	
	public GameWindow window;
	
	private final List<UIObject> objects = new ArrayList<UIObject>();
	
	public UIManager() {
	}
	
	public void add( UIObject obj ) {
		obj.position.position( window.bounds );
		objects.add( obj );
	}
	
	public void render() {
		for( UIObject object : objects ) {
			if( object.drawable == null )
				continue;
			
			object.drawable.position = object.position.screenPos;
			window.renderer.add( object.drawable );
		}
	}
	
	public void clearAll( ) {
		objects.clear();
	}
	
	public boolean receive( Event event ) {
		for( UIObject obj : objects ) {
			if( obj.receive( event ) ) {
				
				return true;
			}
		}
		
		return false;
		
	}
}
