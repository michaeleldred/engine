/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.event;

/**
 * Listens for events that are produced by the various systems in the engine.
 * 
 * @author Michael Eldred
 */
public interface EventListener {
	
	/**
	 * Receives an event from any {@link EventManager} that this listener has 
	 * been added to. Currently each listener will receive any event that is 
	 * added to the EventManager so this method will have to sort through them
	 * to decide if the Event is something it wants to deal with.
	 * 
	 * @param event The event that is sent to the listener
	 */
	public void receive( Event event );
	
}
