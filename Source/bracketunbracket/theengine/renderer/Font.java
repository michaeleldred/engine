/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michael Eldred
 */
public class Font {
	public final Map<Character , FontChar> characters = new HashMap<Character,FontChar>( 255 );
	public final String name;
	
	public static Font font;
	
	public final int size;
	
	/**
	 * 
	 * @param name
	 * @param size
	 */
	public Font( String name , int size ) {
		this.name = name;
		this.size = size;
	}
	
	public void add( char c , FontChar fontChar ) {
		characters.put( c , fontChar );
	}
	
	public FontChar get( char c ) {
		return characters.get( c );
	}
}
