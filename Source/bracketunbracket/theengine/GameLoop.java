package bracketunbracket.theengine;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.input.WindowEvent;

/**
 * @author Michael Eldred
 */
public class GameLoop implements EventListener {

	public List<Tickable> tickables = new ArrayList<>();
	
	boolean close = false;
	
	public void add( Tickable tickable ) {
		tickables.add( tickable );
	}
	
	public void run() {
		while( !close ) {
			tick();
		}
	}
	
	public void tick() {
		for( Tickable t : tickables ) {
			t.tick();
		}
	}

	@Override
	public void receive(Event event) {
		
		if( event instanceof WindowEvent ) {
			
			// Wait for a window close event.
			if( ((WindowEvent) event).windowState == WindowEvent.WINDOW_CLOSE )
				close = true;
		}
	}

}
