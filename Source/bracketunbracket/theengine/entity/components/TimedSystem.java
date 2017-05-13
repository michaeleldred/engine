/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;

/**
 * A system that sends an event after a certain amount of time has occurred.
 * 
 * @author Michael Eldred
 */
public class TimedSystem extends GameSystem {

	@Override
	public void tick(List<Entity> entities) {
		List<Entity> sorted = sort( entities , TimedComponent.class );
		
		for( Entity entity : sorted ) {
			process ( entity );
		}
	}
	
	public void process( Entity current ) {
		List<TimedComponent> timers = current.getAllComponentsByType( TimedComponent.class );
		
		for( TimedComponent tc : timers ) {
			if( ++tc.current >= tc.length ) {
				current.receiveEvent( tc.event );
				tc.current = 0;
			}
		}
	}

}
