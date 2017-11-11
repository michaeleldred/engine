/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.Arrays;

import bracketunbracket.theengine.entity.components.StaticTileMap;
import bracketunbracket.theengine.entity.components.Tile;

/**
 * @author Michael Eldred
 */
public class DynamicTileMap extends StaticTileMap {

	public DynamicTileMap(int width, int height) {
		super( width, height );
	}
	
	@Override
	public void add( Tile tile , int x , int y ) {
		if( x >= width ) {
			// Make the map bigger
			this.tiles = Arrays.copyOf( tiles , x + 1 );
			// Fill in the new rows
			for( int i = width; i < tiles.length; i++ ) {
				tiles[ i ] = new Tile[ height ];
			}
			this.width = tiles.length;
		} else if( x < 0 ) {
			Tile[][] newArray = new Tile[ width - x ][ height ];
			System.arraycopy( tiles , 0 , newArray , -x , tiles.length );
			
			x = 0;
			this.tiles = newArray;
			this.width = tiles.length;
		}
		
		
		if( y >= height ) {
			// Make the map bigger
			for( int i = 0; i < tiles.length; i++ ) {
				tiles[ i ] = Arrays.copyOf( tiles[ i ] , y + 1 );
			}
			
			height = y + 1;
		} else if( y < 0 ) {
			for( int i = 0; i < tiles.length; i++ ) {
				Tile[] newArray = new Tile[ height - y ];
				System.arraycopy( tiles[ i ] , 0 , newArray , -y , tiles[ i ].length );
				tiles[ i ] = newArray;
			}
			
			y = 0;
			this.height = tiles[ 0 ].length;
		}
		
		// update tile
		updateTiles();
		super.add( tile , x , y );
	}
	
	@Override
	public Tile get( int x , int y ) {
		if( x >= 0 && y >= 0 && x < width && y < height )
			return tiles[ x ][ y ];
		return null;
	}

	private void updateTiles() {
		for( int i = 0; i < tiles.length; i++ ) {
			for( int j = 0; j < tiles[ i ].length; j++ ) {
				if( tiles[ i ][ j ] != null ) {
					tiles[ i ][ j ].x = i;
					tiles[ i ][ j ].y = j;
				}
			}
		}
	}
}
