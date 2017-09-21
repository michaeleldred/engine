/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

import java.util.ListIterator;
import java.util.Stack;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.renderer.GameWindow;

/**
 * @author Michael Eldred
 */
public class ScreenManager implements EventListener {

	public final Stack<Screen> screens = new Stack<Screen>();
	
	public final GameWindow window;
	
	public ScreenManager( GameWindow window ) {
		this.window = window;
	}
	
	public void setScreen( Screen screen ) {
		// If there is an old screen, remove the parent
		while( !screens.empty() ) {
			screens.pop();
		}
		
		screen.setParent( window );
		screen.create();
		screens.push( screen );
	}
	
	public void pushScreen( Screen screen ) {
		screen.setParent( window );
		screen.create();
		screens.push( screen );
	}
	
	public void popScreen() {
		screens.pop();
	}

	public Screen getCurrentScreen() {
		return screens.peek();
	}
	
	public void update() {
		for( ListIterator<Screen> it = screens.listIterator( screens.size() ); it.hasPrevious(); ) {
			// If update returns true, get out of the loop
			if( it.previous().update() )
				break;
		}
		
		for( Screen screen : screens ) {
			screen.render();
			screen.postRender();
		}
	}

	@Override
	public void receive( Event event ) {
		if( !screens.empty() )
			screens.peek().receive( event );
	}
}