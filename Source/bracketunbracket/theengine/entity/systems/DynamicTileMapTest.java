/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.entity.components.DynamicTileMap;
import bracketunbracket.theengine.entity.components.Tile;

/**
 * @author Michael Eldred
 */
public class DynamicTileMapTest {
	@Test
	public void ExpandArrayWhenNewElementAdded() {
		DynamicTileMap map = new DynamicTileMap( 2 , 2 );
		map.add( new Tile() , 2 , 0 );
		
		assertEquals( 3 , map.width );
		
		map.add( new Tile() , 0 , 2 );
		
		assertEquals( 3 , map.height );
		
		map.add( new Tile() , 3 , 3 );
		
		assertEquals( 4 , map.width );
		assertEquals( 4 , map.height );
	}
	
	@Test
	public void ExpandArrayWhenNegativeLocationAdded() {
		DynamicTileMap map = new DynamicTileMap( 2 , 2 );
		Tile t = new Tile();
		map.add( t , -1 , 0 );
		
		assertEquals( 3 , map.width );
		assertEquals( t , map.get( 0 , 0 ) );
		
		map.add( new Tile() , -2 , 0 );
		
		assertEquals( 5 , map.width );
		assertEquals( t , map.get( 2 , 0 ) );
		
		t = new Tile();
		map.add( t , 0 , -1 );
		
		assertEquals( 3 , map.height );
		assertEquals( t , map.get( 0 , 0 ) );
		
		map.add( new Tile() , 0 , -2 );
		
		assertEquals( 5 , map.height );
		assertEquals( t , map.get( 0 , 2 ) );
	}
}
