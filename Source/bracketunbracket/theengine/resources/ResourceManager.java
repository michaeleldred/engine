package bracketunbracket.theengine.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;

/**
 * @author Michael
 */
public class ResourceManager implements EventListener {
	
	private HashMap< String , ResourceParser > resParsers = new HashMap< String , ResourceParser >();
	
	private final List< Resource > loaded = new ArrayList< Resource >();
	private final List< Resource > unloaded = new ArrayList< Resource >();
	
	/**
	 * Load a file 
	 * 
	 * @param filename
	 */
	public void load( String filename ) {
		FileSystem.loadData( filename , this );
	}
	
	
	/**
	 * Add an parser to load certain types of objects.
	 * 
	 * @param parser The object that parses
	 */
	public void addParser( ResourceParser parser ) {
		parser.resourceManager = this;
		// Register the parser for each of the types
		for( String type : parser.types ) {
			resParsers.put( type , parser );
		}
	}

	@Override
	public void receive(Event event) {
		Data data = null;
		if( event instanceof DataHandledEvent ) {
			data = ((DataHandledEvent)event).data;
		} else {
			return;
		}
		
		JSONObject root = new JSONObject( ((TextData)data).getData() );
		
		JSONArray dataList = root.getJSONArray( "resources" );
		
		for( int i = 0; i < dataList.length(); i++ ) {
			JSONObject currentResource = dataList.getJSONObject( i );
			
			String type = currentResource.getString( "type" );
			
			// If the type has a parser, gather the infomration and send it
			if( resParsers.containsKey( type ) ) {
				Set<String> keys = currentResource.keySet();
				HashMap< String , String > values = new HashMap< String , String >(); 
				for( String key : keys ) {
					values.put( key , currentResource.getString( key ) );
				}
				
				add( resParsers.get( type ).load( values ) );
			}
			
		}
	}

	/**
	 * Resources notify the manager when they are loaded using this method so
	 * that the manager can update teh state.
	 * 
	 * @param res The resource that just finised loading
	 */
	public void loaded( Resource res ) {
		
		// Update the lists
		unloaded.remove( res );
		loaded.add( res );
	}
	
	public void add( Resource resource ) {
		resource.setManager( this );
		if( !loaded.contains( resource ) ) {
			unloaded.add( resource );
		}
	}
	
	public int getLoaded() {
		return loaded.size();
	}
	public int getUnloaded() {
		return unloaded.size();
	}
}
