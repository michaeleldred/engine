package bracketunbracket.theengine.renderer.animation;

import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.Animation;

/**
 * @author Michael Eldred
 */
public class MoveAnimation extends Animation {

	public Vector2 start;
	public Vector2 end;
	private float total = 0.0f;
	
	public MoveAnimation( Vector2 start , Vector2 end , float length ) {
		super(length);
		this.start = start;
		this.end = end;
	}

	@Override
	public void update(float delta) {
		total += delta;
		
		// If the total is less than length, update the size 
		if( total < length ) {
			source.position.x += end.x * ( delta / length );
			source.position.y += end.y * ( delta / length );
		} else {
			source.position.set( end );
		}
	}
	

}
