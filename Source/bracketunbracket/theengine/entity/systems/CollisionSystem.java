/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.CollisionComponent;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.QuadTree;

/**
 * @author Michael Eldred
 */
public class CollisionSystem extends GameSystem {

	@Override
	public void tick(List<Entity> entities) {
		
		QuadTree qt = new QuadTree(-500 , -500 , 1000 , 1000 );
		
		for( Entity current : entities ) {
				
			PositionComponent position = current.getComponentByType( PositionComponent.class );

			if( position == null ) {
				continue;
			}
			
			// Add all of the position components
			List<CollisionComponent> colliders = current.getAllComponentsByType( CollisionComponent.class );
			
			if( colliders == null ) {
				continue;
			}
			
			for( CollisionComponent collider : colliders ) {
				collider.collisions.clear();
				qt.add( collider , position );
			}
			
		}
		
		qt.runCollisions();
		
	}

}
