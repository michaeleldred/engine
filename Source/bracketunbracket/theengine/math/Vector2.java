package bracketunbracket.theengine.math;

/**
 * @author Michael Eldred
 */
public class Vector2 implements Cloneable {
	public float x;
	public float y;
	
	private final static float VECTOR_DIFF = 0.000000001f; 
	
	public Vector2() {
		this.x = 0;
		this.y = 0;
	}
	
	public Vector2( float x , float y ) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2( Vector2 other ) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vector2 add( Vector2 vec ) {
		this.x += vec.x;
		this.y += vec.y;
		
		return this;
	}
	
	public Vector2 mult( float f ) {
		this.x *= f;
		this.y *= f;
		
		return this;
	}
	
	public void set( Vector2 vec ) {
		this.x = vec.x;
		this.y = vec.y;
	}
	
	public void set( float x , float y ) {
		this.x = x;
		this.y = y;
		
	}
	
	public float dist() {
		return (float)Math.sqrt( this.x * this.x + this.y * this.y );
	}
	
	public float distTo( Vector2 other ) {
		float x = this.x - other.x;
		float y = this.y - other.y;
		
		return (float)Math.sqrt( x * x + y * y );
	}
	
	@Override
	public boolean equals( Object other ) {
		return equals( other , VECTOR_DIFF );
	}
	
	public boolean equals( Object other , float tolerance ) {
		if( other instanceof Vector2 ) {
			Vector2 v = (Vector2)other;
			return ( Math.abs( x - v.x ) < tolerance &&
					 Math.abs( y - v.y ) < tolerance );
		}
		return false;
	}
	
	public Vector2 normalise() {
		return new Vector2( x / dist() , y / dist() );
	}
	
	@Override
	public String toString() {
		return "Vector2(" + x + " , " + y + ")";
	}
	
	@Override
	public Vector2 clone() {
		return new Vector2( this.x , this.y );
	}
}
