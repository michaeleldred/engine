package bracketunbracket.theengine.platform;

import android.app.Activity;
import bracketunbracket.theengine.renderer.AndroidRenderContext;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.resources.FilePlatform;
import bracketunbracket.theengine.sound.AndroidAudioContext;
import bracketunbracket.theengine.sound.AudioContext;

/**
 * @author Michael
 */
public class AndroidPlatform implements Platform {

	private final AndroidRenderContext context;
	private final AndroidAudioContext audioContext;
	
	public AndroidPlatform( Activity activity ) {
		this.context = new AndroidRenderContext( activity );
		this.audioContext = new AndroidAudioContext( activity.getAssets() );
	}
	
	/**
	 * @see bracketunbracket.theengine.platform.Platform#getRenderContext()
	 */
	@Override
	public RenderContext getRenderContext() {
		return context;
	}

	/**
	 * @see bracketunbracket.theengine.platform.Platform#getAudioContext()
	 */
	@Override
	public AudioContext getAudioContext() {
		return audioContext;
	}

	@Override
	public FilePlatform getFilePlatform() {
		return null;
	}

}
