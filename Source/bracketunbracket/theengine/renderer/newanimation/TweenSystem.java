/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.RenderComponent;

/**
 * @author michaeleldred
 *
 */
public class TweenSystem extends GameSystem {

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.entity.GameSystem#tick(java.util.List)
	 */
	@Override
	public void tick(List<Entity> entities) {
		List<Entity> sorted = sort( entities , AnimationComponent.class );
		
		System.out.println( sorted.size() );
		
		for( Entity current : sorted ) {
			AnimationComponent c = current.getComponentByType( AnimationComponent.class );
			if( c.animation.destination == null ) {
				c.animation.destination = current.getComponentByType( RenderComponent.class ).obj;
			}
			
			c.animation.update( 1 );
		}
	}

}
