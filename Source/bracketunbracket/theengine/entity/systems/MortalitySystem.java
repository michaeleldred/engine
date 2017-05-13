/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.GameEvent;

/**
 * This system listens to events from an entity and if the event matches the
 * one stored in the component, it removes the entity from the manager.
 * 
 * @author Michael Eldred
 */
public class MortalitySystem extends GameSystem {

	/**
	 * 
	 */
	@Override
	public void tick(List<Entity> entities) {
		List<Entity> sorted = sort( entities , MortalComponent.class );
		
		for( Entity current : sorted ) {
			process( current );
		}
	}
	
	public void process( Entity current ) {
		// Get the name of the entity
		String name = current.getComponentByType( MortalComponent.class ).eventName;
		
		// Go through all of the events looking for one with the same name.
		for( Event evt : current.events ) {
			// If there is an event with the same name, remove the entity
			if( evt instanceof GameEvent && ((GameEvent)evt).name.equalsIgnoreCase( name ) ) {
				this.manager.remove( current );
				break;
			}
		}
		
	}

}
