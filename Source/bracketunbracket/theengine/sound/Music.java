/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;

/**
 * @author Michael Eldred
 */
public abstract class Music {
	private List<EventListener> eventListeners = new ArrayList<EventListener>();
	public void addEventListener( EventListener listener ) {
		eventListeners.add( listener );
	}
	
	public void loaded() {
		for( EventListener listener : eventListeners ) {
			listener.receive( new Event() );
		}
	}
}
