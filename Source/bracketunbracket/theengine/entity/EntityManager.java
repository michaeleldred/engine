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
		//System.out.println( entities.size() );
	}
	
	/**
	 * Adds a new entity to the list of entities that have been added.
	 */
	public void add( Entity newEntity ) {
		
		// If the manager is not updating systems, just add the new
		// entity to the list of entities
		if( !updating ) {
			this.entities.add( newEntity );
			newEntity.setParent( this );
		}
		
		// If the manager is updating systems, add the new entity to be added
		// after the systems have updated. 
		else {
			this.addedEntities.add( newEntity );
			newEntity.setParent( this );
		}
	}
	
	/**
	 * Convenience method to add a list of entities. Just calls {@link
	 * #add(Entity)} for each of the entities in the list.
	 * 
	 * @param entities The list of entities to add.
	 */
	public void addAll( List<Entity> entities ) {
		// Just add all of the entities individually
		for( int i = 0; i < entities.size(); i++ ) {
			add( entities.get( i ) );
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
		newSystem.init();
	}
	
	public void removeSystem( GameSystem system ) {
		system.destroy();
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
	
	public void destroy() {
		for( GameSystem system : systems) {
			system.destroy();
		}
	}
}
