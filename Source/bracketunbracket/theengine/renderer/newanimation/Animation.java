/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author Michael Eldred
 */
public abstract class Animation {
	
	/**
	 * The current tick of the frame
	 */
	public int currentTick = 0;
	
	/**
	 * The number of ticks the animation will run before resetting
	 */
	public int length = 0;
	
	/**
	 * If this is set to true, then the animation will loop indefinetly
	 */
	public boolean loop = false;
	
	/**
	 * A wrapper class for the math function that is going to be used to alter
	 * the parameter in the animation. This class is not going to be used in
	 * this class, only in sub-classes of the Animation.
	 */
	public Tweener tweener = null;
	
	public RenderObject destination = null;
	
	/**
	 * Creates a new Animation
	 * 
	 * @param length  The number of ticks the animation should last.
	 * @param tweener The mathematical function to alter the variables with
	 */
	public Animation( RenderObject destination , int length , Tweener tweener ) {
		this( destination , length , tweener , false );
	}
	
	/**
	 * Creates a new Animation
	 * 
	 * @param length  The number of ticks the animation should last.
	 * @param tweener The mathematical function to alter the variables with
	 */
	public Animation( RenderObject destination , int length , Tweener tweener , boolean loop ) {
		this.destination = destination;
		this.length = length;
		this.tweener = tweener;
		this.loop = loop;
	}
	
	/**
	 * Updates the current tick of the animation, making sure it is in the 
	 * correct range.
	 * 
	 * @param ticks The number of ticks to advance in the animation.
	 */
	public void update( int ticks ) {
		if( !loop && currentTick + ticks >= length ) {
			currentTick = length;
		} else {
			currentTick = ( currentTick + ticks ) % length;
		}
		
		// Call the subclass to do the meat work of the animation
		doUpdate();
	}
	
	public void reset() {
		currentTick = 0;
	}
	
	public abstract void doUpdate();
}