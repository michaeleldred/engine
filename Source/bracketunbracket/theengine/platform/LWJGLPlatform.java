package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.renderer.LWJGLRenderContext;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.resources.FilePlatform;
import bracketunbracket.theengine.resources.LWJGLFilePlatform;
import bracketunbracket.theengine.sound.AudioContext;
import bracketunbracket.theengine.sound.LWJGLAudioContext;

/**
 * @author Michael
 */
public class LWJGLPlatform implements Platform {
	
	private final LWJGLRenderContext context;
	private final LWJGLAudioContext audioContext;
	private final FilePlatform platform;
	
	public LWJGLPlatform( float width , float height ) throws Exception {
		this.context = new LWJGLRenderContext( width , height );
		this.audioContext = new LWJGLAudioContext();
		this.platform = new LWJGLFilePlatform();
	}

	@Override
	public RenderContext getRenderContext() {
		return context;
	}
	
	@Override
	public AudioContext getAudioContext() {
		return audioContext;
	}

	@Override
	public FilePlatform getFilePlatform() {
		return platform;
	}

}
