package bracketunbracket.theengine.renderer.animation;

import bracketunbracket.theengine.renderer.Animation;

/**
 * @author Michael
 */
public class ScaleAnimation extends Animation {
	
	public float start;
	public float end;
	public float total = 0.0f;
	
	public ScaleAnimation( float start , float end , float time ) {
		super( time );
		this.start = start;
		this.end = end;
	}
	
	/**
	 * @see bracketunbracket.theengine.renderer.animation#update(float)
	 */
	@Override
	public void update( float delta ) {
		
		total += delta;
		
		// If the total is less than length, update the size 
		if( total < length ) {
			source.scale = start + ( end - start ) * ( total / length );
		} else {
			source.scale = end;
		}
	}

}
