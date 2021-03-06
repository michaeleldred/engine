/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.math;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael Eldred
 */
public class RectangleTest {
	@Test
	public void RectangleAreaIsCorrect() {
		Rectangle rect = new Rectangle( 3 , 5 );
		assertEquals( 15.0f , rect.area() , 0.0001f );
		
		rect = new Rectangle( 3 , -5 );
		assertEquals( 15.0f , rect.area() , 0.0001f );
	}
	
	@Test
	public void RectangleIntersection() {
		Rectangle rect1 = new Rectangle( 0 , 0 , 10 , 10 );
		Rectangle rect2 = new Rectangle( 5 , 5 , 10 , 10 );
		
		assertEquals( new Rectangle( 5 , 5 , 5 , 5 ) , rect1.intersection( rect2 ) );
		
		rect1 = new Rectangle( 0 , 0 , 10 , 10 );
		rect2 = new Rectangle( -10 , -10 , 20 , 20 );
		
		assertEquals( new Rectangle( 0 , 0 , 10 , 10 ) , rect1.intersection( rect2 ) );
	}
	@Test
	public void createRectangleWithCenter() {
		Rectangle rect = new Rectangle( new Vector2( -25 , -35 ) , 50 , 100 );
		assertEquals( -50.0f , rect.x , 0.1f );
		assertEquals( -85.0f , rect.y , 0.1f );
		assertEquals( 50.0f , rect.w , 0.1f );
		assertEquals( 100.0f , rect.h , 0.1f );
	}
}
