/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import bracketunbracket.theengine.entity.Component;

/**
 * @author michaeleldred
 *
 */
public class AnimationComponent extends Component {
	public Animation animation;
	public String event;
	public boolean isActive = false;
	
	protected AnimationComponent( Animation animation ) {
		this.animation = animation;
	}
	
	public AnimationComponent( String event , Animation animation ) {
		this.animation = animation;
		this.event = event;
	}
}
