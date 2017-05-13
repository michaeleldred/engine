/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

import java.util.List;

public class MockGameSystem extends GameSystem {
	public boolean wasTicked;
	public List<Entity> entities;
	
	public void tick( List<Entity> entities ) {
		wasTicked = true;
		this.entities = entities;
	}
}
