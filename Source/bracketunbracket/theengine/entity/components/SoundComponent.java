/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.HashMap;
import java.util.Map;

import bracketunbracket.theengine.entity.Component;

/**
 * @author Michael Eldred
 */
public class SoundComponent extends Component {
	public final Map< String , String > responses = new HashMap<String,String>();
	
	public void addResponse( String event , String sound ) {
		responses.put( event , sound );
	}
}
