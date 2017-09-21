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
	
	protected EventManager eventManager;
	
	/**
	 * Update the state of the game system
	 */
	public abstract void tick( List<Entity> entities );
	
	public void init() {
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
}
