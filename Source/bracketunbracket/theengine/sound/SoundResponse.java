package bracketunbracket.theengine.sound;

/**
 * @author Michael
 */
public class SoundResponse {
	
	protected String sound = "";
	protected float pitch = 1.0f;
	protected SoundResponse child;
	
	/*
	 * Creates a response with no default sound, this should only be used for 
	 * extending classes.
	 */
	protected SoundResponse() {
		this( "" );
	}
	
	public SoundResponse( SoundResponse child ) {
		this.child = child;
	}
	
	public SoundResponse( String sound ) {
		this.sound = sound;
	}
	
	public String getSound() {
		return sound;
	}
	
	/**
	 * 
	 * @return
	 */
	public float getPitch() {
		if( child == null )
			return pitch;
		else
			return child.getPitch() * pitch; 
	}
}
