/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.input;

import bracketunbracket.theengine.event.Event;

/**
 * @author Michael Eldred
 */
public class PointerEvent extends Event {
	public float x;
	public float y;
	
	public int button = -1;
	
	public boolean isDown = false;
	
	public final static int LEFT = 0;
	public final static int RIGHT = 1;
	
	public enum EventType {
		DOWN,
		MOVE,
		UP,
	}
	
	public final EventType type;
	
	public PointerEvent( int button , double x , double y , boolean isDown ) {
		this.button = button;
		this.x = (float)x;
		this.y = (float)y;
		this.isDown = isDown;
		this.type = EventType.DOWN;
	}
	
	public PointerEvent( int button , double x , double y , boolean isDown , EventType type ) {
		this.button = button;
		this.x = (float)x;
		this.y = (float)y;
		this.isDown = isDown;
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "PointerEvent: down=" + isDown + " button=" + button + " at ( " + x + " , " + y + " )";
	}
	
}
