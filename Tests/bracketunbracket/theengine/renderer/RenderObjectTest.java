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
public class RenderObjectTest {
	@Test
	public void AbsolutePositionInheritsFromParent() {
		RenderObject parent = new RenderObject( new Vector2( 100 , -100 ) , null , 10 , 10 , 0 );
		RenderObject child = new RenderObject( new Vector2( -5 , 10 ) , null , 10 , 10 , 0 );
		
		parent.addChild( child );
		
		assertEquals( new Vector2( 95 , -90 ) , child.getAbsolutePosition() );
	}
	
	@Test
	public void GetColorFromParent() {
		RenderObject parent = new RenderObject( new Vector2( 100 , -100 ) , new Color( 1.0f , 1.0f , 1.0f , 1.0f ) , 10 , 10 , 0 );
		RenderObject child = new RenderObject( new Vector2( -5 , 10 ) , new Color( 1.0f , 1.0f , 1.0f , 1.0f ) , 10 , 10 , 0 );
		parent.addChild( child );
		
		parent.color.alpha = 0.5f;
		
		assertEquals( new Color( 1.0f , 1.0f , 1.0f , 0.5f ) , child.getColor() );
	}
	
	@Test
	public void ChildIsAssignedParent() {
		RenderObject parent = new RenderObject( new Vector2( 100 , -100 ) , null , 10 , 10 , 0 );
		RenderObject child = new RenderObject( new Vector2( -5 , 10 ) , null , 10 , 10 , 0 );
		
		parent.addChild( child );
		
		assertEquals( parent , child.parent );
	}
	@Test
	public void ClearChildren() {
		RenderObject parent = new RenderObject( new Vector2( 100 , -100 ) , null , 10 , 10 , 0 );
		RenderObject child = new RenderObject( new Vector2( -5 , 10 ) , null , 10 , 10 , 0 );
		
		parent.addChild( child );
		
		parent.clearChildren();
		
		assertEquals( 0 , parent.children.size() );
	}
}
