package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.renderer.Color;

public class TilemapRenderComponent extends RenderComponent {
	
	public TilemapRenderComponent( int layer ) {
		this( layer , new Color( 1.0f , 1.0f , 1.0f ) );
	}
	
	public TilemapRenderComponent( int layer , Color color  ) {
		super( color , 0 , 0 , layer );
	}
}
