/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

/**
 * @author Michael Eldred
 */
public class Collision {
	public CollisionComponent other;
	
	public Collision( CollisionComponent other ) {
		this.other = other;
	}
}
