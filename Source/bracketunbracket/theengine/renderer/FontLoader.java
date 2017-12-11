package bracketunbracket.theengine.renderer;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONObject;

import bracketunbracket.theengine.resources.FileLoader;
import bracketunbracket.theengine.resources.ResourceLoader;
import bracketunbracket.theengine.resources.ResourceManager;

/*
 * Soli Deo gloria
 */

/**
 * @author Michael Eldred
 */
public class FontLoader implements ResourceLoader {
	
	private final RenderContext context;
	
	public FontLoader( RenderContext context ) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.resources.ResourceLoader#create(bracketunbracket.theengine.resources.ResourceManager, java.util.Map)
	 */
	@Override
	public void create(ResourceManager manager, Map<String, String> vals) throws Exception {
		
		JSONObject root = null;
		try {
			InputStream in = FileLoader.loadFilenameAsStream( "Fonts/" + vals.get( "filename" ) );
			root = new JSONObject( FileLoader.streamToString( in ) );
		} catch( Exception exc ) {
			exc.printStackTrace();
			return;
		}
		
		String filename = root.getString( "file" );
		Texture tex = context.create( filename );
		
		float width = (float) root.getDouble( "width" );
		float height = (float) root.getDouble( "height" );
		String name = root.getString( "name" );
		
		// Get a list of all the characters included in the font
		JSONObject characters = root.getJSONObject( "characters" );
		
		JSONObject current;
		Image im;
		FontChar fontchar;
		Font retVal = new Font( name , root.getInt( "size" ) );
		for( Iterator<String> it = characters.keys(); it.hasNext(); ) {
			String c = it.next(); 
			// Get the object
			char actual = c.charAt( 0 );
			current = characters.getJSONObject( c );
			
			// Get the coordinates
			float x1 = (float)current.getDouble( "x" );
			float y1 = (float)current.getDouble( "y" );
			float x2 = x1 + (float)current.getDouble( "width" );
			float y2 = y1 + (float)current.getDouble( "height" );
			
			// Store it in an image
			im = new Image( tex , x1 / width , x2 / width , y1 / height , y2 / height , x2 - x1 , y2 - y1 );
			
			// Add extra information for drawing
			float originX = (float)current.getDouble( "originX" );
			float originY = (float)current.getDouble( "originY" );
			float advance = (float)current.getDouble( "advance" );
			
			context.addImage( name + c , im );
			fontchar = new FontChar( actual , "name" + c , originX , originY , advance , x2 - x1 , y2 - y1 ); 
			
			// Add the character to the font
			retVal.add( actual , fontchar );
		}
		
		Font.font = retVal;
	}

}
