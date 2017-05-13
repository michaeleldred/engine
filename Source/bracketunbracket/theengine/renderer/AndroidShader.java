/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import static android.opengl.GLES20.*;

import android.util.Log;

/**
 * @author Michael Eldred
 */
public class AndroidShader extends Shader {
	private final String vertText;
	private final String fragText;
	
	public int program = -1;
	
	public AndroidShader( String vertText , String fragText ) {
		this.vertText = vertText;
		this.fragText = fragText;
	}
	
	@Override
	public void load() {
		int frag = loadShader( GL_FRAGMENT_SHADER , fragText );
		int vert = loadShader( GL_VERTEX_SHADER , vertText );
		
		program = glCreateProgram();
		
		glAttachShader( program , vert );
		glAttachShader( program , frag );
		
		glLinkProgram( program );
		int[] error = new int[ 1 ];
		
		glGetProgramiv( program , GL_LINK_STATUS , error , 0 );
		
		if( error[ 0 ] == 0 ) {
			Log.d( "SHADER" , "ERROR: " + glGetProgramInfoLog( program ) );
		} else {
			loaded = true;
		}
	}
	
	private int loadShader( int type , String shaderCode ) {
		// create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = glCreateShader(type);

	    // add the source code to the shader and compile it
	    glShaderSource(shader, shaderCode);
	    glCompileShader(shader);
	    
	    int error[] = new int[ 1 ];
	    glGetShaderiv( shader , GL_COMPILE_STATUS, error , 0 );
	    
	    if( error[ 0 ] == 0 ) {
	    	Log.d( "SHADER", "ERROR: " + glGetShaderInfoLog( shader ) );
	    }

	    return shader;
	}
}
