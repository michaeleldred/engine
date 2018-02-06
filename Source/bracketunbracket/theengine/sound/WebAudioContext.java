package bracketunbracket.theengine.sound;

import org.teavm.jso.dom.html.HTMLAudioElement;

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
		WebSound sound = (WebSound)sounds.get( name );
		if( sound != null ) {
			((HTMLAudioElement)sound.audioElement.cloneNode( true )).play();
		} else {
			System.out.println( "No Sound for: " + name );
		}
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#playMusic(java.lang.String)
	 */
	@Override
	public void playMusic(String name) {
		WebMusic music = (WebMusic)tracks.get( name );
		music.audioElement.setLoop( true );
		music.audioElement.play();
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
		return new WebSound( filename );
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#newMusicTrack(java.lang.String)
	 */
	@Override
	public Music newMusicTrack(String filename) {
		return new WebMusic( filename );
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
