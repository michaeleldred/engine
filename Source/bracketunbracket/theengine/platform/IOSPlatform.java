/**
 * 
 */
package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.renderer.IOSRenderContext;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.resources.FilePlatform;
import bracketunbracket.theengine.resources.IOSFilePlatform;
import bracketunbracket.theengine.sound.AudioContext;

/**
 * @author michaeleldred
 *
 */
public class IOSPlatform implements Platform {
	
	private IOSRenderContext renderContext = new IOSRenderContext();
	private IOSFilePlatform filePlatform = new IOSFilePlatform();

	@Override
	public RenderContext getRenderContext() {
		return renderContext;
	}

	@Override
	public AudioContext getAudioContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FilePlatform getFilePlatform() {
		return filePlatform;
	}

}
