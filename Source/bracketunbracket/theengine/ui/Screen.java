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
public abstract class Screen {
	
	public final List<Event> events = new ArrayList<Event>();
	public UIManager manager = new UIManager();
	
	protected GameWindow parent = null;
	
	/**
	 * 
	 * @return True if this is the last screen that should update.
	 */
	public boolean update() {
		return true;
	}
	
	public abstract void render();
	public abstract void create();
	
	public void postRender() {
		manager.render();
	}
	
	/**
	 * Get an event from a manager.
	 * 
	 * @param e
	 */
	public void receive( Event e ) {
		
		// Only let the screen have it if the UI doesn't use it.
		if( !manager.receive( e ) ) {
			events.add( e );
		}
	}
	
	public void setParent( GameWindow parent ) {
		manager.window = parent;
		this.parent = parent;
	}
	
	public GameWindow getParent() {
		return parent;
	}
	public void remove() {
	}
}
