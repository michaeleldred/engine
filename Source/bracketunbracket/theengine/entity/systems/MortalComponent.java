/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import bracketunbracket.theengine.entity.Component;

/**
 * @author Michael Eldred
 */
public class MortalComponent extends Component {
	public final String eventName;
	
	public MortalComponent( String eventName ) {
		this.eventName = eventName;
	}
}
