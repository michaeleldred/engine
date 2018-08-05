/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael Eldred
 *
 */
public class BuildAnimationTest {
	
	@Test
	public void AnimationIncreasesLength() {
		BuildAnimation anim = new BuildAnimation( null );
		
		anim.add( new MockAnimation( 10 ) );
		
		assertEquals( 10 , anim.length );
		
		anim.add( new MockAnimation( 30 ) );
		
		assertEquals( 40 , anim.length );
	}
	
	@Test
	public void GetCorrectAnimationForTime() {
		BuildAnimation anim = new BuildAnimation( null );
		
		MockAnimation first = new MockAnimation( 20 );
		MockAnimation second = new MockAnimation( 20 );
		
		anim.add( first );
		anim.add( second );
		
		assertEquals( first , anim.getAnimationForFrame( 11 ) );
		assertEquals( second , anim.getAnimationForFrame( 31 ) );
	}
	
	@Test
	public void CorrectAnimationIsUpdated() {
		BuildAnimation anim = new BuildAnimation( null );
		
		MockAnimation first = new MockAnimation( 20 );
		MockAnimation second = new MockAnimation( 20 );
		
		anim.add( first );
		anim.add( second );
		anim.update( 13 );
		
		assertTrue( first.wasUpdated );
		
		anim.update( 13 );
		assertTrue( second.wasUpdated );
	}
}
