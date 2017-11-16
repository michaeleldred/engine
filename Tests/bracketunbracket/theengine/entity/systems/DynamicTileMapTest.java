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
	
	@Test
	public void RemoveMapEdgeRow() {
		DynamicTileMap map = new DynamicTileMap( 2 , 2 );
		Tile t = new Tile();
		map.add( t , 1 , 1 );
		map.add( new Tile() , 0 , 0 );
		
		map.removeRow( 0 );
		
		assertEquals( 1 , map.height );
		assertEquals( t , map.get( 1 , 0 ) );
		
		map = new DynamicTileMap( 2 , 2 );
		map.add( t , 0 , 0 );
		map.add( new Tile() , 1 , 1 );
		
		map.removeRow( 1 );
		
		assertEquals( 1 , map.height );
		assertEquals( t , map.get( 0 , 0 ) );
	}
	
	@Test
	public void RemoveMapEdgeColumn() {
		DynamicTileMap map = new DynamicTileMap( 2 , 2 );
		Tile t = new Tile();
		map.add( t , 1 , 1 );
		map.add( new Tile() , 0 , 0 );
		
		map.removeColumn( 0 );
		
		assertEquals( 1 , map.width );
		assertEquals( t , map.get( 0 , 1 ) );
		
		map = new DynamicTileMap( 2 , 2 );
		t = new Tile();
		map.add( t , 0 , 0 );
		map.add( new Tile() , 1 , 1 );
		
		map.removeColumn( 1 );
		
		assertEquals( 1 , map.width );
		assertEquals( t , map.get( 0 , 0 ) );
	}
}
