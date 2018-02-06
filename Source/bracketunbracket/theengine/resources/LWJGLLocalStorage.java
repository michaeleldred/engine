package bracketunbracket.theengine.resources;

import java.io.OutputStream;

import org.json.JSONObject;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.resources.FileLoader.LocalStorage;

/**
 * @author Michael
 */
public class LWJGLLocalStorage implements LocalStorage, EventListener {

	private JSONObject root;
	
	@Override
	public void save() {
		OutputStream out = FileLoader.loader.save( "local.storage" );
		try {
			out.write( root.toString().getBytes() );
			out.close();
		} catch( Exception exc ) {
			exc.printStackTrace();
		}
	}

	@Override
	public void load() {
		FileSystem.loadData( "local.storage" , this );
	}

	@Override
	public String get( String key ) {
		if( root != null && root.has( key ) ) {
			return root.getString( key );
		}
		return null;
	}

	@Override
	public void put( String key, String value ) {
		root.put( key , value );
	}

	@Override
	public void receive(Event event) {
		try {
			Data data = null;
			
			if( event instanceof DataHandledEvent ) {
				data = ((DataHandledEvent)event).data;
			} else {
				System.out.println( "BAD" );
				return;
			}
			this.root = new JSONObject( ((TextData)data).getData() );
		} catch( Exception exc ) {
			System.out.println( "Creating new local storage" );
		}
	}

}
