/**
 * 
 */
package bracketunbracket.theengine.sound;

import bracketunbracket.theengine.event.EventListener;

/**
 * @author michaeleldred
 *
 */
public class IOSAudioContext extends AudioContext {

	private boolean mute;
	
	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#play(java.lang.String)
	 */
	@Override
	public void play(String name) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#play(bracketunbracket.theengine.sound.SoundResponse)
	 */
	@Override
	public void play(SoundResponse name) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#playMusic(java.lang.String)
	 */
	@Override
	public void playMusic(String name) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#setPause(boolean)
	 */
	@Override
	public void setPause(boolean pause) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#newSound(java.lang.String, bracketunbracket.theengine.event.EventListener)
	 */
	@Override
	public Sound newSound(String filename, EventListener listener) {
		IOSSound retVal = new IOSSound();
		retVal.addEventListener( listener );
		retVal.loaded();
		
		return retVal;
	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#newMusicTrack(java.lang.String, bracketunbracket.theengine.event.EventListener)
	 */
	@Override
	public Music newMusicTrack(String filename, EventListener listener) {
		IOSMusic retVal = new IOSMusic();
		retVal.addEventListener( listener );
		retVal.loaded();
		
		return retVal;
	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#mute()
	 */
	@Override
	public void mute() {
		mute = true;
	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#unmute()
	 */
	@Override
	public void unmute() {
		mute = false;
	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.sound.AudioContext#isMute()
	 */
	@Override
	public boolean isMute() {
		return mute;
	}

}
