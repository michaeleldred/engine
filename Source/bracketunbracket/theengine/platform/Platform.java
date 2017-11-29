package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.platform.ads.AdLoader;

/**
 * @author Michael Eldred
 */
public abstract class Platform {
	
	protected static Platform platform = null;
	
	public abstract AdLoader getAdLoader();
	public static Platform getPlatform() {
		return platform;
	}
}
