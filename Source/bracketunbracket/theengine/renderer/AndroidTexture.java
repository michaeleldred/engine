/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * @author Michael Eldred
 */
public class AndroidTexture extends Texture {
	int texID;
	
	public static Activity context = null;
	private Bitmap bmp;
	public final float width;
	public final float height;
	private boolean isLoaded = false;
	
	private final static BitmapFactory.Options options = new BitmapFactory.Options();
	static {
		options.inScaled = false;
	}
	
	public AndroidTexture( String filename ) {
		InputStream is = null;
		try {
			 is = context.getAssets().open( "Images/" + filename );
		} catch (Exception exc ) {
			exc.printStackTrace();
		}
		
		Log.d( "TEXTURE", "LOADING:" + filename );
		
		bmp = BitmapFactory.decodeStream( is , null , options );
		width = bmp.getWidth();
		height = bmp.getHeight();
	}
	
	/**
	 * Transfer the file data to OpenGL should only be done in an OpenGL
	 * context
	 */
	public void load() {
		int t[] = new int[ 1 ];
		glGenTextures( 1 , t , 0 );
		texID = t[ 0 ];
		
		glEnable( GL_TEXTURE_2D );
		glBindTexture( GL_TEXTURE_2D , texID );
		
		glTexParameterf( GL_TEXTURE_2D , GL_TEXTURE_MAG_FILTER , GL_LINEAR );
		glTexParameterf( GL_TEXTURE_2D , GL_TEXTURE_MIN_FILTER , GL_LINEAR );
		
		GLUtils.texImage2D( GL_TEXTURE_2D , 0 , bmp, 0 );
		
		bmp.recycle();
		bmp = null;
		
		isLoaded = true;
		loaded();
	}
	
	public boolean isLoaded() {
		return isLoaded;
	}
	
	@Override
	public int getID() {
		return texID;
	}
	
}
