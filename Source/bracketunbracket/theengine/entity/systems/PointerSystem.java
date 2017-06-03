/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.CollisionComponent;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.Selectable;
import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.GameEvent;
import bracketunbracket.theengine.input.PointerEvent;
import bracketunbracket.theengine.math.Rectangle;

/**
 * @author Michael Eldred
 */
public class PointerSystem extends GameSystem {

	public List<PointerEvent> unused = new ArrayList<PointerEvent>();
	
	public final PointerEvent[] states = new PointerEvent[ 16 ];
	public final boolean[] used = new boolean[ 16 ];
	
	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.entity.GameSystem#tick(java.util.List)
	 */
	@Override
	public void tick(List<Entity> entities) {
		// Go through events and update states
		for( Event e : events ) {
			if( e instanceof PointerEvent ) {
				PointerEvent evt = (PointerEvent)e;
				
				states[ evt.button ] = evt;
			}
		}
		
		List<Entity> sorted = sort( entities , Selectable.class , CollisionComponent.class , PositionComponent.class );
		List<Entity> unprocessed = new ArrayList<Entity>();
		
		// Match all of the selected elements with their pointers
		for( Entity current : sorted ) {
			Selectable s = current.getComponentByType( Selectable.class );
			
			// Process unselected entities later
			if( s.button < 0 || s.event == null ) {
				// If it's not, process later
				unprocessed.add( current );
				continue;
			}
			
			// Check the state of the event
			PointerEvent evt = states[ s.button ];
			
			used[ s.button ] = true;
			
			// If the state is still down update the current event
			if( evt.isDown ) {
				s.event = evt;
			}
			// Otherwise release the object
			else {
				s.parent.receiveEvent( new GameEvent( "release" ) );
				s.button = -1;
				s.event = null;
			}
			
		}
		
		// Update unused
		for( int i = 0; i < used.length; i++ ) {
			if( !used[ i ] && states[ i ] != null ) {
				unused.add( states[ i ] );
			}
		}
		
		// Go through all of the components with pointers and process them
		for( Entity e : unprocessed ) {
			process( e );
		}
		
		// Clear out lists for next process
		for( int i = 0; i < states.length; i++ ) {
			if( states[ i ] != null && !states[ i ].isDown ) {
				states[ i ] = null;
			}
		}
		
		for( int i = 0; i < used.length; i++ ) {
			used[ i++ ] = false;
		}
		
		unused.clear();
		events.clear();
		
	}
	
	public void process( Entity e ) {
		
		CollisionComponent c = e.getComponentByType( CollisionComponent.class );
		PositionComponent p = e.getComponentByType( PositionComponent.class );
		Selectable s = e.getComponentByType( Selectable.class );
		
		Rectangle bounds = new Rectangle( p.position.x - ( c.width  / 2 ) , p.position.y - ( c.height  / 2 ) , c.width , c.height );
		
		// Match all of the entities with a pointer that has not been consumed
		for( Iterator<PointerEvent> it = unused.iterator(); it.hasNext(); ) {
			PointerEvent evt = it.next();
			
			// If the object contains the pointer event
			if( bounds.contains( evt.x , evt.y ) && evt.isDown ) {
				// Select the selectable
				s.button = evt.button;
				s.event = evt;
				
				// This event is used so remove it from the unused ones
				it.remove();
				break;
			}
		}
		
	}

}
