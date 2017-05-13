/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

/**
 * @author Michael Eldred
 */
public class FontChar {
	public final char character;
	public final String imagename;
	public final float originX;
	public final float originY;
	public final float advance;
	public final float width;
	public final float height;
	
	public FontChar( char character , String image , float originX , float originY , float advance , float width , float height ) {
		this.character = character;
		this.imagename = image;
		this.originX = originX;
		this.originY = originY;
		this.advance = advance;
		this.width = width;
		this.height = height;
	}
}
