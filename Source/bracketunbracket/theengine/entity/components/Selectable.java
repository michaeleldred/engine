/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;
import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.RenderComponent;
import bracketunbracket.theengine.input.PointerEvent;

/**
 * @author Michael Eldred
 */
public class Selectable extends Component {
	public int button = -1;
	public PointerEvent event = null;
	
	public final Entity box;
	
	public Selectable() {
		box = new Entity();
		box.add( new PositionComponent() );
		box.add( new RenderComponent( "selection" , 84 , 84 , 6 ) );
	}
}
