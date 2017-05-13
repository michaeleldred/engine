/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.RenderComponent;

/**
 * @author Michael Eldred
 */
public class RenderSystemTest {
	@Test
	public void DoesNotRenderEntityNoComponents() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		entities.add( new Entity() );
	
		RenderSystem r = new RenderSystem( );
		r.tick( entities );
		
		//assertEquals( 0 , r.objects.size() );
	}
	
	@Test
	public void RendersEntityWithPositionAndRenderComponent() {
		ArrayList<Entity> entities = new ArrayList<Entity>();
		Entity e = new Entity();
		e.add( new PositionComponent() );
		e.add( new RenderComponent( "test", 10 , 10 ) );
		
		entities.add( e );
		
		RenderSystem r = new RenderSystem( );
		r.tick( entities );
		
		assertEquals( 1 , r.objects.size() );
	}

}
