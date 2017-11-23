package bracketunbracket.theengine.renderer.animation;

import bracketunbracket.theengine.renderer.Animation;

/**
 * @author Michael Eldred
 */
public class FadeAnimation extends Animation {

	public float start;
	public float end;
	public float total = 0.0f;
	
	public FadeAnimation( float start , float end , float length ) {
		super( length );
		this.start = start;
		this.end = end;
	}

	@Override
	public void update( float delta ) {

		total += delta;
		
		// If the total is less than length, update the size 
		if( total < length ) {
			source.color.alpha = start + ( end - start ) * ( total / length );
		} else {
			source.color.alpha = end;
		}
	}

	@Override
	public boolean over() {
		return total >= length; 
	}
}
