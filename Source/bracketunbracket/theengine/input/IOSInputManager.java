/**
 * 
 */
package bracketunbracket.theengine.input;

import bracketunbracket.theengine.event.EventManager;
import bracketunbracket.theengine.input.PointerEvent.EventType;
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
	
	public void handleTouch( int button , float x , float y , boolean isDown , EventType type  ) {
		
		x = x - gameWindow.getWidth() / 2.0f;
		y = y - gameWindow.getHeight() / 2.0f;

		x /= gameWindow.getWidth() / 2.0f;
		y /= gameWindow.getHeight() / 2.0f;
		
		//System.out.println( "Translated To: " + x + " , " + y );
		
		x *= gameWindow.getScaledDimensions().x / 2.0f;
		y *= gameWindow.getScaledDimensions().y / 2.0f;
		
		//System.out.println( "Final translated To: " + x + " , " + y );
		eventManager.sendEvent( new PointerEvent(button, x, y, isDown , type ));
	}
}
