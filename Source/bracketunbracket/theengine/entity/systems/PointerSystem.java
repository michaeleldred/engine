/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.CollisionComponent;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.Selectable;
import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.GameEvent;
import bracketunbracket.theengine.input.PointerEvent;
import bracketunbracket.theengine.input.PointerEvent.EventType;
import bracketunbracket.theengine.math.Rectangle;

/**
 * @author Michael Eldred
 */
public class PointerSystem extends GameSystem {

	public List<PointerEvent> unused = new ArrayList<PointerEvent>();
	
	public final Map<Integer,PointerEvent> states = new HashMap<Integer,PointerEvent>( 16 );
	public final Map<Integer,Boolean> used = new HashMap<Integer,Boolean>();
	
	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.entity.GameSystem#tick(java.util.List)
	 */
	@Override
	public void tick(List<Entity> entities) {
		// Go through events and update states
		for( Event e : events ) {
			if( e instanceof PointerEvent ) {
				PointerEvent evt = (PointerEvent)e;
				states.put( evt.button , evt );
				used.put( evt.button , false );
			}
		}
		
		List<Entity> sorted = sort( entities , Selectable.class , CollisionComponent.class , PositionComponent.class );
		List<Entity> unprocessed = new ArrayList<Entity>();
		
		// Match all of the selected elements with their pointers
		for( Entity current : sorted ) {
			Selectable s = current.getComponentByType( Selectable.class );
			
			// Process unselected entities later
			if( s.event == null || s.button < 0 ) {
				// The entity does not have an event, deal with it later
				unprocessed.add( current );
				continue;
			}
			
			// Check the state of the event
			PointerEvent evt = states.get( s.button );
			
			// If the state is still down update the current event
			if( evt != null && evt.isDown  ) {
				s.event = evt;
				used.put( s.button , true );
			}
			// Otherwise release the object
			else {
				s.parent.receiveEvent( new GameEvent( "release" ) );
				s.button = -1;
				s.event = null;
			}
		}
		
		// Update unused
		for( Integer i : used.keySet() ) {
			if( !used.get( i.intValue() ) && states.containsKey( i ) ) {
				unused.add( states.get( i ) );
			}
		}
		
		// Go through all of the components with pointers and process them
		for( Entity e : unprocessed ) {
			process( e );
		}
		
		// Clear out lists for next process
		for( Iterator<Integer> it = states.keySet().iterator(); it.hasNext(); ) {
			int i = it.next();
			if( !states.get( i ).isDown ) {
				it.remove();
				used.remove( i );
			}
		}
		
		for( Integer i : used.keySet() ) {
			used.put( i , false );
		}
		
		unused.clear();
		events.clear();
		
	}
	
	public void process( Entity e ) {
		
		CollisionComponent c = e.getComponentByType( CollisionComponent.class );
		PositionComponent p = e.getComponentByType( PositionComponent.class );
		Selectable s = e.getComponentByType( Selectable.class );
		
		Rectangle bounds = new Rectangle( p.position.x - ( c.getWidth()  / 2 ) , p.position.y - ( c.getHeight()  / 2 ) , c.getWidth() , c.getHeight() );
		
		// Match all of the entities with a pointer that has not been consumed
		for( Iterator<PointerEvent> it = unused.iterator(); it.hasNext(); ) {
			PointerEvent evt = it.next();
			
			// If the object contains the pointer event
			if( bounds.contains( evt.x , evt.y ) && evt.isDown && evt.type == EventType.DOWN ) {
				// Select the selectable
				s.button = evt.button;
				s.event = evt;
				
				// This event is used so remove it from the unused ones
				it.remove();
				break;
			} else {
				
				s.button = -1;
				s.event = null;
			}
		}
		
	}

}
