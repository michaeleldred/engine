/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import bracketunbracket.theengine.renderer.Color;
import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author michaeleldred
 *
 */
public class ColorAnimation extends Animation {
	
	private Color start;
	private Color end;

	public ColorAnimation(Color start , Color end , RenderObject destination, int length, Tweener tweener) {
		super(destination, length, tweener);
		this.start = start;
		this.end = end;
	}

	@Override
	public void doUpdate() {
		destination.color.red   = tweener.getValue( start.red , end.red   , currentTick , length );
		destination.color.blue  = tweener.getValue( start.blue , end.blue  , currentTick , length );
		destination.color.green = tweener.getValue( start.green , end.green , currentTick , length );
		destination.color.alpha = tweener.getValue( start.alpha , end.alpha , currentTick , length );
	}

}
