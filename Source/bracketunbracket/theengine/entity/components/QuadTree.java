/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.math.Rectangle;

/**
 * @author Michael Eldred
 */
public class QuadTree {
	
	public final Rectangle dimensions;
	
	public final static int MAX_OBJECTS = 10;
	public final static int MAX_LEVELS = 5;
	
	private int level = 0;
	
	private final List<QuadTreeObject> objects = new ArrayList<QuadTreeObject>();
	
	private QuadTree[] children = new QuadTree[ 4 ];
	
	private boolean hasChildren = false;
	
	public QuadTree( float x , float y , float w , float h ) {
		dimensions = new Rectangle( x , y , w , h );
	}
	
	public boolean hasChildren() {
		return hasChildren;
	}
	
	public void add( QuadTreeObject obj ) {
		add( obj.c , obj.p );
	}
	
	public QuadTree[] getChildren() {
		return children;
	}
	
	public void add( CollisionComponent c , PositionComponent p ) {
		
		float minX = p.position.x - c.width / 2.0f;
		float maxX = p.position.x + c.width / 2.0f;
		
		float minY = p.position.y - c.height / 2.0f;
		float maxY = p.position.y + c.height / 2.0f;
		
		if( minX > dimensions.x + dimensions.w ||
			maxX < dimensions.x ||
			
			minY > dimensions.y + dimensions.h ||
			maxY < dimensions.y ) {
			
			return;
		}
		

		// If there is children just add it to all of the children
		if( hasChildren ) {
			for( int i = 0; i < children.length; i++ ) {
				children[ i ].add( new QuadTreeObject( c , p ) );
			}
			
			return;
		}	
			
		objects.add( new QuadTreeObject( c , p ) );
		
		// Disperse the objects if there are too many objects in the
		// current quad
		if( objects.size() > MAX_OBJECTS ) {
			split();
		}
	}
	
	public int numChildren() {
		return objects.size();
	}
	
	public void runCollisions() {
		
		// If the object has children, run the collisions for each child
		if( hasChildren ) {
			for( int i = 0; i < children.length; i++ ) {
				children[ i ].runCollisions();
			}
			return;
		}
		
		// System.out.println( "Checking " + objects.size() + " objects" );
		
		for( int i = 0; i < objects.size(); i++ ) {
			
			PositionComponent p = objects.get( i ).p;
			CollisionComponent c = objects.get( i ).c;
			
			float minX = p.position.x - c.width / 2.0f;
			float maxX = p.position.x + c.width / 2.0f;
			
			float minY = p.position.y - c.height / 2.0f;
			float maxY = p.position.y + c.height / 2.0f;
			
			for( int j = i+1; j < objects.size(); j++ ) {
				PositionComponent p2 = objects.get( j ).p;
				CollisionComponent c2 = objects.get( j ).c;
				
				if( minX > p2.position.x + c2.width / 2.0f ||
					maxX < p2.position.x - c2.width / 2.0f ||
					
					minY > p2.position.y + c2.height / 2.0f ||
					maxY < p2.position.y - c2.height / 2.0f ) {
					continue;
				} else {
					
					c2.collisions.add( new Collision( c ) );
					c.collisions.add( new Collision( c2 ) );
				}
				
			}
		}
	}
	
	public void split() {
		
		// Don't create a tree that goes too deep
		if( this.level >= MAX_LEVELS )
			return;
		
		children[ 0 ] = new QuadTree( dimensions.x                       , dimensions.y                       , dimensions.w / 2.0f , dimensions.h / 2.0f );
		children[ 1 ] = new QuadTree( dimensions.x + dimensions.w / 2.0f , dimensions.y                       , dimensions.w / 2.0f , dimensions.h / 2.0f );
		children[ 2 ] = new QuadTree( dimensions.x + dimensions.w / 2.0f , dimensions.y + dimensions.h / 2.0f , dimensions.w / 2.0f , dimensions.h / 2.0f );
		children[ 3 ] = new QuadTree( dimensions.x                       , dimensions.y + dimensions.h / 2.0f , dimensions.w / 2.0f , dimensions.h / 2.0f );
		
		// Update the tree level
		for( int i = 0; i < children.length; i++ ) {
			children[ i ].level = this.level + 1;
		}
		
		
		for( QuadTreeObject obj : objects ) {
			for( int i = 0; i < children.length; i++ ) {
				children[ i ].add( obj );
			}
		}
		
		objects.clear();
		
		hasChildren = true;
	}
}

class QuadTreeObject {
	public final CollisionComponent c;
	public final PositionComponent p;
	
	public QuadTreeObject( CollisionComponent c , PositionComponent p ) {
		this.c = c;
		this.p = p;
	}
}