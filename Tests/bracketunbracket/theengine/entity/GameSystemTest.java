package bracketunbracket.theengine.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.GameEvent;

public class GameSystemTest {
	@Test
	public void SortEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		
		Entity e = new Entity();
		entities.add( e );
		
		Entity e1 = new Entity();
		e1.add( new MockComponent() );
		entities.add( e1 );
		
		GameSystem sys = new MockGameSystem();
		
		List<Entity> sortedEntities = sys.sort( entities , MockComponent.class );
		
		assertFalse( sortedEntities.contains( e ) );
		assertTrue( sortedEntities.contains( e1 ) );
		
	}
	
	@Test
	public void SortEMptyList() {
		GameSystem sys = new MockGameSystem();
		sys.sort( new ArrayList<Entity>() , MockComponent.class );
		
		assertTrue( true );
	}
	
	@Test
	public void ContainsEvent() {
		
		class TestEvent extends Event {}
		
		MockGameSystem gameSystem = new MockGameSystem();
		gameSystem.receive( new TestEvent() );
		gameSystem.receive( new TestEvent() );
		
		assertTrue( gameSystem.containsClass( TestEvent.class ) );
	}
	
	@Test
	public void GetAllEventsByClass() {
		
		class TestEvent extends Event {}
		
		MockGameSystem gameSystem = new MockGameSystem();
		gameSystem.receive( new TestEvent() );
		gameSystem.receive( new TestEvent() );
		gameSystem.receive( new Event() );
		gameSystem.receive( new Event() );
		
		List<TestEvent> events = gameSystem.getEventsByClass( TestEvent.class ); 
		
		assertEquals( 2 , events.size() );
	}
	
	@Test
	public void TestEventIsThere() {
		
		MockGameSystem gameSystem = new MockGameSystem();
		gameSystem.events.add( new Event() );
		
		assertFalse( gameSystem.containsClass( GameEvent.class ) );
		
		gameSystem.events.add( new GameEvent( "test" ) );
		assertTrue( gameSystem.containsClass( GameEvent.class ) );
	}
}

