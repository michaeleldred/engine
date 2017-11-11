/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;

/**
 * @author Michael Eldred
 */
public class TileMapComponent extends Component {
	public final StaticTileMap map;
	
	public TileMapComponent( int width , int height ) {
		map = new StaticTileMap( width , height );
	}
	
	public TileMapComponent( DynamicTileMap map ) {
		this.map = map;
	}
}
