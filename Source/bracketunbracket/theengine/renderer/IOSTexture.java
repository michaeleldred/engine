/**
 * 
 */
package bracketunbracket.theengine.renderer;

import org.robovm.apple.coregraphics.CGImage;

import org.robovm.apple.glkit.GLKTextureInfo;
import org.robovm.apple.glkit.GLKTextureLoader;
import org.robovm.apple.glkit.GLKTextureLoaderOptions;
import org.robovm.apple.uikit.UIImage;

import static viewcontrollers.GLES20.*;

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
	
	public boolean isLoaded = false;
	
	public IOSTexture( String filename ) {
		// Loading code translated from 
		// https://www.raywenderlich.com/3047-opengl-es-2-0-for-iphone-tutorial-part-2-textures
		image = UIImage.getImage( "Images/" + filename ).getCGImage();
		
		width = (int) image.getWidth();
		height = (int) image.getHeight();
	}
	
	public void load() {
		try {
			// Need to have no errors here or else loadTexture crashes,
			// glGetError clears the log
			glGetError();
			GLKTextureLoaderOptions options = new GLKTextureLoaderOptions();
			options.setShouldApplyPremultiplication( false );
			//options.setSRGB( true );
			GLKTextureInfo texture = GLKTextureLoader.createTexture( image , options );
			texID = texture.getName();
			isLoaded = true;
			loaded();
		} catch( Exception exc ) {
			exc.printStackTrace();
		}
		
	}
	
	@Override
	public int getID() {
		return texID;
	}

}
