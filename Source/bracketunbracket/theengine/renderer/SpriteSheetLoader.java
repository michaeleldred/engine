/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.io.InputStream;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import bracketunbracket.theengine.resources.FileLoader;
import bracketunbracket.theengine.resources.ResourceLoader;
import bracketunbracket.theengine.resources.ResourceManager;

/**
 * @author Michael Eldred
 */
public class SpriteSheetLoader implements ResourceLoader {
	
	private RenderContext context;
	
	public SpriteSheetLoader( RenderContext context ) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.resources.ResourceLoader#create(bracketunbracket.theengine.resources.ResourceManager, java.util.Map)
	 */
	@Override
	public void create( ResourceManager manager, Map<String, String> vals ) throws Exception {
		JSONObject root = null;
		
		// Load the text file from disk
		try {
			InputStream in = FileLoader.loadFilenameAsStream( "Images/" + vals.get( "filename" ) );
			root = new JSONObject( FileLoader.streamToString( in ) );
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		
		// Load the texture
		JSONObject meta = root.optJSONObject( "meta" );
		Texture tex = context.create( meta.optString( "image" , "" ) );
		
		float texW = (float) meta.getJSONObject( "size" ).getDouble( "w" );
		float texH = (float) meta.getJSONObject( "size" ).getDouble( "h" );
		
		// Go through each object and get the properties
		JSONArray images = root.optJSONArray( "frames" );
		
		for( int i = 0; i < images.length(); i++ ) {
			// For each object, add a new image to the manager
			JSONObject obj = (JSONObject) images.get( i );
			JSONObject frame = obj.getJSONObject( "frame" );
			
			String name = obj.optString( "filename" );
			
			float x1 = (float)frame.getDouble("x");
			float y1 = (float)frame.getDouble("y");
			
			float x2 = x1 + (float)frame.getDouble("w");
			float y2 = y1 + (float)frame.getDouble("h");
			
			float xOff = 0; //-( 1 / ( 2 * texW ) );
			float yOff = 0; //+( 1 / ( 2 * texH ) );
			Image im = new Image( tex , x1 / texW + xOff, x2 / texW  + xOff , y1 / texH + yOff, y2 / texH  + yOff , x2 - x1 , y2 - y1 );
			//System.out.println( "texCoords for " + name + " = ( " + x1 / texW + " , " + y1 / texH + " , " +
			//		+ x2 / texW + " , " + y2 / texH + " ) ");
			context.addImage( name , im );
		}
	}

}
