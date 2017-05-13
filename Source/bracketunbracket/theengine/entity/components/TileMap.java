/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author Michael Eldred
 */
public class TileMap {

	public final int width;
	public final int height;
	
	final Tile[][] tiles;
	
	private final static int[][] TILE_MATRIX = new int[][] {
		{ 0 , -1 } , { 0 , 1 } , { 1 , 0 } , { -1 , 0 },
	};
	
	public TileMap( int width , int height ) {
		this.width = width;
		this.height = height;
		
		tiles = new Tile[ width ][ height ];
	}
	
	/**
	 * Adds a tile to the tilemap at the specific location {@code ( x , y )}
	 * 
	 * @param tile The tile to add to the map
	 * @param x    The x location in the grid to add the tile, needs to be less
	 *             than {{@link #width}.
	 * @param y    The y location in the grid to add the tile, needs to be less
	 *             than {{@link #height}.
	 */
	public void add( Tile tile , int x , int y ) {
		tile.x = x;
		tile.y = y;
		tiles[ x ][ y ] = tile;
	}
	
	/**
	 * Gets a tile from the tilemap
	 * 
	 * @param x The x location in the grid to get the tile from, needs to be less
	 *          than {{@link #width}.
	 * @param y The y location in the grid to get the tile from, needs to be less
	 *          than {{@link #height}.
	 * @return  The tile at the location ( x , y ) in the tilemap, or null if
	 *          there is no tile at that location it will return {@code null}.
	 */
	public Tile get( int x , int y ) {
		if( x >= 0 && y >= 0 && x < width && y < height )
			return tiles[ x ][ y ];
		return null;
	}
	
	public Tile remove( int x , int y ) {
		Tile temp = tiles[ x ][ y ];
		
		// If there was a tile, reset it's coordinates
		/*if( temp != null ) {
			temp.x = -1;
			temp.y = -1;
		}*/
		tiles[ x ][ y ] = null;
		return temp;
	}
	
	public boolean isPassable( int x , int y ) {
		if ( tiles[ x ][ y ] == null ) {
			return true;
		}
		return tiles[ x ][ y ].isPassable();
	}
	
	public List<Tile> getNeighbours( Tile tile ) {
		return getNeighbours( tile.x , tile.y );
	}

	public List<Tile> getNeighbours( int x , int y ) {
		List<Tile> retVal = new ArrayList<Tile>();
		
		// Check all four neighbours if they are valid tiles, and have a path
		for( int i = 0; i < TILE_MATRIX.length; i++ ) {
			int[] curr = TILE_MATRIX[ i ];
			Tile t = get( x + curr[ 0 ], y + curr[ 1 ] );
			
			// If the tile is is passable, add it to the neighbour list.
			if( t != null ) {
				retVal.add( t );
			}
		}
		
		return retVal;
	}
	
	public List<Tile> getConnected( Tile start ) {
		List<Tile> retVal = new ArrayList<Tile>();
		Queue<Tile> frontier = new LinkedList<Tile>();
		ArrayList<Tile> visited = new ArrayList<Tile>();
		
		frontier.add( start );
		
		// Keep looking while there are places to look.
		while( !frontier.isEmpty() ) {
			Tile curr = frontier.poll();
			
			// If the tile is not passable, don't do anything with it.
			if( !curr.isPassable() || visited.contains( curr ) ) {
				continue;
			}
			
			retVal.add( curr );
			
			frontier.addAll( getNeighbours( curr ) );
			visited.add( curr );
		}
		
		return retVal;
	}
	
}
