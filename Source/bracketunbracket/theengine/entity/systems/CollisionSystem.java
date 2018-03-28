/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.CollisionComponent;
import bracketunbracket.theengine.entity.components.PositionComponent;

/**
 * @author Michael Eldred
 */
public class CollisionSystem extends GameSystem {

	@Override
	public void tick(List<Entity> entities) {
		QuadTree qt = new QuadTree(-2000 , -2000 , 4000 , 4000 );
		
		List<Entity> sorted = sort( entities , CollisionComponent.class , PositionComponent.class );
		
		for( Entity current : sorted ) {
				
			PositionComponent position = current.getComponentByType( PositionComponent.class );
			
			// Add all of the position components
			List<CollisionComponent> colliders = current.getAllComponentsByType( CollisionComponent.class );
			
			for( CollisionComponent collider : colliders ) {
				collider.collisions.clear();
				qt.add( collider , position );
			}
			
		}
		
		qt.runCollisions();
		
	}

}
