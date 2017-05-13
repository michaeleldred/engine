package bracketunbracket.theengine.entity;

import bracketunbracket.theengine.event.Event;

public class ComponentChangeEvent extends Event {
	public final boolean add = true;
	public final Component component;
	
	public ComponentChangeEvent( Component comp ) {
		this.component = comp;
	}
}
