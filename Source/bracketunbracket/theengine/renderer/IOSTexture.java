/**
 * 
 */
package bracketunbracket.theengine.renderer;

import org.robovm.apple.coregraphics.CGBitmapContext;
import org.robovm.apple.coregraphics.CGContext;
import org.robovm.apple.coregraphics.CGImage;
import org.robovm.apple.coregraphics.CGImageAlphaInfo;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.glkit.GLKTextureInfo;
import org.robovm.apple.glkit.GLKTextureLoader;
import org.robovm.apple.uikit.UIImage;

/**
 * @author michaeleldred
 *
 */
public class IOSTexture extends Texture {

	int texID = -1;
	// Probably release this memory
	public final int width;
	public final int height;
	private final CGImage image;
	
	public IOSTexture( String filename ) {
		// Loading code translated from 
		// https://www.raywenderlich.com/3047-opengl-es-2-0-for-iphone-tutorial-part-2-textures
		image = UIImage.getImage( filename ).getCGImage();
		
		width = (int) image.getWidth();
		height = (int) image.getHeight();
	}
	
	public void load() {
		try {
			GLKTextureInfo texture = GLKTextureLoader.createTexture( image , null );
			texID = texture.getName();
		} catch( Exception exc ) {
			exc.printStackTrace();
		}
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return texID;
	}

}
