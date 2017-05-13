/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;

/**
 * @author Michael Eldred
 */
public class TileMapComponent extends Component {
	public final TileMap map;
	
	public TileMapComponent( int width , int height ) {
		map = new TileMap( width , height );
	}
}
