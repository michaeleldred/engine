/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.event.EventManager;

/**
 * An entity manager manages all of the game objects in the game world, as well
 * as the systems that interact with them.
 * 
 * @author Michael Eldred
 */
public class EntityManager implements EventListener {
	
	/**
	 * A list of the entities that belong to this manager
	 */
	public final List<Entity> entities = new ArrayList<Entity>();
	
	/**
	 * A list of the systems that are run by this manager
	 */
	public final List<GameSystem> systems = new ArrayList<GameSystem>();
	
	private boolean updating = false;
	
	private final List<Entity> addedEntities = new ArrayList<Entity>();
	private final List<Entity> removeEntities = new ArrayList<Entity>();
	
	private final List<Event> newEvents = new ArrayList<Event>();
	
	private final EventManager eventManager = new EventManager();
	
	public EntityManager() {
		eventManager.addListener( this );
	}
	
	/**
	 * Run an update through all of the games systems.
	 */
	public void update() {
		updating = true;
		
		// Update events for next frame
		for( Event event : newEvents ) {
			for( GameSystem system : systems ) {
				system.receive( event );
			}
			
			for( Entity e : entities ) {
				e.receiveEvent( event );
			}
		}
		
		newEvents.clear();
		
		for( GameSystem system : systems ) {
			system.tick( entities );
			system.events.clear();
		}
		updating = false;
		entities.addAll( addedEntities );
		addedEntities.clear();
		
		entities.removeAll( removeEntities );
		removeEntities.clear();
		
		// Clear all of the events that were not added this frame
		for( Entity e : entities ) {
			e.swapEvents();
		}
		
		//System.out.println("Called: " + Entity.numCalls );
		Entity.numCalls = 0;
	}
	
	/**
	 * Adds a new entity to the list of entities that have been added.
	 */
	public void add( Entity newEntity ) {
		
		// If the manager is not updating systems, just add the new
		// entity to the list of entities
		if( !updating ) {
			this.entities.add( newEntity );
		}
		
		// If the manager is updating systems, add the new entity to be added
		// after the systems have updated. 
		else {
			this.addedEntities.add( newEntity );
		}
	}
	
	public void remove( Entity entity ) {
		// If the manager is not updating systems, just remove the entity from
		// the list
		if( !updating ) {
			this.entities.remove( entity );
		}
		
		// If the manager is updating systems, add the new entity to be added
		// after the systems have updated. 
		else {
			this.removeEntities.add( entity );
		}
	}
	
	/**
	 * Adds a new system to the entity manager
	 */
	public void addSystem( GameSystem newSystem ) {
		newSystem.manager = this;
		newSystem.eventManager = eventManager;
		
		systems.add( newSystem ); 
	}
	
	public void removeSystem( GameSystem system ) {
		systems.remove( system );
	}

	/**
	 * 
	 * @param event
	 */
	@Override
	public void receive( Event event ) {
		newEvents.add( event );
	}
}
