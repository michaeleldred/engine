package bracketunbracket.theengine.sound;

/**
 * @author Michael
 */
public class RandomSound extends SoundResponse {

	/**
	 * The list of sounds to choose from
	 */
	private final String[] sounds;
	
	/**
	 * Create an object that randomly picks from a list of sounds to give play.
	 * 
	 * @param sounds A list of resource names of the sounds to pick from
	 */
	public RandomSound(String... sounds) {
		this.sounds = sounds;
	}

	/**
	 * Get a random sound to be played by an audio context.
	 * 
	 * @see bracketunbracket.theengine.sound.SoundResponse#getSound()
	 */
	@Override
	public String getSound() {
		// Get a random number based on the length of the sound array
		int randomNum = (int)(Math.random() * (double)sounds.length);
		return sounds[ randomNum ];
	}
}
