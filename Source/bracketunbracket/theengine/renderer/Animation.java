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
