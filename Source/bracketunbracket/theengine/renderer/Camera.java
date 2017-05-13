/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael Eldred
 */
public class Camera {
	public final Vector2 offset = new Vector2( 0.0f , 0.0f );
	private int shakeTick = 0;
	
	public void update() {
		if( shakeTick > 0 ) {
			shakeTick--;
			offset.x = (int)( (float)Math.random() * (float)( shakeTick * 2 ) ) - (float)shakeTick;
			offset.y = (int)( (float)Math.random() * (float)( shakeTick * 2 ) ) - (float)shakeTick;
		} else {
			offset.x = 0;
			offset.y = 0;
		}
	}
	
	public void shake( int ticks ) {
		shakeTick = ticks;
	}
}
