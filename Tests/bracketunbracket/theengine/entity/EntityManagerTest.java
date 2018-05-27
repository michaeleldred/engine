/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.GameEvent;

public class EntityManagerTest {
	@Test
	public void AddEntityToEntityManager() {
		EntityManager manager = new EntityManager();
		Entity e = new Entity();
		
		manager.add( e );
		
		assertTrue( manager.entities.contains( e ) );
	}
	
	@Test
	public void AddGameSystemToEntityManager() {
		EntityManager manager = new EntityManager();
		MockGameSystem gs = new MockGameSystem();
		
		manager.addSystem( gs );
		
		assertTrue( manager.systems.contains( gs ) );
	}
	
	@Test
	public void RemoveGameSystemToEntityManager() {
		EntityManager manager = new EntityManager();
		MockGameSystem gs = new MockGameSystem();
		
		manager.addSystem( gs );
		manager.removeSystem( gs );
		
		assertFalse( manager.systems.contains( gs ) );
	}
	
	@Test
	public void GameSystemTickedByEntityManager() {
		EntityManager manager = new EntityManager();
		MockGameSystem gs = new MockGameSystem();
		
		manager.addSystem( gs );
		
		manager.update();
		
		assertTrue( gs.wasTicked );
	}
	
	@Test
	public void GameSystemGetsEntitiesFromEntityManager() {
		EntityManager manager = new EntityManager();
		MockGameSystem gs = new MockGameSystem();
		Entity e = new Entity();
		manager.add( e );
		
		manager.addSystem( gs );
		
		manager.update();
		
		assertTrue( gs.entities.contains( e ) );
	}
	
	@Test
	public void EventPassedToSystems() {
		EntityManager manager = new EntityManager();
		MockSystem gs = new MockSystem();
		
		manager.addSystem( gs );
		
		Event evt = new Event();
		manager.receive( evt );
		
		manager.update();

		assertTrue( gs.received.contains( evt ) );
	}
	
	@Test
	public void ManagerAddedToSystems() {
		EntityManager manager = new EntityManager();
		MockGameSystem gs = new MockGameSystem();
		
		manager.addSystem( gs );
		
		assertEquals( manager , gs.manager );
	}
	
	@Test
	public void AddEntityInSystemLoop() {
		EntityManager manager = new EntityManager();
		Entity e = new Entity();
		final Entity ent = new Entity();
		manager.add( e );
		
		GameSystem system = new GameSystem() {
			
			@Override
			public void tick(List<Entity> entities) {
				for( int i = 0; i < entities.size(); i++ ) {
					this.manager.add( ent );
				}
			}
		};
		
		manager.addSystem( system );
		manager.update();
		
		
		assertTrue( manager.entities.contains( ent ) );
	}
	
	@Test
	public void removeEntity() {
		EntityManager manager = new EntityManager();
		Entity e = new Entity();
		
		manager.add( e );
		
		manager.remove( e );
		
		assertFalse( manager.entities.contains( e ) );
	}
	
	@Test
	public void RemoveEntityInSystemLoop() {
		EntityManager manager = new EntityManager();
		Entity e = new Entity();
		final Entity ent = new Entity();
		manager.add( e );
		manager.add( ent );
		
		GameSystem system = new GameSystem() {
			
			@Override
			public void tick(List<Entity> entities) {
				for( int i = 0; i < entities.size(); i++ ) {
					manager.remove( ent );
				}
			}
		};
		
		manager.addSystem( system );
		manager.update();
		
		assertFalse( manager.entities.contains( ent ) );
	}
	
	@Test
	public void SystemGetsEventsInNextFrame() {
		EntityManager manager = new EntityManager();
		MockSystem mock = new MockSystem();
		manager.addSystem( mock );
		manager.addSystem( new GameSystem() {
			
			@Override
			public void tick( List<Entity> entities ) {
				manager.receive( new GameEvent( "turn" ) );
			}
		});
		
		manager.update();
		assertEquals( 0 , mock.received.size() );
		
		manager.update();
		assertEquals( 1 ,  mock.received.size() );
	}
	
	@Test
	public void SystemGetsInitialized() {
		EntityManager manager = new EntityManager();
		MockSystem mock = new MockSystem();
		
		manager.addSystem( mock );
		
		assertTrue( mock.init );
	}
	
	@Test
	public void SystemsGetKilledOnRemoval() {
		EntityManager manager = new EntityManager();
		MockSystem mock = new MockSystem();
		
		manager.addSystem( mock );
		manager.removeSystem( mock );
		
		assertTrue( mock.destroyed );
	}
	
	@Test
	public void AllSystemsGetDestroyedOnManagerDestroyed() {
		EntityManager manager = new EntityManager();
		MockSystem mock1 = new MockSystem();
		MockSystem mock2 = new MockSystem();
		
		manager.addSystem( mock1 );
		manager.addSystem( mock2 );
		manager.destroy();
		
		assertTrue( mock1.destroyed );
		assertTrue( mock2.destroyed );
	}
	
	@Test
	public void AllEntitiesGetEventOnUpdate() {
		EntityManager manager = new EntityManager();
		GameEvent gameEvent = new GameEvent( "TEST" );
		
		Entity entity = new Entity();
		manager.add( entity );
		
		manager.receive( gameEvent );
		
		manager.update();
		
		assertTrue( entity.events.contains( gameEvent ) );
	}
	
	@Test
	public void AllEntitiesGetAdded() {
		EntityManager manager = new EntityManager();
		
		Entity e1 = new Entity();
		Entity e2 = new Entity();
		
		List<Entity> entities = new ArrayList<Entity>();
		entities.add( e1 );
		entities.add( e2 );
		
		manager.addAll( entities );
		
		assertTrue( manager.entities.contains( e1 ) );
		assertTrue( manager.entities.contains( e2 ) );
	}
}

class MockSystem extends GameSystem {
	public List<Event> received = new ArrayList<Event>();
	public boolean init = false;
	public boolean destroyed = false;
	@Override
	public void init() {
		this.init = true;
	}
	@Override
	public void tick(List<Entity> entities) {
		received.addAll( this.events );
		
	}
	
	@Override
	public void destroy() {
		this.destroyed = true;
	}
}
