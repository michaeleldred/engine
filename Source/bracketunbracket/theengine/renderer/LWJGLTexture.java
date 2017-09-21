/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import bracketunbracket.theengine.resources.FileLoader;
/**
 * @author Michael Eldred
 */
public class LWJGLTexture implements Texture {
	
	public final float width;
	public final float height;
	public final int comp;
	int texID;
	
	private ByteBuffer image;
	
	public LWJGLTexture( String filename ) {
		ByteBuffer data = null;
		try {
			data = FileLoader.loadFilenameAsBuffer( "Images/" + filename );
		} catch( Exception exc ) {
			exc.printStackTrace();
		}
		
		IntBuffer w = BufferUtils.createIntBuffer( 1 );
		IntBuffer h = BufferUtils.createIntBuffer( 1 );
		IntBuffer c = BufferUtils.createIntBuffer( 1 );
		
		image = stbi_load_from_memory( data , w , h , c , 0);
		if( image == null ) {
			throw new RuntimeException( "Could not load: " + filename );
		}
		
		this.width = w.get( 0 );
		this.height = h.get( 0 );
		comp = c.get( 0 );
	}
	
	public void load() {
		
		// Pass the buffer to OpenGL
		glEnable( GL_TEXTURE_2D );
		texID = glGenTextures();
		glBindTexture( GL_TEXTURE_2D , texID );
		
		if( comp == 3 ) {
			glTexImage2D( GL_TEXTURE_2D , 0 , GL_RGB , (int)width , (int)height , 0 , GL_RGB , GL_UNSIGNED_BYTE , image );
		} else {
			glTexImage2D( GL_TEXTURE_2D , 0 , GL_RGBA , (int)width , (int)height , 0 , GL_RGBA , GL_UNSIGNED_BYTE , image );
		}
		
		glTexParameteri( GL_TEXTURE_2D , GL_TEXTURE_MAG_FILTER , GL_LINEAR );
		glTexParameteri( GL_TEXTURE_2D , GL_TEXTURE_MIN_FILTER , GL_LINEAR );
		
		glDisable( GL_TEXTURE_2D );
		
		image = null;
	}

	@Override
	public int getID() {
		return texID;
	}
}
