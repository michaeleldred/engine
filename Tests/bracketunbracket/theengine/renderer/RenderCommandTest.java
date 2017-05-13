/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael Eldred
 */
public class RenderCommandTest {
	@Test
	public void RenderObjectCopiedIntoList(){
		RenderObject obj = new RenderObject( null , null , 10 , 10 , 1 );
		RenderCommand command = new RenderCommand();
		
		command.add( obj );
		
		assertTrue( obj != command.getObjects().get( 0 ) );
	}
	
	@Test
	public void ObjectsAddedWithAbsolutePosition(){
		RenderObject parent = new RenderObject( new Vector2( 1.0f , 2.0f ) , null , 10 , 10 , 1 );
		RenderObject obj = new RenderObject( new Vector2( 3.0f , 4.0f ) , null , 10 , 10 , 1 );
		RenderCommand command = new RenderCommand();
		parent.addChild( obj );
		
		command.add( obj );
		
		assertEquals( new Vector2( 4.0f , 6.0f ) , command.getObjects().get( 0 ).position );
	}
}
