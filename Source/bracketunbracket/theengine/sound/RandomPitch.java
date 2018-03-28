package bracketunbracket.theengine.sound;

/**
 * Generates a sound with a random pitch. 
 * 
 * @author Michael Eldred
 */
public class RandomPitch extends SoundResponse {
	
	/**
	 * The maximum value to generate when create a random number for the pitch
	 */
	private float upperBounds;
	
	/**
	 * The minimum value to generate when create a random number for the pitch
	 */
	private float lowerBounds;
	
	/**
	 * Creates a new Random pitch response that specifies a range to generate
	 * the pitches from, but does not generate a sound string. This constructor 
	 * relies on a child to create the actual name of the sound. 
	 * 
	 * @param child       The child response which is going to generate the
	 *                    sound string.
	 * @param upperBounds The maximum value to generate when create a random 
	 *                    number for the pitch.
	 * @param lowerBounds The minimum value to generate when create a random 
	 *                    number for the pitch.
	 */
	public RandomPitch( SoundResponse child , float lowerBounds , float upperBounds ) {
		super( child );
		this.upperBounds = upperBounds;
		this.lowerBounds = lowerBounds;
	}
	
	/**
	 * Creates a new Random pitch response that specifies a range to generate
	 * the pitches from.
	 * 
	 * @param sound       The name of the string sound
	 * @param upperBounds The maximum value to generate when create a random 
	 *                    number for the pitch.
	 * @param lowerBounds The minimum value to generate when create a random 
	 *                    number for the pitch.
	 */
	public RandomPitch( String sound , float lowerBounds , float upperBounds ) {
		super( sound );
		this.upperBounds = upperBounds;
		this.lowerBounds = lowerBounds;
	}
	
	/**
	 * Generates a random float value to set the pitch to between the upper and
	 * lower bounds specified in the constructor.
	 * 
	 * @return A random value to be passed to the audio engine to modiy the
	 *         pitch. 
	 * @see bracketunbracket.theengine.sound.SoundResponse#getPitch()
	 */
	@Override
	public float getPitch() {
		return lowerBounds + 
				(float)(Math.random() * ( upperBounds - lowerBounds ) );
	}
	
	@Override
	public String getSound() {
		
		// Rely on the child response first to generate a sound
		if( child != null )
			return child.getSound();
		else
			return sound;
	}
}
