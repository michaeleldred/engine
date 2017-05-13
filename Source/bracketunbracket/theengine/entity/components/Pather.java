/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.ArrayList;
import java.util.List;

/**
 * Finds a path for the character on a {@link TileMap}.
 * 
 * @author Michael Eldred
 */
public class Pather {
	
	public static class Location {
		public int x;
		public int y;
		
		public Location( int x , int y ) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public boolean equals( Object other ) {
			if( other instanceof Location ) {
				Location o = (Location)other;
				
				return o.x == this.x && o.y == this.y;
			}
			
			return false;
		}
		
		@Override
		public String toString() {
			return "Location: (" + x + " , " + y + ")";
		}
	}
	
	private final TileMap tilemap;
	
	public Pather( TileMap tilemap ) {
		this.tilemap = tilemap;
	}
	

	public List<Location> getOpenLocations() {
		
		List<Location> locations = new ArrayList<Location>();
		
		if( tilemap.height < 3 ) {
			return locations;
		}
		
		for( int i = 0; i < tilemap.width; i++ ) {
			
			// Start at the second one because no elements on the first two rows
			// of the tilemap
			for( int j = 2; j < tilemap.height; j++ ) {
				
				// If the tile isn't null, check it
				if( !tilemap.isPassable( i , j ) ) {
					
					// If the two tiles above it are empty, then add it to the
					// passable locations
					if(  tilemap.isPassable( i , j - 1 ) &&
						 tilemap.isPassable( i , j - 2 ) ) {
						locations.add( new Location( i , j - 1 ) );
					}
				}
				
			}
		}
		
		return locations;
	}


	public Location chooseLocation( int x , int y ) {
		List<Location> locations = getOpenLocations();
		
		int left = 0;
		int right = 0;
		
		for( int i = 0; i < locations.size(); i++ ) {
			Location l = locations.get( i );
			if( l.x < x ) {
				right++;
			} else if( l.x > x ){
				left++;
			}
		}
		
		System.out.println( "Left: " + left + " , Right: " + right );
		
		if( left > right ) {
			// Choose the direction on the immediate left
			int i = y - 1;
			
			System.out.println( "TEST: " + tilemap.tiles[ x - 1 ][ i ] );
			
			if( tilemap.isPassable( x - 1 , i ) ) {
				return new Location( x - 1  , i );
			} else if( x - 1 > 0 && tilemap.isPassable( x - 1 , i - 1 ) ) {
				return new Location( x - 1  , i - 1 );
			}
			
		} else if( left < right ) {
			// Choose the direction on the immediate right
			int i = y - 1;
			
			if(  tilemap.isPassable( x + 1 , i ) ) {
				return new Location( x + 1  , i );
			} else if( x - 1 > 0 && tilemap.isPassable( x + 1 , i - 1 ) ) {
				return new Location( x + 1  , i - 1 );
			}
		}
		
		// Just return the location that we already have
		return new Location( x , y );
	}
	
	
}
