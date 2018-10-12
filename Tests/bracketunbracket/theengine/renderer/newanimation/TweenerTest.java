/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author michaeleldred
 *
 */
public class TweenerTest {
	@Test
	public void TestValuesFadeToNegative() {
		Tweener tween = Tweener.easeOutQuad;
		
		float val = tween.getValue( 0.45f , 0.0f, 120, 120 );
		
		assertEquals( 0.0f , val , 0.0000001f );
	}
}
