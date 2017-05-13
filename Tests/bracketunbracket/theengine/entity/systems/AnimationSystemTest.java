/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.components.AnimationComponent;
import bracketunbracket.theengine.entity.components.RenderComponent;

/**
 * @author Michael Eldred
 */
public class AnimationSystemTest {
	@Test
	public void AddFramesToAnimation() {
		AnimationComponent animation = new AnimationComponent( 10 , "test1" , "test2" );
		
		assertEquals( 2 , animation.frames.size() );
	}
	
	@Test
	public void AdvanceFrame() {
		AnimationSystem system = new AnimationSystem();
		AnimationComponent animation = new AnimationComponent( 1 , "test1" , "test2" );
		
		List<Entity> entities = new ArrayList<Entity>();
		Entity e = new Entity();
		e.add( new RenderComponent( "test1" , 24 , 24 ) );
		e.add( animation );
		
		entities.add( e );
		system.tick(entities);
		
		assertEquals( 1 , animation.currentFrame );
	}
	
	@Test
	public void AnimationUpdatesRenderObject() {
		AnimationSystem system = new AnimationSystem();
		AnimationComponent animation = new AnimationComponent( 1 , "test1" , "test2" );
		
		RenderComponent render = new RenderComponent( "test1" , 24 , 24 );
		
		List<Entity> entities = new ArrayList<Entity>();
		Entity e = new Entity();
		e.add( render );
		e.add( animation );
		
		entities.add( e );
		system.tick(entities);
		
		assertEquals( "test2" , render.obj.imName );
	}
	
	@Test
	public void FrameResetsOnOverflow() {
		AnimationSystem system = new AnimationSystem();
		AnimationComponent animation = new AnimationComponent( 1 , "test1" , "test2" );
		
		List<Entity> entities = new ArrayList<Entity>();
		Entity e = new Entity();
		e.add( new RenderComponent( "test1" , 24 , 24 ) );
		e.add( animation );
		
		entities.add( e );
		system.tick(entities);
		system.tick(entities);
		
		assertEquals( 0 , animation.currentFrame );
	}
	
	@Test
	public void LoopBasedOnArgument() {
		AnimationSystem system = new AnimationSystem();
		AnimationComponent animation = new AnimationComponent( 1 , false , "test1" , "test2" );
		
		List<Entity> entities = new ArrayList<Entity>();
		Entity e = new Entity();
		e.add( new RenderComponent( "test1" , 24 , 24 ) );
		e.add( animation );
		
		entities.add( e );
		system.tick(entities);
		system.tick(entities);
		
		assertEquals( 1 , animation.currentFrame );
	}
}
