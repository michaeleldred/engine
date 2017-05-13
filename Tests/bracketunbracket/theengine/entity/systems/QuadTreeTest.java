package bracketunbracket.theengine.entity.systems;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.entity.components.CollisionComponent;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.QuadTree;
import bracketunbracket.theengine.math.Rectangle;

public class QuadTreeTest {
	
	@Test
	public void CreateQuadTreeDimensions() {
		
		QuadTree quadTree = new QuadTree( -10 , -10 , 20 , 20 );
		Rectangle r = quadTree.dimensions;
		
		assertEquals( r.x , -10 , 0.1f );
		assertEquals( r.y , -10 , 0.1f );
		assertEquals( r.w ,  20 , 0.1f );
		assertEquals( r.h ,  20 , 0.1f );
	}
	
	@Test
	public void ObjectAddedToTree() {
		QuadTree quadTree = new QuadTree( -10 , -10 , 20 , 20 );
		CollisionComponent c = new CollisionComponent("" , 5 , 5 );
		PositionComponent p = new PositionComponent();
		
		quadTree.add( c , p );
		
		assertEquals( 1 , quadTree.numChildren() );
	}
	
	@Test
	public void ObjectNotAddedIfNotContained() {
		QuadTree quadTree = new QuadTree( -10 , -10 , 20 , 20 );
		CollisionComponent c = new CollisionComponent("" , 5 , 5 );
		PositionComponent p = new PositionComponent( 40 , 40 );
		
		quadTree.add( c , p );
		
		assertEquals( 0 , quadTree.numChildren() );
	}
	
	@Test
	public void ObjectCollisionsOccur() {
		QuadTree quadTree = new QuadTree( -10 , -10 , 20 , 20 );
		CollisionComponent c = new CollisionComponent( "" , 5 , 5 );
		PositionComponent p = new PositionComponent( 0 , 0 );
		
		quadTree.add( c , p );
		
		c = new CollisionComponent( "" , 5 , 5 );
		p = new PositionComponent( 1 , 1 );
		
		quadTree.add( c , p );
		
		quadTree.runCollisions();
		
		assertEquals( 1 , c.collisions.size() );
	}
	
	@Test
	public void SpiltQuadTreeHasChildren() {
		QuadTree tree = new QuadTree( 0 , 0 , 4 , 4 );
		tree.split();
		
		assertTrue( tree.hasChildren() );
	}
	
	@Test
	public void SpiltQuadTreeChildrenRightDimensions() {
		QuadTree tree = new QuadTree( 0 , 0 , 4 , 4 );
		tree.split();
		
		assertEquals( 4 , tree.getChildren().length );
		assertEquals( new Rectangle( 0 , 0 , 2 , 2 ) , tree.getChildren()[ 0 ].dimensions );
		assertEquals( new Rectangle( 2 , 0 , 2 , 2 ) , tree.getChildren()[ 1 ].dimensions );
		assertEquals( new Rectangle( 2 , 2 , 2 , 2 ) , tree.getChildren()[ 2 ].dimensions );
		assertEquals( new Rectangle( 0 , 2 , 2 , 2 ) , tree.getChildren()[ 3 ].dimensions );
	}
	
	@Test
	public void SplitPlacesObjectsInCorrectChildren() {
		QuadTree tree = new QuadTree( 0 , 0 , 100 , 100 );
		PositionComponent p;
		CollisionComponent c;
		
		// Top left corner
		p = new PositionComponent( 10 , 10 );
		c = new CollisionComponent( "" , 5 , 5 );
		tree.add( c , p );
		
		// Bottom Right corner
		p = new PositionComponent( 75 , 75 );
		tree.add( c ,  p );
		
		// Top Left and Top Right
		p = new PositionComponent( 50 , 10 );
		tree.add( c , p );
		
		tree.split();
		
		assertEquals( 2 , tree.getChildren()[ 0 ].numChildren() );
		assertEquals( 1 , tree.getChildren()[ 1 ].numChildren() );
		assertEquals( 1 , tree.getChildren()[ 2 ].numChildren() );
	}
	
	@Test
	public void ParentHasNoObjectsAfterSplit() {
		QuadTree tree = new QuadTree( 0 , 0 , 100 , 100 );
		PositionComponent p;
		CollisionComponent c;
		
		p = new PositionComponent( 10 , 10 );
		c = new CollisionComponent( "" , 5 , 5 );
		tree.add( c , p );
		
		tree.split();
		
		assertEquals( 0 , tree.numChildren() );
	}
	
	@Test
	public void ObjectsAddedToChildrenAfterSplit() {
		QuadTree tree = new QuadTree( 0 , 0 , 100 , 100 );
		PositionComponent p;
		CollisionComponent c;
		
		p = new PositionComponent( 10 , 10 );
		c = new CollisionComponent( "" , 5 , 5 );
		tree.add( c , p );
		
		tree.split();
		
		p = new PositionComponent( 75 , 75 );
		tree.add( c ,  p );
		
		assertEquals( 0 , tree.numChildren() );
		assertEquals( 1 , tree.getChildren()[ 0 ].numChildren() );
		assertEquals( 1 , tree.getChildren()[ 2 ].numChildren() );
	}
}
