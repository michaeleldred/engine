package bracketunbracket.theengine.sound;

import java.util.HashMap;
import java.util.Map;

public abstract class AudioContext {
	
	protected final Map< String , Sound > sounds = new HashMap< String , Sound >();
	protected final Map< String , Music > tracks = new HashMap< String , Music >();
	
	public abstract void play( String name );
	public abstract void playMusic( String name );
	public abstract void setPause( boolean pause );
	public abstract Sound newSound( String filename );
	public abstract Music newMusicTrack( String filename );
	public abstract void mute();
	public abstract void unmute();
	public abstract boolean isMute();

	public void add( String name, Sound sound ) {
		sounds.put( name , sound );
	}
	
	public void add( String name, Music sound ) {
		tracks.put( name , sound );
	}
	
	public Sound get( String name ) {
		return sounds.get( name );
	}
}
