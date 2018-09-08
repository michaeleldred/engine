/**
 * 
 */
package bracketunbracket.theengine.renderer;

import static viewcontrollers.GLES20.*;

/**
 * @author michaeleldred
 *
 */
public class IOSShader extends Shader {
	private String fragText;
	private String vertText;
	
	public int program = -1;

	public IOSShader( String vertText , String fragText ) {
		this.vertText = vertText;
		this.fragText = fragText;
	}
	
	@Override
	public void load( ) {
		int frag = loadShader( GL_FRAGMENT_SHADER , fragText );
		int vert = loadShader( GL_VERTEX_SHADER , vertText );
		
		program = glCreateProgram();
		
		glAttachShader( program , vert );
		glAttachShader( program , frag );
		
		glLinkProgram( program );
		
		if( program == 0 )
			System.out.println( "PROGRAM: " + glGetProgramInfoLog( program ) );
		else
			loaded = true;
	}
	
	private int loadShader( int type , String shaderCode ) {
		int shader = glCreateShader( type );
		
		glShaderSource( shader , shaderCode );
		glCompileShader( shader );
		
		
		
		if( shader != 0 )
			System.out.println( "Loaded shader: " + shader );
		else
			System.err.println( glGetShaderInfoLog( shader ) );
			
		return shader;
	}
}
