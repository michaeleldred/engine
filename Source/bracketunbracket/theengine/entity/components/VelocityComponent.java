package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;
import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael
 */
public class VelocityComponent extends Component {
	public Vector2 velocity = new Vector2();
	
	public VelocityComponent() {
	}
	
	public VelocityComponent( float x , float y ) {
		velocity.x = x;
		velocity.y = y;
	}
	
	public VelocityComponent( Vector2 vec ) {
		velocity.set( vec );
	}
	
	@Override
	public VelocityComponent clone() {
		return new VelocityComponent( velocity.x , velocity.y );
	}
}
