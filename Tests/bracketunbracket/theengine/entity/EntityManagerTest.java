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
}

class MockSystem extends GameSystem {
	public List<Event> received = new ArrayList<Event>();
	@Override
	public void tick(List<Entity> entities) {
		received.addAll( this.events );
		
	}
}
