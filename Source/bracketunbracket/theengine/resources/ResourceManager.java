/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Eldred
 */
public class ResourceManager {
	
	private final Map< String , Resource > resources = new HashMap< String , Resource >();
	
	public void add( String name , Resource r ) {
		resources.put( name , r );
	}
	
	public Resource getResource( String name ) {
		return resources.get( name );
	}
}
