/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author michaeleldred
 */
public class AnimationTest {
	@Test
	public void AdvanceFrame() {
		Animation anim = new MockAnimation( 6 );
		anim.update( 4 );
		
		assertEquals( 4 , anim.currentTick );
	}
	
	@Test
	public void ResetFrameOnAdvance() {
		Animation anim = new MockAnimation( 3 );
		anim.update( 4 );
		
		assertEquals( 1 , anim.currentTick );
	}
	
	@Test
	public void ResetFrameOnMultipleLengthAdvance() {
		Animation anim = new MockAnimation( 3 );
		anim.update( 1 );
		
		assertEquals( 1 , anim.currentTick );
	}
	
	@Test
	public void SubClassGetsEvent() {
		MockAnimation anim = new MockAnimation( 12 );
		anim.update( 3 );
		
		assertTrue( anim.wasUpdated );
	}
	
	@Test
	public void AddTweenerToAnimation() {
		
		Animation anim = new MockAnimation( 12 , Tweener.linear );
		
		assertEquals( Tweener.linear , anim.tweener );
	}
}

class MockAnimation extends Animation {

	public boolean wasUpdated = false;
	
	public MockAnimation( int length ) {
		this( length , null );
	}
	
	public MockAnimation( int length , Tweener tweener ) {
		super( null , length , tweener );
	}
	

	@Override
	public void doUpdate() {
		wasUpdated = true;
	}
	
}