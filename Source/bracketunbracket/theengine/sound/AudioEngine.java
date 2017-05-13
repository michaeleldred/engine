package bracketunbracket.theengine.sound;

import java.util.HashMap;
import java.util.Map;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.input.WindowEvent;

public class AudioEngine implements EventListener {

	public final AudioContext audioContext;
	public final Map< String , String > scripts = new HashMap< String , String >();
	public final ScriptRunner runner;
	
	public AudioEngine( AudioContext c , ScriptRunner runner ) {
		this.audioContext = c;
		this.runner = runner;
	}

	public void play( String string ) {
		audioContext.play( string );
	}
	
	public void playMusic( String string ) {
		audioContext.playMusic( string );
	}
	
	public void doScript( String name ) {
		runner.run( name );
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
