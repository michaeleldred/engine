/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author michaeleldred
 *
 */
public class MovementAnimation extends Animation {
	
	public Vector2 start;
	public Vector2 end;
	
	private Tweener xTweener;
	private Tweener yTweener;

	public MovementAnimation( Vector2 start , Vector2 end , RenderObject destination, int length, Tweener tweener) {
		this( start , end , destination , length , tweener , tweener );
	}
	
	public MovementAnimation( Vector2 start , Vector2 end , RenderObject destination, int length, Tweener xTweener , Tweener yTweener) {
		super(destination, length, xTweener);
		this.start = start;
		this.end = end;
		this.xTweener = xTweener;
		this.yTweener = yTweener;
	}
	
	

	@Override
	public void doUpdate() {
		destination.position.x = xTweener.getValue( start.x , end.x , currentTick, length );
		destination.position.y = yTweener.getValue( start.y , end.y , currentTick, length );
	}

}
