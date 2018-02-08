package bracketunbracket.theengine.resources;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.renderer.Image;
import bracketunbracket.theengine.renderer.Font;
import bracketunbracket.theengine.renderer.FontChar;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.renderer.Texture;

/**
 * @author Michael
 */
public class FontParser extends ResourceParser {

	private RenderContext renderContext;
	
	private class FontResource extends Resource implements EventListener {

		private String fontData;
		private Texture fontTexture;
		
		public FontResource( HashMap<String, String> values , ResourceManager manager ) {
			super(values , resourceManager );
			FileSystem.loadData( "Fonts/" + values.get( "filename" ) , this );
		}

		@Override
		public void receive(Event event) {
			Data data = null;
			
			if( event instanceof DataHandledEvent ) {
				data = ((DataHandledEvent)event).data;
			} else {
				// Loaded the font texture
				finished();
				return;
			}
			
			// If the data is text
			if( data instanceof TextData ) {
				fontData = ((TextData) data).getData();
			}
			
			
			JSONObject root = new JSONObject( fontData );
			fontTexture = renderContext.create( root.getString( "file" ) , this );
			
			float width = (float)root.getDouble( "width" );
			float height = (float)root.getDouble( "height" );
			String name = root.getString( "name" );
			
			JSONObject characters = root.getJSONObject( "characters" );
			
			JSONObject current;
			Image im;
			FontChar fontchar;
			Font retVal = new Font( name , root.getInt( "size" ) );
			
			for( Iterator<String> it = characters.keys(); it.hasNext(); ) {
				String c = it.next();
				char actual = c.charAt( 0 );
				current = characters.getJSONObject( c );
				
				float x1 = (float)current.getDouble( "x" );
				float y1 = (float)current.getDouble( "y" );
				
				float x2 = x1 + (float)current.getDouble( "width" );
				float y2 = y1 + (float)current.getDouble( "height" );
				
				im = new Image( fontTexture , x1 / width , x2 / width , y1 / height , y2 / height , x2 - x1 , y2 - y1 );
				
				float originX = (float)current.getDouble( "originX" );
				float originY = (float)current.getDouble( "originY" );
				float advance = (float)current.getDouble( "advance" );
				
				renderContext.addImage( name + c , im );
				fontchar = new FontChar( actual , "name" + c , originX , originY , advance , x2 - x1 , y2 - y1 );
				
				retVal.add( actual , fontchar );
			}
			
			Font.font = retVal;
			
			//finished();
		}
		
	}
	
	public FontParser( RenderContext context ) {
		super( "font" );
		this.renderContext = context;
	}
	
	@Override
	public Resource load(HashMap<String, String> values) {
		return new FontResource( values , resourceManager );
	}

}
