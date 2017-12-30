package bracketunbracket.theengine.resources;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.renderer.Image;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.renderer.Texture;

/**
 * @author Michael
 */
public class SpriteSheetParser extends ResourceParser {
	
	private final RenderContext context;
	
	private class SpriteSheetResource extends Resource implements EventListener {

		public SpriteSheetResource( HashMap<String, String> values ) {
			super(values);
			
			String spritesheetName = values.get( "filename" );
			
			FileSystem.loadData( "Images/" + spritesheetName, this );
		}

		@Override
		public void receive(Event event) {
			Data data = null;
			
			if( event instanceof DataHandledEvent ) {
				data = ((DataHandledEvent)event).data;
			} else {
				return;
			}
			
			JSONObject root = new JSONObject( ((TextData)data).getData() );
			
			// Go through the file and load the texture
			String filename = root.getJSONObject( "meta" ).getString( "image" );
			// TODO: Add listener to this.
			Texture texture = context.create( filename );
			
			// Go through the images
			float w = root.getJSONObject( "meta" ).getJSONObject( "size" ).getInt( "w" );
			float h = root.getJSONObject( "meta" ).getJSONObject( "size" ).getInt( "h" );
			
			JSONArray images = root.getJSONArray( "frames" );
			for( int i = 0; i < images.length(); i++ ) {
				JSONObject currentImage = images.getJSONObject( i );
				JSONObject frame = currentImage.getJSONObject( "frame" );
				float x1 = (float)frame.getDouble( "x" );
				float y1 = (float)frame.getDouble( "y" );
				
				float x2 = x1 + (float)frame.getDouble( "w" );
				float y2 = y1 + (float)frame.getDouble( "h" );
				Image image = new Image( texture , x1 / w , x2 / w , y1 / h , y2 / h , x2 - x1 , y2 - y1 );
				
				context.addImage( currentImage.getString( "filename" ) , image);
			}
			
			finished();
		}
		
	}
	
	public SpriteSheetParser( RenderContext context ) {
		super( "spritesheet" );
		this.context = context;
	}
	
	public Resource load( HashMap< String , String > values ) {	
		return new SpriteSheetResource( values );
	}
}
