/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.HashMap;
import java.util.Map;

import bracketunbracket.theengine.entity.Component;
import bracketunbracket.theengine.sound.SoundResponse;

/**
 * @author Michael Eldred
 */
public class SoundComponent extends Component {
	public final Map< String , SoundResponse > responses = new HashMap< String , SoundResponse >();
	
	public void addResponse( String event , SoundResponse response ) {
		responses.put( event , response );
	}
	
	public void addResponse( String event , String sound ) {
		addResponse( event , new SoundResponse( sound ) );
	}
}
