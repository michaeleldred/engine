/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author Michael Eldred
 */
public class Tile {
	public int x = -1;
	public int y = -1;
	
	public RenderObject obj;
	
	public boolean isPassable() {
		return false;
	}
	
	public String getImageName() {
		 return "";
	}
}
