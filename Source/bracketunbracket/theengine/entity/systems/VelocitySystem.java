package bracketunbracket.theengine.entity.systems;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.VelocityComponent;
import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael
 */
public class VelocitySystem extends GameSystem {

	/**
	 * @see bracketunbracket.theengine.entity.GameSystem#tick(java.util.List)
	 */
	@Override
	public void tick( List<Entity> entities ) {
		List<Entity> sorted = sort( entities , PositionComponent.class , VelocityComponent.class );
		
		for( int i = 0; i < sorted.size(); i++ ) {
			Entity current = sorted.get( i );
			
			Vector2 velocity = current.getComponentByType( VelocityComponent.class ).velocity;
			current.getComponentByType( PositionComponent.class ).position.add( velocity );
		}
		
	}

}
