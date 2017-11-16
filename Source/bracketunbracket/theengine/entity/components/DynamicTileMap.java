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
	
	public void removeRow( int row ) {
		if( row < 0 || row >= height )
			throw new IndexOutOfBoundsException();
		 
		if( row == 0 ) {
			for( int i = 0; i < tiles.length; i++ ) {
				Tile[] newArray = new Tile[ height - 1 ];
				System.arraycopy( tiles[ i ] , 1 , newArray , 0 , height - 1 );
				tiles[ i ] = newArray;
			}
		} else if( row == height ) {
			for( int i = 0; i < tiles.length; i++ ) {
				Tile[] newArray = new Tile[ height - 1 ];
				System.arraycopy( tiles[ i ] , 0 , newArray , 0 , height - 1 );
				tiles[ i ] = newArray;
			}
		}
		
		this.height -= 1;
		updateTiles();
	}
	
	public void removeColumn( int col ) {
		if( col < 0 || col >= width )
			throw new IndexOutOfBoundsException();
		
		Tile[][] newArray = new Tile[ width - 1 ][ height ]; 
		if( col == 0 ) {
			System.arraycopy( tiles , 1 , newArray , 0 , width - 1 );
		} else if( col == width - 1 ) {
			System.arraycopy( tiles , 0 , newArray, 0 , width - 1 );
		}
		
		this.width -= 1;
		this.tiles = newArray;
		updateTiles();
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
