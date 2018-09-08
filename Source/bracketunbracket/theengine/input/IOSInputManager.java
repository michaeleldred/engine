/**
 * 
 */
package bracketunbracket.theengine.input;

import bracketunbracket.theengine.event.EventManager;
import bracketunbracket.theengine.renderer.GameWindow;

/**
 * @author michaeleldred
 *
 */
public class IOSInputManager {
	
	private final EventManager eventManager;
	private final GameWindow gameWindow;
	
	public IOSInputManager( EventManager eventManger , GameWindow window ) {
		this.eventManager = eventManger;
		this.gameWindow = window;
	}
	
	public void handleTouch( int button , float x , float y , boolean isDown ) {
		System.out.println( "Touch at: " + x + " , " + y );
		
		x = x - 568.0f / 2.0f;
		y = y - 320.0f / 2.0f;

		x /= 568.0f / 2.0f;
		y /= 320.0f / 2.0f;
		
		System.out.println( "Translated To: " + x + " , " + y );
		
		x *= gameWindow.getScaledDimensions().x / 2.0f;
		y *= gameWindow.getScaledDimensions().x / 2.0f;
		eventManager.sendEvent( new PointerEvent(button, x, y, isDown));
	}
}
