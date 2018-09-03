/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.RenderComponent;
import bracketunbracket.theengine.event.GameEvent;

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
		
		for( GameEvent evt : getEventsByClass( GameEvent.class ) ) {
			System.out.println( evt.name );
		}
		
		for( Entity current : sorted ) {
			List<AnimationComponent> anims = current.getAllComponentsByType( AnimationComponent.class );
			for( AnimationComponent c : anims ) {
				
				for( GameEvent evt : getEventsByClass( GameEvent.class ) ) {
					//System.out.println( evt.name );
					if( evt.name.equalsIgnoreCase( c.event ) ) {
						System.out.println( "Running event: " + c.event + " for: " + c.parent );
						c.isActive = true;
					}
				}
				
				if( c.animation.currentTick >= c.animation.length ) {
					c.animation.reset();
					c.isActive = false;
				}
				
				if( c.animation.destination == null ) {
					c.animation.destination = current.getComponentByType( RenderComponent.class ).obj;
				}
				
				if( c.isActive ) {
					c.animation.update( 1 );
				}
			}
		}
	}

}
