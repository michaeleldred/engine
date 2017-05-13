/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.EntityManager;
import bracketunbracket.theengine.entity.components.TimedComponent;
import bracketunbracket.theengine.entity.components.TimedSystem;
import bracketunbracket.theengine.event.GameEvent;

/**
 * @author Michael Eldred
 */
public class TimedSystemTest {
	@Test
	public void ReceiveEventAfterTicks() {
		EntityManager manager = new EntityManager();
		
		TimedSystem system = new TimedSystem();
		GameEvent event = new GameEvent( "Test" );
		Entity entity = new Entity();
		entity.add( new TimedComponent( 1 , event ) );
		
		manager.add( entity );
		manager.addSystem( system );
		
		manager.update();
		
		assertTrue( entity.events.contains( event ) );
	}
}
