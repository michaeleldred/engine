/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Eldred
 */
public class ResourceParser {
	
	private final Map< String , ResourceLoader > loaders = new HashMap< String , ResourceLoader >();
	
	public final class UnloadedResource {
		public final ResourceLoader loader;
		public final Map<String, String> vals;
		public final ResourceManager manager;
		
		public UnloadedResource( ResourceLoader loader , Map<String, String> vals , ResourceManager manager ) {
			this.loader = loader;
			this.vals = vals;
			this.manager = manager;
		}
		
		public void load( ) throws Exception {
			loader.create( manager , vals );
		}
	}
	
	public final void addLoader( String type , ResourceLoader loader ) {
		loaders.put( type , loader );
	}
	
	public final List<UnloadedResource> parseJSON( ResourceManager manager , String text ) {
		List<UnloadedResource> retVal = new ArrayList<UnloadedResource>();
		
		try {
		JSONObject root = new JSONObject( text );
		JSONArray resources = root.getJSONArray( "resources" );
		
		for( int i = 0; i < resources.length(); i++ ) {
			
			JSONObject curr = resources.getJSONObject( i );
			String type = curr.getString( "type" );
			Map<String, String> vals = new HashMap<String,String>();
			
			for ( Iterator<String> jt = curr.keys(); jt.hasNext(); ) {
				String s = jt.next();
				
				vals.put( s , curr.getString( s ) );
			}
			
			if( loaders.containsKey( type ) ) {
				UnloadedResource res = new UnloadedResource(loaders.get( type ) , vals , manager );
				retVal.add( res );
			}
		}
		} catch( Exception exc ) {
			exc.printStackTrace();
		}
		
		return retVal;
	}
	
	public boolean loads( String type ) {
		return loaders.containsKey( type );
	}
}
