/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author michaeleldred
 *
 */
public class ScaleAnimation extends Animation {
	private float min;
	private float max;
	public ScaleAnimation( float min , float max , RenderObject destination, int length, Tweener tweener) {
		super(destination, length, tweener);
		this.max = max;
		this.min = min;
	}

	@Override
	public void doUpdate() {
		destination.scale = tweener.getValue( min , max , currentTick , length );
	}

}
