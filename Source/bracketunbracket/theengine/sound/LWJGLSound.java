/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

/**
 * @author Michael Eldred
 */
public class LWJGLSound implements Sound {
	public final int buffer;
	
	public LWJGLSound( int buffer ) {
		this.buffer = buffer;
	}
}
