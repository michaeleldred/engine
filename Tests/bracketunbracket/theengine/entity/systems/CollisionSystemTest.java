/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.components.CollisionComponent;
import bracketunbracket.theengine.entity.components.PositionComponent;

/**
 * @author Michael Eldred
 */
public class CollisionSystemTest {
	
	CollisionSystem system;
	Entity e1;
	Entity e2;
	Entity noCollide;
	List<Entity> entities = new ArrayList<Entity>();
	
	@Before
	public void setup() {
		system = new CollisionSystem();
		e1 = new Entity();
		e1.add( new PositionComponent( 0 , 0 ) );
		e1.add( new CollisionComponent( "collide" , 40 , 50 ) );
		
		e2 = new Entity();
		e2.add( new PositionComponent( 20 , 20 ) );
		e2.add( new CollisionComponent( "collide" , 40 , 50 ) );
		
		noCollide = new Entity();
		noCollide.add( new PositionComponent( 1000 , 1000 ) );
		noCollide.add( new CollisionComponent( "collide" , 40 , 50 ) );
	}
	
	@Test
	public void TestTwoEntitiesCollide() {
		entities.add( e1 );
		entities.add( e2 );
		system.tick( entities );
		
		assertEquals( 1 , e1.getComponentByType( CollisionComponent.class ).collisions.size() );
		assertEquals( 1 , e2.getComponentByType( CollisionComponent.class ).collisions.size() );
		
		entities.clear();
	}
	
	@Test
	public void TestTwoEntitiesDoNotCollide() {
		entities.add( e1 );
		entities.add( noCollide );
		system.tick( entities );
		
		assertEquals( 0 , e1.getComponentByType( CollisionComponent.class ).collisions.size() );
		assertEquals( 0 , noCollide.getComponentByType( CollisionComponent.class ).collisions.size() );
		
		entities.clear();
	}
	
	@Test
	public void EntitiesCollisionsArecleared() {
		entities.add( e1 );
		entities.add( e2 );
		system.tick( entities );
		system.tick( entities );
		
		assertEquals( 1 , e1.getComponentByType( CollisionComponent.class ).collisions.size() );
		assertEquals( 1 , e2.getComponentByType( CollisionComponent.class ).collisions.size() );
		
		entities.clear();
	}
	
	@Test
	public void AllCollisionsComponentsFromAnEntityAreAdded() {
		Entity twoColliders = new Entity();
		twoColliders.add( new PositionComponent() );
		twoColliders.add( new CollisionComponent( "collider" , 40 , 40 ) );
		twoColliders.add( new CollisionComponent( "collider2" , 30 , 30 ) );
		
		entities.add( twoColliders );
		entities.add( e1 );
		
		system.tick( entities );
		
		assertEquals( 2 , e1.getComponentByType( CollisionComponent.class ).collisions.size() );
	}
	
	@Test
	public void GetCollisionPoint() {
		entities.add( e1 );
		entities.add( e2 );
		system.tick( entities );
		
		assertEquals( 1 , e1.getComponentByType( CollisionComponent.class ).collisions.size() );
		assertEquals( 1 , e2.getComponentByType( CollisionComponent.class ).collisions.size() );
		
		entities.clear();
	}
}
