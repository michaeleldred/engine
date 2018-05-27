package bracketunbracket.theengine.math;

/**
 * @author Michael Eldred
 */
public class Rectangle {
	
	public float x;
	public float y;
	public float w;
	public float h;
	
	/*
	 * The tolerance for two rectangles being equal to deal with floats
	 * inaccuracies
	 */
	private static final float TOLERANCE = 0.00001f;
	
	public Rectangle() {
		this( 0.0f , 0.0f , 0.0f , 0.0f );
	}
	
	public Rectangle( float w, float h ) {
		this( 0.0f , 0.0f , w , h );
	}
	
	public Rectangle( float x , float y , float w, float h ) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Rectangle( Vector2 center , float w , float h ) {
		this.x = center.x - w/2;
		this.y = center.y - h/2;
		this.w = w;
		this.h = h;
	}
	
	public void setLocation( float x , float y ) {
		this.x = x;
		this.y = y;
	}
	
	public void setBounds( float x , float y , float w , float h ) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean intersects( Rectangle r2 ) {
		return !( r2.x > x + w ||
				  r2.x + r2.w < x ||
				  r2.y > y + h ||
				  r2.y + r2.h < y );
	}
	
	public boolean contains( Vector2 point ) {
		return this.x < point.x && this.x + this.w > point.x &&
				this.y < point.y && this.y + this.h > point.y;
	}
	
	public boolean contains( float x , float y ) {
		return this.x < x && this.x + this.w > x &&
				this.y < y && this.y + this.h > y;
	}
	
	public Vector2 center() {
		float x = this.x + w / 2;
		float y = this.y + h / 2;
		
		return new Vector2( x , y );
	}

	@Override
	public boolean equals( Object other ) {
		if( !( other instanceof Rectangle ) ) {
			return false;
		}
		
		Rectangle r = (Rectangle)other;
		
		return Math.abs( r.x - x ) < TOLERANCE &&
				Math.abs( r.y - y ) < TOLERANCE &&
				Math.abs( r.w - w ) < TOLERANCE &&
				Math.abs( r.h - h ) < TOLERANCE;
	}
	
	public Rectangle intersection( Rectangle other ) {
		if( !this.intersects( other ) ) {
			return new Rectangle();
		}
		
		// If they intersect
		// Algorithm from: http://stackoverflow.com/questions/19753134/get-the-points-of-intersection-from-2-rectangles
		float x1 = Math.max( this.x , other.x );
		float y1 = Math.max( this.y , other.y );
		
		float x2 = Math.min( this.x + this.w , other.x + other.w );
		float y2 = Math.min( this.y + this.h , other.y + other.h );
		
		
		// Return the created rectangle.
		return new Rectangle( x1 , y1 , x2 - x1 , y2 - y1 );
	}
	
	/** 
	 * Returns the area of this rectangle, will be a positive number.
	 * 
	 * @return The area of the rectangle, as a number from 0 to the maximum
	 *         value of the float.
	 */
	public float area() {
		return Math.abs( w * h );
	}
	
	@Override
	public String toString() {
		return "Rect( " + x + " , " + y + " , " + w + " , " + h + " )";
	}
}
