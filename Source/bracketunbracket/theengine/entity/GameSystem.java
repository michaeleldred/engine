/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventManager;

/**
 * @author Michael Eldred
 */
public abstract class GameSystem {
	
	public final List<Event> events = new ArrayList<Event>();
	
	public EntityManager manager;
	
	public EventManager eventManager;
	
	/**
	 * Update the state of the game system
	 */
	public abstract void tick( List<Entity> entities );
	
	public void init() {
	}
	public void destroy() {
	}
	
	public void receive( Event event ) {
		events.add( event );
	}
	
	@SafeVarargs
	public final List<Entity> sort( List<Entity> entities , Class<? extends Component>...required   ) {
		List<Entity> sortedEntities = new ArrayList<Entity>();
		
		for( Entity e : entities ) {
			boolean add = true;
			
			// Go through all of the required components to see if the entity has it.
			for( int i = 0; i < required.length && add; i++ ) {
				if( e.getComponentByType( required[ i ] ) == null ) {
					add = false;
				}
			}
			
			// if the entity has all of the components, add it to the list.
			if( add )
				sortedEntities.add( e );
		}
		
		return sortedEntities;
	}
	
	/**
	 * Checks to see if a type of event has been passed to this system in the
	 * last frame.
	 * 
	 * @param  eventType The type of event to check for
	 * @return {@code true} if the system has received an event of the
	 *         {@code eventType} class.
	 */
	public boolean containsClass( Class< ? extends Event> eventType ) {
		return containsClass(eventType , this.events );
	}
	
	/**
	 * Checks to see if a type of event is in the list
	 * last frame.
	 * 
	 * @param  eventType The type of event to check for
	 * @return {@code true} if the system has received an event of the
	 *         {@code eventType} class.
	 */
	public boolean containsClass( Class< ? extends Event> eventType , List<Event> events ) {
		for( Event evt : events ) {
			
			if( eventType.isInstance( evt ) ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks to see if a type of event has been passed to this system in the
	 * last frame.
	 * 
	 * @param  eventType The type of event to check for
	 * @return {@code true} if the system has received an event of the
	 *         {@code eventType} class.
	 */
	public <T extends Event> List< T > getEventsByClass( Class< T > eventType ) {
		return getEventsByClass( eventType , this.events );
	}
	
	/**
	 * Checks to see if a type of event has been passed to this system in the
	 * last frame.
	 * 
	 * @param  eventType The type of event to check for
	 * @return {@code true} if the system has received an event of the
	 *         {@code eventType} class.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Event> List< T > getEventsByClass( Class< T > eventType , List<Event> events ) {
		List< T > retVal = new ArrayList< T >();
		
		for( Event evt : events ) {
			
			if( eventType.isInstance( evt ) ) {
				retVal.add( (T)evt );
			}
		}
		return retVal;
	}
}
