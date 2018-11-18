/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author Michael Eldred
 */
public class Tile {
	public int x = -1;
	public int y = -1;
	public boolean passable = true;
	public StaticTileMap map;
	
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
	
	public Vector2 getTileCenter( Vector2 mapOrigin ) {
		Vector2 retVal = new Vector2( 0 , 0 );
		int midX = map.width / 2;
		int midY = map.height / 2;
		
		retVal.x = mapOrigin.x + ( x - midX ) * 70.0f + ( map.width % 2 == 1 ? 0.0f : 35.0f );;
		retVal.y = mapOrigin.y + ( y - midY ) * 70.0f; 
		
		return retVal;
	}
}
