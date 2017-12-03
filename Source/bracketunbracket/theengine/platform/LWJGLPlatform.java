package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.renderer.LWJGLRenderContext;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.sound.AudioContext;
import bracketunbracket.theengine.sound.LWJGLAudioContext;

/**
 * @author Michael
 */
public class LWJGLPlatform implements Platform {
	
	private final LWJGLRenderContext context;
	private final LWJGLAudioContext audioContext;
	
	public LWJGLPlatform() throws Exception {
		this.context = new LWJGLRenderContext( 1024 , 600 );
		this.audioContext = new LWJGLAudioContext();
	}

	@Override
	public RenderContext getRenderContext() {
		return context;
	}
	
	@Override
	public AudioContext getAudioContext() {
		return audioContext;
	}

}
