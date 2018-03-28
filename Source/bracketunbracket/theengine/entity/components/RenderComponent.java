package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;
import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.Color;
import bracketunbracket.theengine.renderer.RenderObject;

public class RenderComponent extends Component {
	public RenderObject obj = null;
	
	public RenderComponent( RenderObject obj ) {
		this.obj = obj;
	}
	
	public RenderComponent( Color color , float width , float height ) {
		this( color , width , height , 0 );
	}
	
	public RenderComponent( Color color , float width , float height , int layer ) {
		this( new RenderObject( new Vector2() , color , width , height , layer ) );
	}
	
	public RenderComponent( String texture  ) {
		this( texture , 0 , 0 , 0 );
	}
	
	public RenderComponent( String texture , float width , float height ) {
		this( texture , width , height , 0 );
	}
	
	public RenderComponent( String texture , float width , float height , int layer  ) {
		this( texture , width , height , layer , new Color( 1.0f , 1.0f , 1.0f , 1.0f ) );
	}
	
	public RenderComponent( String texture , float width , float height , int layer , Color color ) {
		this( new RenderObject( null , color , width , height , texture , layer , 0.0f  ) );
	}
}
