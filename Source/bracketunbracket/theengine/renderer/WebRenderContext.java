package bracketunbracket.theengine.renderer;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.webgl.WebGLBuffer;
import org.teavm.jso.webgl.WebGLRenderingContext;
import org.teavm.jso.webgl.WebGLShader;
import org.teavm.jso.webgl.WebGLUniformLocation;

import static org.teavm.jso.webgl.WebGLRenderingContext.*;
import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael
 */
public class WebRenderContext extends RenderContext {
	
	private static HTMLDocument document = Window.current().getDocument();
	private WebGLRenderingContext gl;
	private GameWindow gameWindow;
	private Vector2 offset = new Vector2();
	
	public final static String vertexShaderCode =
	        "attribute vec2 pos;\n" +
	        "attribute vec4 color;\n" +
	        "uniform mat4 ortho;\n" +
	        "varying vec4 v_color;\n" +
	        "void main() {\n" +
	        "  v_color = color;\n" +
	        "  gl_Position = vec4( pos , 0 , 1 ) * ortho;\n" +
	        "}\n";
	
	public final static String fragmentShaderCode =
			"#ifdef GL_ES\n" +
	        "  precision mediump float;\n" +
			"#endif\n" +
	        "uniform sampler2D img;\n" +
	        "varying vec2 v_texCoord;\n" +
	        "varying vec4 v_color;\n" +
	        "void main() {\n" +
	        "  gl_FragColor = texture2D( img , v_texCoord ) * v_color;\n" +
	        "}\n";
	
	    
	public final static String fragmentNoTexShaderCode =
	    		"#ifdef GL_ES\n" +
	    		"  precision mediump float;\n" +
	    		"#endif\n" +
	    		"varying vec4 v_color;\n" +
		        "void main() {\n" +
		        "  gl_FragColor = v_color;\n" +
		        "}\n";
	
	private WebShader progNoTex;
	
	public WebRenderContext( float winwidth , float winheight ) {
		
		HTMLCanvasElement canvas = document.createElement( "canvas" ).cast();
		canvas.setAttribute( "width" , Integer.toString( (int)winwidth ) );
		canvas.setAttribute( "height" , Integer.toString( (int)winheight ) );
		
		document.getBody().appendChild( canvas );
		gl = (WebGLRenderingContext)canvas.getContext( "webgl" );
		gl.viewport( 0 ,  0 , (int)winwidth , (int)winheight );
		gl.clearColor( 1.0f , 0.0f , 0.0f , 1.0f );
		gl.clear( COLOR_BUFFER_BIT );
		
		progNoTex = new WebShader( gl ,  vertexShaderCode , fragmentNoTexShaderCode );
		
		progNoTex.load();
	}
	@Override
	public void render() {
		//gl.clear( COLOR_BUFFER_BIT );
	}

	@Override
	public void execute( RenderCommand command ) {
		
		Float32Array vertData = Float32Array.create( 12 * RenderCommand.MAX_OBJECTS );
		Float32Array colorData = Float32Array.create( 24 * RenderCommand.MAX_OBJECTS );
		
		float w,h;
		
		int vert = 0;
		int color = 0;
		
		if( gameWindow == null ) {
			return;
		}
		
		for( int i = 0; i < command.getObjects().size(); i++ ) {
			RenderObject current = command.getObjects().get( i );
			
			Vector2 pos = current.getAbsolutePosition();
			
			float r,g,b,a;
			r = current.getColor().red;
			g = current.getColor().green;
			b = current.getColor().blue;
			a = current.getColor().alpha;
			
			Image image = current.image;
			
			float rotation = (float)Math.toRadians( current.rotation );
			
			// Get the width and height of the new object
			if( image != null ) {
				w = image.w / 2.0f * current.getScale();
				h = image.h / 2.0f * current.getScale();
			} else {
				w = current.getWidth() / 2.0f;
				h = current.getHeight() / 2.0f;
			}
			
			vertData.set( vert++ , pos.x - w );vertData.set( vert++ , pos.y + h );
			vertData.set( vert++ , pos.x - w );vertData.set( vert++ , pos.y - h );
			vertData.set( vert++ , pos.x + w );vertData.set( vert++ , pos.y - h );
			
			vertData.set( vert++ , pos.x - w );vertData.set( vert++ , pos.y + h );
			vertData.set( vert++ , pos.x + w );vertData.set( vert++ , pos.y - h );
			vertData.set( vert++ , pos.x + w );vertData.set( vert++ , pos.y + h );
			
			colorData.set( color++ , r );colorData.set( color++ , g );colorData.set( color++ , b );colorData.set( color++ , a );
			colorData.set( color++ , r );colorData.set( color++ , g );colorData.set( color++ , b );colorData.set( color++ , a );
			colorData.set( color++ , r );colorData.set( color++ , g );colorData.set( color++ , b );colorData.set( color++ , a );
			
			colorData.set( color++ , r );colorData.set( color++ , g );colorData.set( color++ , b );colorData.set( color++ , a );
			colorData.set( color++ , r );colorData.set( color++ , g );colorData.set( color++ , b );colorData.set( color++ , a );
			colorData.set( color++ , r );colorData.set( color++ , g );colorData.set( color++ , b );colorData.set( color++ , a );
		}
		
		// Actually draw
		WebGLBuffer vertBuffer = gl.createBuffer();
		gl.bindBuffer( ARRAY_BUFFER , vertBuffer );
		gl.bufferData( ARRAY_BUFFER , vertData , STATIC_DRAW );
		
		WebGLBuffer colorBuffer = gl.createBuffer();
		gl.bindBuffer( ARRAY_BUFFER , colorBuffer );
		gl.bufferData( ARRAY_BUFFER , colorData , STATIC_DRAW );
		
		WebShader prog = progNoTex;
		
		Vector2 dim = gameWindow.getScaledDimensions();
		float width = dim.x;
		float height = dim.y;
		
		gl.useProgram( prog.program );
		
		WebGLUniformLocation loc = gl.getUniformLocation( prog.program , "ortho" );
		gl.uniformMatrix4fv( loc , false , getOrtho() );
		
		gl.bindBuffer( ARRAY_BUFFER , vertBuffer ); 
		int vertHandle = gl.getAttribLocation( prog.program , "pos" );
		gl.enableVertexAttribArray( vertHandle );
		gl.vertexAttribPointer( vertHandle , 2 , FLOAT , false , 0 , 0 );
		
		gl.bindBuffer( ARRAY_BUFFER , colorBuffer );
		int colorHandle = gl.getAttribLocation( prog.program , "color" );
		gl.enableVertexAttribArray( colorHandle );
		gl.vertexAttribPointer( colorHandle , 4 , FLOAT , false , 0 , 0 );
		
		gl.drawArrays( TRIANGLES , 0 , command.getObjects().size() * 6 );
		
		gl.disableVertexAttribArray( colorHandle );
		gl.disableVertexAttribArray( vertHandle );
		
		//System.out.println( "NO ERROR: " + ( gl.getError() == NO_ERROR ) );
		
		/*WebShader prog = progNoTex;
		
		int vertPosLoc = gl.getAttribLocation( prog.program , "pos" );
		
		WebGLBuffer posBuffer = gl.createBuffer();
		
		gl.bindBuffer( ARRAY_BUFFER , posBuffer );
		
		Float32Array positions = Float32Array.create( 6 );
		int pos = 0;
		
		positions.set( pos++ , 0.0f );positions.set( pos++ , 0.0f );
		positions.set( pos++ , 0.0f );positions.set( pos++ , 50.0f );
		positions.set( pos++ , 70.0f );positions.set( pos++ , 0.0f );
		
		WebGLUniformLocation loc = gl.getUniformLocation( prog.program , "ortho" );
		
		gl.bufferData( ARRAY_BUFFER , positions , STATIC_DRAW ); 
		
		gl.useProgram( prog.program );
		gl.bindBuffer( ARRAY_BUFFER , posBuffer );
		gl.enableVertexAttribArray( vertPosLoc );
		gl.uniformMatrix4fv( loc , false , getOrtho() );
		
		gl.vertexAttribPointer( vertPosLoc , 2 , FLOAT , false , 0 , 0 );
		
		gl.drawArrays( TRIANGLES , 0 , 3 );*/
		
	}

	@Override
	public Texture create( String filename ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOffset( Vector2 offset ) {
		this.offset.set( offset );
	}

	@Override
	public Shader newShader( String vert, String frag ) {
		return new WebShader( gl , vert , frag );
	}

	@Override
	public void setGameWindow(GameWindow gameWin) {
		this.gameWindow = gameWin;
	}
	
	public float[] getOrtho() {
		
		Vector2 dim = gameWindow.getScaledDimensions();
		float zFar = -1.0f;
		float zNear = 1.0f;
		
		float[] ortho = new float[ 16 ];
		float ri = dim.x / 2.0f + offset.x;
		float le = -dim.x / 2.0f - offset.x;
		
		float t = dim.y / 2.0f + offset.y;
		float b = -dim.y / 2.0f - offset.y;
		
		ortho[ 0 ] = 2.0f / ( ri - le );
		ortho[ 3 ] = -( ri + le ) / ( ri - le );
		ortho[ 5 ] = -2.0f / ( t - b ) ;
		ortho[ 7 ] = - ( t + b ) / ( t - b );
		ortho[ 10 ] = 2.0f / ( zFar - zNear );
		ortho[ 11 ] = ( zNear + zFar ) / ( zNear - zFar );
		ortho[ 15 ] = 1.0f;
		
		return ortho;
	}

}
