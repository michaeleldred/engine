/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.entity.systems.MockRenderContext;
import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael Eldred
 */
public class RendererTest {
	@Test
	public void AddRenderObjectToRenderer() {
		Renderer renderer = new Renderer(null);
		RenderObject obj = new RenderObject( null , null , 0 , 0 , 0 );
		
		renderer.add( obj );
		
		assertTrue( renderer.objects.contains( obj ) );
	}
	
	@Test
	public void RemoveRenderObjectToRenderer() {
		Renderer renderer = new Renderer(null);
		RenderObject obj = new RenderObject( null , null , 0 , 0 , 0 );
		
		renderer.add( obj );
		renderer.remove( obj );
		
		assertFalse( renderer.objects.contains( obj ) );
	}
	
	@Test
	public void StashImageInRenderObject() {
		MockRenderContext context = new MockRenderContext();
		Image test = new Image( null , 10 , 1, 1, 1, 1, 1 );
		context.addImage( "test" , test );
		
		Renderer renderer = new Renderer( context );
		RenderObject obj = new RenderObject( null , null , 0 , 0 , "test" , 0 , 0 );
		
		renderer.add( obj );
		
		renderer.render();
		
		assertEquals( test , obj.image );
	}
	
	@Test
	public void DontStachImageInRenderObjectIfAlreadyThere() {
		MockRenderContext context = new MockRenderContext();
		Image test = new Image( null , 10 , 1, 1, 1, 1, 1 );
		context.addImage( "test" , test );
		
		Renderer renderer = new Renderer( context );
		RenderObject obj = new RenderObject( null , null , 0 , 0 , "test" , 0 , 0 );
		obj.image = test;
		
		renderer.add( obj );
		
		renderer.render();
		
		assertEquals( 0 , context.imageGet );
	}
	
	@Test
	public void RestashIfImageNameChanges() {
		MockRenderContext context = new MockRenderContext();
		Image test = new Image( null , 10 , 1, 1, 1, 1, 1 );
		Image test2 = new Image( null , 10 , 1, 1, 1, 1, 1 );
		context.addImage( "test" , test );
		context.addImage( "test2" , test2 );
		
		Renderer renderer = new Renderer( context );
		RenderObject obj = new RenderObject( null , null , 0 , 0 , "test" , 0 , 0 );
		obj.image = test2;
		
		renderer.add( obj );
		
		renderer.render();
		
		assertEquals( test , obj.image );
	}
	
	@Test
	public void CreateRenderCommandFromRenderObject() {
		MockRenderContext context = new MockRenderContext();
		Renderer renderer = new Renderer( context );
		RenderObject obj = new RenderObject( null , null , 0 , 0 , 0 );
		
		renderer.add( obj );
		
		renderer.render();
		
		assertTrue( context.commands.get( 0 ).getObjects().contains( obj ) );
	}
	
	@Test
	public void RenderCommandGetsImageFromObject() {
		MockRenderContext context = new MockRenderContext();
		
		Image test = new Image( new Texture() {
			@Override
			public int getID() {
				return 2;
			}
		} , 10 , 1, 1, 1, 1, 1 );
		
		context.addImage( "test" , test );
		
		Renderer renderer = new Renderer( context );
		RenderObject obj = new RenderObject( null , null , 0 , 0 , "test" , 0 , 0 );
		
		renderer.add( obj );
		
		renderer.render();
		
		assertEquals( test.texture , context.commands.get( 1 ).texture );
	}
	
	@Test
	public void RenderCommandSwitchOnDifferentImage() {
		MockRenderContext context = new MockRenderContext();
		
		Image test = new Image( new Texture() {
			@Override
			public int getID() {
				return 1;
			}
		} , 10 , 1, 1, 1, 1, 1 );
		
		Image test2 = new Image( new Texture() {
			@Override
			public int getID() {
				return 2;
			}
		} , 10 , 1, 1, 1, 1, 1 );
		
		context.addImage( "test" , test );
		context.addImage( "test2" , test2 );
		
		Renderer renderer = new Renderer( context );
		RenderObject obj = new RenderObject( null , null , 0 , 0 , "test" , 0 , 0 );
		RenderObject obj2 = new RenderObject( null , null , 0 , 0 , "test2" , 0 , 0 );
		
		renderer.add( obj );
		renderer.add( obj2 );
		
		renderer.render();
		
		assertEquals( test.texture , context.commands.get( 1 ).texture );
		assertEquals( test2.texture , context.commands.get( 2 ).texture );
	}
	
	@Test
	public void ObjectsExceedSizeCreatesNewCommand() {
		MockRenderContext context = new MockRenderContext();
		Renderer renderer = new Renderer( context );
		
		for( int i = 0; i < RenderCommand.MAX_OBJECTS + 1; i++ )
			renderer.add( new RenderObject( null , null , 0 , 0 , 0 ) );
		
		renderer.render();
		
		assertEquals( 2 , context.commands.size() );
	}
	
	@Test
	public void ObjectsChildrenAreAddedToRender() {
		RenderObject parent = new RenderObject( new Vector2( 100 , -100 ) , null , 10 , 10 , 0 );
		RenderObject child = new RenderObject( new Vector2( -5 , 10 ) , null , 10 , 10 , 0 );
		
		parent.addChild( child );
		
		MockRenderContext context = new MockRenderContext();
		Renderer renderer = new Renderer( context );
		
		renderer.add( parent );
		renderer.render();
		
		assertEquals( 2 , context.commands.get( 0 ).getObjects().size() );
	}
	
	@Test
	public void ChildrenAddedBeforeObject() {
		RenderObject parent = new RenderObject( new Vector2( 100 , -100 ) , null , 10 , 10 , 0 );
		RenderObject child = new RenderObject( new Vector2( -5 , 10 ) , null , 10 , 10 , 0 );
		
		parent.addChild( child );
		
		MockRenderContext context = new MockRenderContext();
		Renderer renderer = new Renderer( context );
		
		renderer.add( parent );
		renderer.render();
		
		child.equals( context.commands.get( 0 ).getObjects().get( 0 ) );
		assertEquals( child , context.commands.get( 0 ).getObjects().get( 0 ) );
		assertEquals( parent , context.commands.get( 0 ).getObjects().get( 1 ) );
	}
	
	@Test
	public void TileMapRendererInFront() {
		MockRenderContext c = new MockRenderContext();
		Renderer renderer = new Renderer( c );
		
		RenderObject tilemap = new RenderObject( new Vector2( 0 , 0 ) , null , 0 , 0 , 3 );
		tilemap.addChild( new RenderObject( new Vector2( 0 , 0 ) , null , 0 , 0 , 0 ) );
		tilemap.addChild( new RenderObject( new Vector2( 0 , 0 ) , null , 0 , 0 , 0 ) );
		tilemap.addChild( new RenderObject( new Vector2( 0 , 0 ) , null , 0 , 0 , 0 ) );
		
		RenderObject box = new RenderObject( new Vector2( 0 , 0 ) , null , 0 , 0 , 5 );
		
		renderer.add( tilemap );
		renderer.add( box );
		
		renderer.render();
		
		assertEquals( 1 , c.commands.size() );
		assertEquals( tilemap , c.commands.get( 0 ).getObjects().get( 3 ) );
		assertEquals( box , c.commands.get( 0 ).getObjects().get( 4 ) );
	}
	
	@Test
	public void AnimationsGetUpdated() {
		Renderer renderer = new Renderer( new MockRenderContext() );
		
		RenderObject obj = new RenderObject( null , null , 0 , 0 , 0 );
		MockAnimation anim = new MockAnimation( obj , 100.0f );
		obj.addAnimation( anim );
		
		renderer.add( obj );
		
		renderer.render();
		renderer.render();
		
		assertNotEquals( 0.0f , anim.delta , 0.01f );
	}
	
	@Test
	public void AnimationsGetAdded() {
		Renderer renderer = new Renderer( new MockRenderContext() );
		
		RenderObject obj = new RenderObject( null , null , 0 , 0 , 0 );
		MockAnimation anim = new MockAnimation( obj , 100.0f );
		obj.addAnimation( anim );
		
		renderer.add( obj );
		
		renderer.render();
		
		assertTrue( obj.animations.contains( anim ) );
	}
	
	
	
}

class MockAnimation extends Animation {

	public MockAnimation( RenderObject source , float length ) {
		super( source , length );
	}

	public float delta = 0.0f;
	
	@Override
	public void update( float delta ) {
		this.delta += delta;
	}

	@Override
	public boolean over() {
		// TODO Auto-generated method stub
		return false;
	}
	
}