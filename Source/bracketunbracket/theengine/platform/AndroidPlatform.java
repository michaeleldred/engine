package bracketunbracket.theengine.platform;

import android.app.Activity;
import android.widget.RelativeLayout;
import bracketunbracket.theengine.platform.ads.AdLoader;
import bracketunbracket.theengine.platform.ads.AndroidAdLoader;

/**
 * @author Michael Eldred
 */
public class AndroidPlatform extends Platform {

	private final AdLoader adLoader;
	
	public AndroidPlatform( Activity activity , RelativeLayout parent  ) {
		Platform.platform = this;
		this.adLoader = new AndroidAdLoader( activity , parent );
	}
	
	@Override
	public AdLoader getAdLoader() {
		return adLoader;
	}

}
