/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author Michael Eldred
 */
public class EventManager {
	
	public final List<EventListener> listeners = new ArrayList<EventListener>();
	
	public final Map< Class< ? extends Event> , List<EventListener> > evListeners = new HashMap< Class< ? extends Event> , List<EventListener> >();
	private final List<EventListener> toAdd = new ArrayList<EventListener>();
	private boolean add = true;
	
	/**
	 * Adds a listener that listens to all events that are added to the event
	 * manager.
	 * 
	 * @param listener
	 */
	public void addListener( EventListener listener ) {
		listeners.add( listener );
	}
	
	/**
	 * Adds a listener that only listens for events of a specific type.
	 * 
	 * @param listener The EventListener to add.
	 * @param type     The type of event to listen for.
	 */
	public void addListener( EventListener listener , Class<? extends Event> type ) {
		toAdd.add( listener );
		
		if( add ) {
			for( ListIterator<EventListener> it = toAdd.listIterator(); it.hasNext(); ) {
				
				EventListener l = it.next();
				
				List<EventListener> typeList = evListeners.get( type );
				// If no event listeners have been added of the same type.
				if( typeList == null ) {
					
					// Create a new list to add
					typeList = new ArrayList<EventListener>();
					typeList.add( l );
					
					evListeners.put( type , typeList );
				} else {
					typeList.add( l );
				}
			}
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void sendEvent( Event event ) {
		
		add = false;
		
		// Get the event type and give this event to listeners that ask for it.
		List<EventListener> typeList = evListeners.get( event.getClass() );
		
		if( typeList != null ) {
			for( EventListener listener : typeList ) {
				listener.receive( event );
			}
		}
		
		// TODO: Get rid of this code.
		// Currently does no sorting of events, each event is passed to every
		// listener.
		for( EventListener listener : listeners ) {
			
			listener.receive( event );
		}
		
		add = true;
	}
}
