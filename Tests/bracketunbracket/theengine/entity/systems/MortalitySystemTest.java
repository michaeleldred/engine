package bracketunbracket.theengine.entity.systems;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.EntityManager;
import bracketunbracket.theengine.event.GameEvent;

public class MortalitySystemTest {
	@Test
	public void EntityIsRemovedOnEvent() {
		EntityManager manager = new EntityManager();
		
		Entity e = new Entity();
		e.add( new MortalComponent( "test" ) );
		e.events.add( new GameEvent( "test" ) );
		
		manager.add( e );
		manager.addSystem( new MortalitySystem() );
		
		manager.update();
		
		assertFalse( manager.entities.contains( e ) );
	}
}
