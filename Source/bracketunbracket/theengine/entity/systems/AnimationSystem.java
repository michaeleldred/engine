/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.AnimationComponent;
import bracketunbracket.theengine.entity.components.RenderComponent;

/**
 * @author Michael Eldred
 */
public class AnimationSystem extends GameSystem {

	@Override
	public void tick( List<Entity> entities ) {
		
		for( Entity current : entities ) {
			AnimationComponent animation = current.getComponentByType( AnimationComponent.class );
			RenderComponent render = current.getComponentByType( RenderComponent.class );
			
			if( render == null || animation == null ) {
				continue;
			}
			
			// Update the current frame of the animation if the number of ticks has passed
			if( ++animation.ticks >= animation.ticksPerFrame ) {
				animation.ticks = 0;
				
				if( animation.loop )
					animation.currentFrame = ++animation.currentFrame % animation.frames.size();
				else
					animation.currentFrame = Math.min( ++animation.currentFrame , animation.frames.size() - 1 );
				
				render.obj.imName = animation.frames.get( animation.currentFrame );
			}
		}
	}

}
