/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.event;

/**
 * @author Michael Eldred
 */
public class GameEvent extends Event {
	
	public final String name;
	
	public final Object data;
	
	public GameEvent( String name ) {
		this( name , null );
	}
	
	public GameEvent( String name , Object data ) {
		this.name = name;
		this.data = data;
	}
}
