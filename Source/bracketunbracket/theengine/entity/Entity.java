/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bracketunbracket.theengine.event.Event;

/**
 * An Entity class is a container for Components to be used in an 
 * Entity-Component-System.
 * 
 * @author Michael Eldred
 */
public class Entity {
	
	private Map< Class< ? extends Component > , List<Component> > components = new HashMap< Class< ? extends Component > , List<Component> >();
	
	public static int numCalls = 0;
	
	public List<Event> events = new ArrayList<Event>();
	public List<Event> added = new ArrayList<Event>();
	
	public EntityManager entityManager;
	
	/**
	 * Adds a new component to the entity
	 * 
	 * @param c The component to add
	 */
	@SuppressWarnings("unchecked")
	public void add( Component comp ) {
		
		comp.setParent( this );
		
		Class<?> insertClass = comp.getClass();
		while( insertClass != Object.class && insertClass != null ) {
			
			List<Component> c = components.get( insertClass );
			if( c == null ) {
				c = new ArrayList<Component>();
				c.add( comp );
				components.put( (Class<? extends Component>) insertClass , c );
			} else {
				c.add( comp );
			}
			
			insertClass = insertClass.getSuperclass();
		}
		
		events.add( new ComponentChangeEvent( comp ) );
	}
	
	/**
	 * Gets a single component of a specified type from the Entity.
	 * 
	 * @param classType The type of component to get
	 * 
	 * @return A single component of the type desired, or null if it was not
	 *         found
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponentByType( Class<T> classType ) {
		
		numCalls++;
		
		// Pre fetch this so we are only requesting the the list once, instead of
		// doing a contains and then a get. 
		List<Component> c = components.get( classType );
		
		// If there is a List, return the first one.
		if( c != null && c.size() > 0 ) {
			return (T) c.get( 0 );
		}
		
		// Return null if there are no components of the class
		return null;
		
	}
	
	/**
	 * Gets all of the components of a specific type from this entity
	 * 
	 * @param classType The type of component to get.
	 * 
	 * @return A list of components that are of the desired type in the Entity, 
	 *         or an empty list if there are none.
	 */
	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getAllComponentsByType( Class<T> classType ) {
		
		return (List<T>)components.get( classType );
		
	}
	
	/**
	 * Get all of the components that are related to this entity
	 * 
	 * @return
	 */
	public List<Component> getAllComponents() {
		List<Component> all = new ArrayList<Component>();
		for( List<Component> c : components.values() ) {
			all.addAll( c );
		}
		
		return all;
	}

	public <T extends Component> void remove( Class<T> classType ) {
		List<Component> c = components.get( classType );
		if( c != null )
			c.clear();
	}
	
	public <T extends Component> void remove( Class<T> classType , T object ) {
		List<Component> c = components.get( classType );
		c.remove( object );
	}
	
	public void receiveEvent( Event event ) {
		added.add( event );
	}

	/**
	 * Swaps the events that were used for the last tick with the ones that
	 * were added during the last frame.
	 */
	public void swapEvents() {
		List<Event> temp = events;
		events = added;
		added = temp;
		added.clear();
	}
	
	public void setParent( EntityManager manager ) {
		this.entityManager = manager;
	}
	
	@Override
	public Entity clone() {
		Entity retVal = new Entity();
		
		Collection<List<Component>> cloneList = components.values();
		
		for( List<Component> current : cloneList ) {
			for( int j = 0; j < current.size(); j++ ) {
				retVal.add( current.get( j ).clone() );
			}
		}
		
		return retVal;
	}
}
