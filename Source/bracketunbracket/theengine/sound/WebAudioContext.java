package bracketunbracket.theengine.sound;

import java.util.ArrayList;
import java.util.List;

import org.teavm.jso.dom.html.HTMLAudioElement;

import bracketunbracket.theengine.event.EventListener;

/**
 * @author Michael
 */
public class WebAudioContext extends AudioContext {

	private boolean isMute = false;
	
	private List<HTMLAudioElement> elements = new ArrayList<HTMLAudioElement>();
	private org.teavm.jso.dom.events.EventListener<org.teavm.jso.dom.events.Event>
		audioEnded = 
		new org.teavm.jso.dom.events.EventListener<org.teavm.jso.dom.events.Event>() {

		@Override
		public void handleEvent(org.teavm.jso.dom.events.Event event) {
			System.out.println( "TEST" );
			elements.remove( event.getTarget().cast() );
		}
		
	};
	
	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#play(java.lang.String)
	 */
	@Override
	public void play(String name) {
		WebSound sound = (WebSound)sounds.get( name );
		if( sound != null ) {
			HTMLAudioElement audio = ((HTMLAudioElement)sound.audioElement.cloneNode( true ));
			elements.add( audio );
			audio.addEventListener( "ended" , audioEnded );
			if( isMute ) {
				audio.setVolume( 0.0f );
			}
			audio.play();
		} else {
			System.out.println( "No Sound for: " + name );
		}
	}
	

	@Override
	public void play( SoundResponse response ) {
		String name = response.getSound();
		
		WebSound sound = (WebSound)sounds.get( name );
		
		if( sound != null ) {
			HTMLAudioElement audio = ((HTMLAudioElement)sound.audioElement.cloneNode( true ));
			// Set properties
			audio.setPlaybackRate( response.getPitch() );
			elements.add( audio );
			audio.addEventListener( "ended" , audioEnded );
			if( isMute ) {
				audio.setVolume( 0.0f );
			}
			audio.play();
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
		elements.add( music.audioElement );
		music.audioElement.addEventListener( "ended" , audioEnded );
		
		if( isMute ) {
			music.audioElement.setVolume( 0.0f );
		}
		
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
	public Sound newSound(String filename , EventListener listener ) {
		return new WebSound( filename , listener );
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#newMusicTrack(java.lang.String)
	 */
	@Override
	public Music newMusicTrack(String filename , EventListener listener ) {
		return new WebMusic( filename , listener );
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#mute()
	 */
	@Override
	public void mute() {
		isMute = true;
		for( HTMLAudioElement current : elements ) {
			current.setVolume( 0.0f );
		}
		System.out.println( "MUTE" + elements.size() );
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#unmute()
	 */
	@Override
	public void unmute() {
		isMute = false;
		for( HTMLAudioElement current : elements ) {
			current.setVolume( 1.0f );
		}
	}

	/**
	 * @see bracketunbracket.theengine.sound.AudioContext#isMute()
	 */
	@Override
	public boolean isMute() {
		return isMute;
	}

	
}
