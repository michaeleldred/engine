package bracketunbracket.theengine.resources;

import java.util.HashMap;

/**
 * @author Michael
 */
public abstract class ResourceParser {
	public final String[] types;
	
	protected ResourceManager resourceManager;
	
	protected ResourceParser( String...types ) {
		this.types = types;
	}
	
	public abstract Resource load( HashMap< String , String > values );
	
	public void setResourceManager( ResourceManager manager ) {
		this.resourceManager = manager;
	}
}
