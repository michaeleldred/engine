/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael Eldred
 */
public class EntityTest {
	@Test
	public void AddComponentToEntity() {
		Entity entity = new Entity();
		Component c = new Component();
		
		entity.add( c );
		
		assertTrue( entity.getAllComponents().contains( c ) );
	}
	
	@Test
	public void AddComponentToEntityCreatesEvent() {
		Entity ent = new Entity();
		MockComponent c = new MockComponent();
		
		ent.add( c );
		
		assertEquals( c , ((ComponentChangeEvent)ent.events.get( 0 )).component );
		
	}
	
	@Test
	public void GetsComponentWithCorrectType() {
		Entity entity = new Entity();
		Component c = new Component();
		
		entity.add( c );
		
		assertEquals( c , entity.getComponentByType( Component.class ) );
	}
	
	@Test
	public void GetComponentDoesNotReturnBadType() {
		Entity entity = new Entity();
		Component c = new Component();
		
		entity.add( c );
		
		assertEquals( null , entity.getComponentByType( MockComponent.class ) );
	}
	
	@Test
	public void ComponentGetParentEntity() {
		Entity entity = new Entity();
		Component c = new Component();
		
		entity.add( c );
		
		assertEquals( entity , c.parent );
	}
	
	@Test
	public void GetOnlyTheComponentsOfAType() {
		Entity entity = new Entity();
		Component c = new Component();
		entity.add( c );
		
		
		c = new Component();
		entity.add( c );
		
		c = new Component();
		
		entity.add( c );
		
		assertEquals( 3 , entity.getAllComponentsByType( Component.class ).size() );
	}
	
	@Test
	public void RemoveComponent() {
		Entity entity = new Entity();
		Component c = new Component();
		
		entity.add( c );
		entity.remove( Component.class );
		
		assertFalse( entity.getAllComponents().contains( c ) );
	}
	
	@Test
	public void CloneEntityNotEqual() {
		Entity entity = new Entity();
		MockComponent mc = new MockComponent();
		entity.add( mc );
		
		Entity clone = entity.clone();
		
		assertNotEquals( entity , clone );
		assertTrue( mc.cloned );
	}
}
