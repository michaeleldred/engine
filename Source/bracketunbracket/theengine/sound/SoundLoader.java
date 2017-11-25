/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import java.util.Map;

import bracketunbracket.theengine.resources.ResourceLoader;
import bracketunbracket.theengine.resources.ResourceManager;

/**
 * @author Michael Eldred
 */
public class SoundLoader implements ResourceLoader {

	private AudioContext context;
	private AudioEngine engine;

	public SoundLoader( AudioEngine engine ) {
		this.engine = engine;
		this.context = engine.audioContext;
	}
	
	public SoundLoader( AudioContext context ) {
		this.context = context;
	}
	
	@Override
	public void create( ResourceManager manager, Map<String, String> vals ) throws Exception {
		if( vals.get( "type" ).equalsIgnoreCase( "sound" ) ) {
			//System.out.println( "File: " + vals.get( "filename" ) );
			
			Sound sound = context.newSound( vals.get( "filename" ) );
			
			context.add( vals.get( "name" ) , sound );
		} else if( vals.get( "type" ).equalsIgnoreCase( "soundevent" ) ) {
			engine.runner.load( vals.get( "name" ), vals.get( "action" ) );
		} else if( vals.get( "type" ).equalsIgnoreCase( "music" ) ) {
			Music track = context.newMusicTrack( vals.get( "filename" ) );
			context.add( vals.get( "name" ) , track );
		}
		
	}

}
