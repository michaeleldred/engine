/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.input;

import bracketunbracket.theengine.event.Event;

/**
 * @author Michael Eldred
 */
public class WindowEvent extends Event {
	
	public final static int WINDOW_CLOSE = 1;
	public final static int WINDOW_UNFOCUSED = 2;
	public final static int WINDOW_FOCUSED = 3;
	
	public int windowState;
	
	public WindowEvent( int state ) {
		this.windowState = state;
	}
}
