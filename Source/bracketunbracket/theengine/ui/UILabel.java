/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author Michael Eldred
 */
public class UILabel extends UIObject {
	
	public UILabel( UIPosition pos , RenderObject obj ) {
		super(pos);
		this.drawable = obj;
	}

	@Override
	public boolean receive( Event event ) {
		// Labels don't do anything with events.
		return false;
	}

}
