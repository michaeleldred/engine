package bracketunbracket.theengine.resources;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;

/**
 * Loads resources off the file system. These should be game resources and not
 * temporary files.
 * 
 * @author Michael Eldred
 */
public class FileSystem {
	public static FilePlatform filePlatform = new WebFilePlatform();
	
	public static TextData loadData( String filename , EventListener listener ) {
		TextData returnVal = filePlatform.getTextData( filename );
		returnVal.addListener( listener );
		return returnVal;
	}
}

/**
 * A piece of data that needs to be loaded so other classes can finish
 * notified when finished.
 * 
 * @author Michael Eldred
 */
abstract class Data {
	
	private List<EventListener> listeners = new ArrayList<EventListener>();
	
	public void addListener( EventListener listener ) {
		listeners.add( listener );
	}
	public void finished() {
		
		// Notify all listeners that this is finished
		for( EventListener listener : listeners ) {
			listener.receive( new DataHandledEvent( this ) );
		}
	}
}

class DataHandledEvent extends Event {
	
	public final Data data;
	
	public DataHandledEvent( Data data ) {
		this.data = data;
	}
}