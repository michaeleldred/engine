package bracketunbracket.theengine.renderer;

/**
 * @author Michael
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
	public void update(float delta) {

		total += delta;
		
		// If the total is less than length, update the size 
		if( total < length ) {
			source.color.alpha = start + ( end - start ) * ( total / length );
		} else {
			source.color.alpha = end;
		}
	}

}
