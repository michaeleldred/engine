package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.renderer.WebRenderContext;
import bracketunbracket.theengine.sound.AudioContext;
import bracketunbracket.theengine.sound.WebAudioContext;

/**
 * @author Michael
 */
public class WebPlatform implements Platform {
	
	private final WebRenderContext renderContext;

	public WebPlatform() {
		this.renderContext = new WebRenderContext( 800 , 600 );
	}
	
	@Override
	public RenderContext getRenderContext() {
		return renderContext;
	}

	@Override
	public AudioContext getAudioContext() {
		return new WebAudioContext();
	}
	
}
