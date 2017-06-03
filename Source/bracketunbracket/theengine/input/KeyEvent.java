package bracketunbracket.theengine.input;

import static org.lwjgl.glfw.GLFW.*;

import bracketunbracket.theengine.event.Event;

/**
 * @author Michael Eldred
 */
public class KeyEvent extends Event {
	public static final int LEFT = GLFW_KEY_LEFT;
	public static final int PRESS = GLFW_PRESS;
	public static final int RIGHT = GLFW_KEY_RIGHT;
	public static final int UP = GLFW_KEY_UP;
	public static final int DOWN = GLFW_KEY_DOWN;
	public final int key;
	public final int action;
	
	public KeyEvent( int key , int action ) {
		this.key = key;
		this.action = action;
	}
}
