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
public class GameWindowTest {
	
	@Test
	public void GetScaledDimensions() {
		GameWindow window = new GameWindow( 1366 , 768 , 800 , 600 );
		
		Vector2 res = window.getScaledDimensions();
		
		assertEquals( 1366f/768f , res.x / res.y , 0.00001f );
	}
	
	@Test
	public void GetResizedScaledDimensions() {
		GameWindow window = new GameWindow( 12 , 12 , 800 , 600 );
		window.resize( 1366 , 768 );
		
		Vector2 res = window.getScaledDimensions();
		
		assertEquals( 1366f/768f , res.x / res.y , 0.00001f );
	}
}
