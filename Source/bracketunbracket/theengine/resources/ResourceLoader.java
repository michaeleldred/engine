/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.resources;

import java.util.Map;

/**
 * @author Michael Eldred
 */
public interface ResourceLoader {
	public void create( ResourceManager manager , Map<String,String> vals ) throws Exception;
}
