/**
 * 
 */
package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.renderer.IOSRenderContext;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.resources.FilePlatform;
import bracketunbracket.theengine.resources.IOSFilePlatform;
import bracketunbracket.theengine.sound.AudioContext;
import bracketunbracket.theengine.sound.IOSAudioContext;

/**
 * @author michaeleldred
 *
 */
public class IOSPlatform implements Platform {
	
	private IOSRenderContext renderContext = new IOSRenderContext();
	private IOSAudioContext audioContext = new IOSAudioContext();
	private IOSFilePlatform filePlatform = new IOSFilePlatform();

	@Override
	public RenderContext getRenderContext() {
		return renderContext;
	}

	@Override
	public AudioContext getAudioContext() {
		return audioContext;
	}

	@Override
	public FilePlatform getFilePlatform() {
		return filePlatform;
	}

}
