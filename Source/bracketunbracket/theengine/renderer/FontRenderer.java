/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael Eldred
 */
public class FontRenderer {
	
	public final Font font;
	
	public FontRenderer( Font font ) {
		this.font = font;
	}
	
	public List<RenderObject> render( String string , Vector2 location ) {
		
		List<RenderObject> retVal = new ArrayList<RenderObject>();
		
		char[] chars = string.toCharArray();
		
		float advance = 0;
		// Get the total advance
		for( char c : chars ) {
			advance += font.get( c ).advance + 8;
		}
		
		float x = -advance / 2 + location.x;
		float y = font.size / 2 + location.y;
		
		for( int i = 0; i < chars.length; i++ ) {
			FontChar c = font.get( chars[ i ] );
			
			Vector2 loc = new Vector2( x - c.originX + ( c.width / 2 ) , y - ( c.originY / 2 ) );
			RenderObject obj = new RenderObject( loc , Color.WHITE , 0 , 0 , font.name + c.character , 11 , 0 );
			obj.effect = "font";
			
			retVal.add( obj );
			
			x += c.advance + 8;
		}
		
		return retVal;
	}
	
	public RenderObject getRenderObject( String string , Vector2 location ) {
		List<RenderObject> objs = render( string , new Vector2( 0 , 0 ) );
		RenderObject parent = new RenderObject( location , new Color( 1.0f , 1.0f , 1.0f , 1.0f ), 0 , 0 , 10 );
		
		for( RenderObject obj : objs ) {
			parent.addChild( obj );
		}
		
		return parent;
	}

	public void render( String string, Renderer renderer , Vector2 location ) {
		List<RenderObject> objs = render( string , new Vector2( 0 , 0 ) );
		RenderObject parent = new RenderObject( location , new Color( 1.0f , 1.0f , 1.0f , 1.0f ), 0 , 0 , 10 );
		
		for( RenderObject obj : objs ) {
			parent.addChild( obj );
		}
		
		renderer.add( parent );
	}
}
