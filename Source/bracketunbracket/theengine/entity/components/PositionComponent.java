/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;
import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael Eldred
 */
public class PositionComponent extends Component {
	public Vector2 position;
	
	public PositionComponent() {
		this( 0 , 0 );
	}
	
	public PositionComponent( float x , float y ) {
		position = new Vector2( x , y );
	}
	
	public PositionComponent( Vector2 v ) {
		this( v.x , v.y );
	}
	
	public PositionComponent( PositionComponent c ) {
		this( c.position.x , c.position.y );
	}
	
	@Override
	public PositionComponent clone() {
		return new PositionComponent( position.x , position.y );
	}
}
