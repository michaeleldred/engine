package bracketunbracket.theengine.renderer;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael
 */
public class AnimationTest {
	
	@Test
	public void LinearTweenTest() {
		LinearTween tweener = new LinearTween();
		
		assertEquals( 10 , tweener.getValue( 0 , 100 , 10 , 100 ) , 0.001f );
	}
	
	@Test
	public void LinearTweenLoopTest() {
		LinearTween tweener = new LinearTween( );
		
		assertEquals( 10 , tweener.getValue( 0 , 100 , 310 , 100 ) , 0.001f );
	}
	
	@Test
	public void ScaleAnimationTest() {
	}
}

abstract class TestAnimation {
	/**
	 * The way the data is going to be changed
	 */
	private Tween tweener;
	public int currentTick = 0;
	public final int length = 0;
	
	public TestAnimation() {
		
	}
	
	
	public abstract void tick( int ticks );
	
}