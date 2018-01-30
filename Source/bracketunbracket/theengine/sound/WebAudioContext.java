package bracketunbracket.theengine.sound;

/**
 * @author Michael
 */
public class WebAudioContext extends AudioContext {

	private boolean isMute = false;
	
	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#play(java.lang.String)
	 */
	@Override
	public void play(String name) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#playMusic(java.lang.String)
	 */
	@Override
	public void playMusic(String name) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#setPause(boolean)
	 */
	@Override
	public void setPause(boolean pause) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#newSound(java.lang.String)
	 */
	@Override
	public Sound newSound(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#newMusicTrack(java.lang.String)
	 */
	@Override
	public Music newMusicTrack(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#mute()
	 */
	@Override
	public void mute() {
		isMute = true;
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#unmute()
	 */
	@Override
	public void unmute() {
		isMute = false;
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#isMute()
	 */
	@Override
	public boolean isMute() {
		return isMute;
	}

}