/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import static org.lwjgl.opengl.GL20.*;

/**
 * @author Michael Eldred
 */
public class LWJGLShader extends Shader {
	
	private final String vertText;
	private final String fragText;
	
	public int program = -1;
	
	public LWJGLShader( String vertText , String fragText ) {
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
		
		if( glGetProgrami( program , GL_LINK_STATUS ) == 0 )
			System.out.println( "ERROR: " + glGetProgramInfoLog( program ) );
		else
			loaded = true;
	}
	
	private int loadShader( int type , String shaderCode ) {
		int shader = glCreateShader( type );
		
		glShaderSource( shader , shaderCode );
		glCompileShader( shader );
		
		if( glGetShaderi( shader , GL_COMPILE_STATUS ) == 0 )
			System.out.println( "ERROR: " + glGetShaderInfoLog( shader ) );
		return shader;
	}
	
}
