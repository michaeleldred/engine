/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;
import bracketunbracket.theengine.event.Event;

/**
 * @author Michael Eldred
 */
public class TimedComponent extends Component {
	
	/**
	 * The event to send when the timer expires
	 */
	public final Event event;
	
	/**
	 * The amount of ticks to send before the event
	 */
	public final int length;
	public int current = 0;
	
	public boolean global = false;
	
	/**
	 * 
	 * @param length The amount of ticks before the event is triggered
	 * @param event  The event that is sent after {@code length} ticks.
	 */
	public TimedComponent( int length , Event event ) {
		this( length , event , false );
	}
	
	/**
	 * 
	 * @param length The amount of ticks before the event is triggered
	 * @param event  The event that is sent after {@code length} ticks.
	 */
	public TimedComponent( int length , Event event , boolean global ) {
		this.length = length;
		this.event = event;
		this.global = global;
	}

}
