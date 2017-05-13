package bracketunbracket.theengine.entity.components;

import bracketunbracket.theengine.entity.Component;

public class TilemapRenderComponent extends Component {
	public float transparency = 1.0f;
	public int layer = 0;
	
	public TilemapRenderComponent( int layer ) {
		this.layer = layer;
	}
}
