/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.input;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.lwjgl.BufferUtils;

import bracketunbracket.theengine.event.EventManager;
import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.GameWindow;

/**
 * @author Michael Eldred
 */
public class LWJGLInputManager {
	
	private DoubleBuffer xPos;
	private DoubleBuffer yPos;
	
	private long window = 0;
	
	boolean pressed = false;
	private EventManager manager;
	private GameWindow gameWin;
	boolean last[] = new boolean[ 5 ];
	private boolean focus = true;
	
	public LWJGLInputManager( long window , EventManager manager , GameWindow gameWin ) {
		this.window = window;
		xPos = BufferUtils.createDoubleBuffer( 1 );
		yPos = BufferUtils.createDoubleBuffer( 1 );
		this.manager = manager;
		this.gameWin = gameWin;
	}
	
	public void update() {
		// Window events
		int winFocus = glfwGetWindowAttrib( window , GLFW_FOCUSED );
		
		if( winFocus == GLFW_FALSE && focus ) {
			manager.sendEvent( new WindowEvent( WindowEvent.WINDOW_UNFOCUSED ) );
			focus = false;
			
		} else if ( winFocus == GLFW_TRUE && !focus ) {
			manager.sendEvent( new WindowEvent( WindowEvent.WINDOW_FOCUSED ) );
			focus = true;
		}
		
		if( glfwWindowShouldClose( window ) == GLFW_TRUE ) {
			manager.sendEvent( new WindowEvent( WindowEvent.WINDOW_CLOSE ) );
		}
		
		// Get Mouse input
		glfwGetCursorPos( window , xPos , yPos );
		
		double x = xPos.get( 0 ) - gameWin.getWidth() / 2.0f;
		double y = yPos.get( 0 ) - gameWin.getHeight() / 2.0f;
		
		x /= gameWin.getWidth() / 2.0f;
		y /= gameWin.getHeight() / 2.0f;
		
		Vector2 v = gameWin.getScaledDimensions();
		x *= ( v.x / 2.0f );
		y *= ( v.y / 2.0f );
		
		int state = glfwGetMouseButton( window , GLFW_MOUSE_BUTTON_LEFT );
		
		if( state == GLFW_PRESS )
			manager.sendEvent( new PointerEvent( PointerEvent.LEFT , x , y , true ) );
		else
			manager.sendEvent( new PointerEvent( PointerEvent.LEFT , x , y , false ) );
		
		state = glfwGetMouseButton( window , GLFW_MOUSE_BUTTON_RIGHT );
		
		if( state == GLFW_PRESS )
			manager.sendEvent( new PointerEvent( PointerEvent.RIGHT , x , y , true ) );
		
	}
}
