package bracketunbracket.theengine.platform;

import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.sound.AudioContext;

public interface Platform {
	public RenderContext getRenderContext();
	public AudioContext getAudioContext();
}
