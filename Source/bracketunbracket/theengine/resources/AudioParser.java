package bracketunbracket.theengine.resources;

import java.util.HashMap;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.sound.AudioContext;
import bracketunbracket.theengine.sound.Music;
import bracketunbracket.theengine.sound.Sound;

/**
 * @author Michael
 */
public class AudioParser extends ResourceParser {

	private final AudioContext audioContext;
	
	private class MusicResource extends Resource implements EventListener {
		public MusicResource(HashMap<String, String> values, ResourceManager resourceManager ) {
			super( values , resourceManager );
			Music music = audioContext.newMusicTrack( values.get( "filename" ) , this );
			
			audioContext.add( values.get( "name" ) , music );
		}

		@Override
		public void receive(Event event) {
			finished();
		}
	}
	
	private class SoundResource extends Resource implements EventListener {
		
		public SoundResource(HashMap<String, String> values , ResourceManager resourceManager ) {
			super( values , resourceManager );
			
			Sound sound = audioContext.newSound( values.get( "filename" ) , this );
			audioContext.add( values.get( "name" ) , sound );
			
			sound.addEventListener( this );
		}

		@Override
		public void receive(Event event) {
			finished();
		}
		
	}
	
	public AudioParser( AudioContext context ) {
		super( "sound" , "music" );
		this.audioContext = context;
	}
	
	@Override
	public Resource load(HashMap<String, String> values) {
		if( values.get( "type" ).equalsIgnoreCase( "music" ) ) {
			return new MusicResource( values , resourceManager ); 
		} else if( values.get( "type" ).equalsIgnoreCase( "sound" ) ) {
			return new SoundResource( values , resourceManager );
		}
		return null;
	}

}
