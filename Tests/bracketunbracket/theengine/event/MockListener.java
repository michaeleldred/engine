/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Eldred
 */
public class MockListener implements EventListener {

	public List<Event> events = new ArrayList<Event>();
	
	@Override
	public void receive( Event event ) {
		events.add( event );
	}

}
