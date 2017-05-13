/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author Michael Eldred
 */
public abstract class UIObject {
	public final UIPosition position;
	public RenderObject drawable;
	
	public UIObject( UIPosition pos ) {
		this.position = pos;
	}
	
	public abstract boolean receive( Event event );
	
}
