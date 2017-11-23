package bracketunbracket.theengine.renderer.animation;

import bracketunbracket.theengine.renderer.Animation;

/**
 * @author Michael Eldred
 */
public class DelayAnimation extends Animation {

	private final Animation other;
	private float total = 0.0f;
	
	public DelayAnimation(float length , Animation other ) {
		super(length);
		this.other = other;
	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.renderer.Animation#update(float)
	 */
	@Override
	public void update(float delta) {

		total += delta;
		
		if( total >= length ) {
			source.addAnimation( other );
		}
	}
	
	@Override
	public boolean over() {
		return total >= length; 
	}

}
