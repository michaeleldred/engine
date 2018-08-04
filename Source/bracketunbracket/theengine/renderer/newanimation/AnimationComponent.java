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
	
	public AnimationComponent( Animation animation ) {
		this.animation = animation;
	}
}
