package bracketunbracket.theengine.renderer;

import org.teavm.jso.webgl.WebGLRenderingContext;
import org.teavm.jso.webgl.WebGLShader;

import static org.teavm.jso.webgl.WebGLRenderingContext.*;

import org.teavm.jso.webgl.WebGLProgram;

/**
 * @author Michael
 */
public class WebShader extends Shader {

	private WebGLRenderingContext gl;
	public WebGLProgram program;
	private String vertText;
	private String fragText;
	
	public WebShader( WebGLRenderingContext gl , String vert , String frag ) {
		this.gl = gl;
		this.vertText = vert;
		this.fragText = frag;
	}
	
	@Override
	public void load() {
		WebGLShader frag = loadShader( FRAGMENT_SHADER , fragText );
		WebGLShader vert = loadShader( VERTEX_SHADER , vertText );
		
		program = gl.createProgram();
		gl.attachShader( program , vert );
		gl.attachShader( program , frag );
		
		gl.linkProgram( program );
		
		if( !gl.getProgramParameterb( program , LINK_STATUS ) ) {
			System.out.println( "LINK ERROR: " + gl.getProgramInfoLog( program ) );
		} else {
			loaded = true;
		}
	}
	
	private WebGLShader loadShader( int type , String shaderCode ) {
		WebGLShader shader = gl.createShader( type );
		gl.shaderSource( shader , shaderCode );
		gl.compileShader( shader );
		
		if( !gl.getShaderParameterb( shader , COMPILE_STATUS ) ) {
			System.out.println( "COMPILE ERROR: " + gl.getShaderInfoLog( shader ) );
			System.out.println( shaderCode );
		} 
		
		return shader;
	}

}
