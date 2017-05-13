/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

/**
 * @author Michael Eldred
 */
public class Image {
	public final Texture texture;
	public final float x1;
	public final float x2;
	public final float y1;
	public final float y2;
	
	public final float w;
	public final float h;
	
	public String name;
	
	public Image( Texture t , float x1 , float x2 , float y1 , float y2 , float w , float h ) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		this.w = w;
		this.h = h;
		
		this.texture = t;
	}
}
