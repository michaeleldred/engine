package bracketunbracket.theengine.resources;

import java.util.HashMap;

/**
 * @author Michael
 */
public abstract class Resource {
	
	protected final HashMap< String , String > values;
	
	public boolean isLoaded = false;
	
	private ResourceManager resourceManager;
	
	public Resource( HashMap< String , String > values ) {
		this.values = values;
	}
	
	/**
	 * Notifies the Resource and ResourceManager that this object has finished
	 * loading. It must be called by every implementing class.
	 */
	public void finished() {
		isLoaded = true;
		resourceManager.loaded( this );
	}
	
	public void setManager( ResourceManager manager ) {
		this.resourceManager = manager;
	}
}
