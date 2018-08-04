package bracketunbracket.theengine.renderer;

/**
 * @author Michael
 */
public abstract class Animation {
	
	public float length;
	public RenderObject source;
	
	public Animation( float length ) {
		this( null , length );
	}
	
	public Animation( RenderObject source , float length ) {
		this.length = length;
		this.source = source;
	}
	
	public void setSource( RenderObject source ) {
		this.source = source;
	}
	
	public abstract void update( float delta );
	public abstract boolean over();
}

abstract class Tween {
	public abstract float getValue( float start , float end , int tick , int length );
	
	public int getActualCurrent( int tick , int length ) {
		return tick - ( tick/length * length );
	}
}

class LinearTween extends Tween {
	public float getValue( float start , float end , int current , int length ) {
		int actualCurrent = getActualCurrent( current , length );
		return start + ( end - start ) * (float)( (float)actualCurrent/(float)length );
	}
}