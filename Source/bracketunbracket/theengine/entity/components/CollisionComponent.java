/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.entity.Component;

/**
 * @author Michael Eldred
 */
public class CollisionComponent extends Component {
	public float height;
	public float width;
	
	public String tag;
	
	/**
	 * Each CollisionComponent will hold a list of the collisions that it was
	 * involved since the collisions were last checked.
	 */
	public List<Collision> collisions = new ArrayList<Collision>();
	
	public CollisionComponent( String tag , float width, float height ) {
		this.tag = tag;
		this.width = width;
		this.height = height;
	}
}
