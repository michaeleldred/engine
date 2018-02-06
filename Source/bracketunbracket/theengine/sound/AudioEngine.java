package bracketunbracket.theengine.sound;

import java.util.HashMap;
import java.util.Map;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.input.WindowEvent;

public class AudioEngine implements EventListener {

	public final AudioContext audioContext;
	public final Map< String , String > scripts = new HashMap< String , String >();
	
	public AudioEngine( AudioContext c ) {
		this.audioContext = c;
	}

	public void play( String sound_effect ) {
		audioContext.play( sound_effect );
	}
	
	public void playMusic( String soundtrack ) {
		audioContext.playMusic( soundtrack );
	}
	
	public void setMute( boolean mute ) {
		if( mute ) {
			audioContext.mute();
		} else {
			audioContext.unmute();
		}
		
	}

	@Override
	public void receive(Event event) {
		if( event instanceof WindowEvent ) {
			if( ((WindowEvent)event).windowState == WindowEvent.WINDOW_FOCUSED ) {
				audioContext.setPause( false );
			} else if( ((WindowEvent)event).windowState == WindowEvent.WINDOW_UNFOCUSED ) {
				audioContext.setPause( true );
			}
		}
	}

}
