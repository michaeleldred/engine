/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.entity.Component;
import bracketunbracket.theengine.math.Rectangle;

/**
 * @author Michael Eldred
 */
public class CollisionComponent extends Component {
	public String tag;
	
	public Rectangle rect = new Rectangle();
	
	/**
	 * Each CollisionComponent will hold a list of the collisions that it was
	 * involved since the collisions were last checked.
	 */
	public List<Collision> collisions = new ArrayList<Collision>();
	
	public CollisionComponent( String tag , float width, float height ) {
		this.tag = tag;
		rect.setBounds( 0 , 0 , width , height );
	}
	
	public float getHeight() {
		return rect.h;
	}
	
	public float getWidth() {
		return rect.w;
	}
}
