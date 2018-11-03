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
	public boolean passable = true;
	
	public Tile() {
		
	}
	
	public Tile( boolean passable ) {
		this.passable = passable;
	}
	
	public RenderObject obj;
	
	public boolean isPassable() {
		return passable;
	}
	
	public String getImageName() {
		 return "";
	}
}
