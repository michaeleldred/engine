/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;

/**
 * @author Michael Eldred
 */
public abstract class Texture {
	private List<EventListener> eventListeners = new ArrayList<EventListener>();
	
	public abstract int getID();
	
	public void addEventListener( EventListener listener ) {
		if( listener != null)
			eventListeners.add( listener );
	}
	
	public void loaded() {
		for( EventListener listener : eventListeners ) {
			listener.receive( new Event() );
		}
	}
}
