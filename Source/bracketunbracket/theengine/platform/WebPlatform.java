package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.renderer.WebRenderContext;
import bracketunbracket.theengine.resources.FilePlatform;
import bracketunbracket.theengine.resources.WebFilePlatform;
import bracketunbracket.theengine.sound.AudioContext;
import bracketunbracket.theengine.sound.WebAudioContext;

/**
 * @author Michael
 */
public class WebPlatform implements Platform {
	
	private final WebRenderContext renderContext;

	public WebPlatform( float width , float height ) {
		this.renderContext = new WebRenderContext( width , height );
	}
	
	@Override
	public RenderContext getRenderContext() {
		return renderContext;
	}

	@Override
	public AudioContext getAudioContext() {
		return new WebAudioContext();
	}

	@Override
	public FilePlatform getFilePlatform() {
		return new WebFilePlatform();
	}
	
}
