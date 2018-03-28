/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.math.Rectangle;

/**
 * @author Michael Eldred
 */
public class Collision {
	public final CollisionComponent other;
	public final Rectangle intersection;
	
	public Collision( CollisionComponent other , Rectangle intersection ) {
		this.other = other;
		this.intersection = intersection;
	}
}
