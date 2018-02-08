package bracketunbracket.theengine.renderer;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLCanvasElement;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.json.JSON;
import org.teavm.jso.typedarrays.Float32Array;
import org.teavm.jso.webgl.WebGLBuffer;
import org.teavm.jso.webgl.WebGLRenderingContext;
import org.teavm.jso.webgl.WebGLUniformLocation;

import static org.teavm.jso.webgl.WebGLRenderingContext.*;

import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.math.Vector2;

/**
 * @author Michael
 */
public class WebRenderContext extends RenderContext {
	
	private static HTMLDocument document = Window.current().getDocument();
	public WebGLRenderingContext gl;
	private GameWindow gameWindow;
	private Vector2 offset = new Vector2();
	
	private WebShader progNoTex;
	private WebShader progTex;
	
	// Vertex data stored here so it doesn't have to be recreated
	private Float32Array vertData = Float32Array.create( 12 * RenderCommand.MAX_OBJECTS );
	private Float32Array colorData = Float32Array.create( 24 * RenderCommand.MAX_OBJECTS );
	private Float32Array texData = Float32Array.create( 12 * RenderCommand.MAX_OBJECTS );
	private Float32Array rotData = Float32Array.create( 18 * RenderCommand.MAX_OBJECTS );
	
	private WebGLBuffer vertBuffer;
	private WebGLBuffer colorBuffer;
	private WebGLBuffer texBuffer;
	private WebGLBuffer rotBuffer;
	
	public WebRenderContext( float winwidth , float winheight ) {
		
		HTMLCanvasElement canvas = document.createElement( "canvas" ).cast();
		canvas.setAttribute( "style", "background: red;" );
		canvas.setAttribute( "width" , Integer.toString( (int)winwidth ) );
		canvas.setAttribute( "height" , Integer.toString( (int)winheight ) );
		canvas.setAttribute( "id" , "game" );
		
		document.getBody().appendChild( canvas );

		gl = (WebGLRenderingContext)canvas.getContext( "webgl" , JSON.parse( "{ \"alpha\": false , \"premultipliedAlpha\" : false }" ) );
		gl.viewport( 0 ,  0 , (int)winwidth , (int)winheight );
		gl.clearColor( 1.0f , 1.0f , 1.0f , 1.0f );
		gl.clear( COLOR_BUFFER_BIT );
		
		gl.colorMask( true , true , true , false );
		
		gl.enable( BLEND );
		gl.blendFunc( SRC_ALPHA , ONE_MINUS_SRC_ALPHA );
		
		gl.disable( DEPTH_TEST );
		
		progNoTex = new WebShader( gl ,  GLRenderer.vertexShaderCode , GLRenderer.fragmentNoTexShaderCode );
		progTex = new WebShader( gl , GLRenderer.vertexShaderCode , GLRenderer.fragmentShaderCode );
		
		progNoTex.load();
		progTex.load();
	}
	@Override
	public void render() {
		
	}

	@Override
	public void execute( RenderCommand command ) {
		
		// Don't render an empty batch
		if( command.getObjects().size() <= 0 ) {
			return;
		}
		
		float w,h;
		
		int vert = 0;
		int color = 0;
		int tex = 0;
		int rot = 0;
		
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
			
			rotData.set( rot++ , pos.x );rotData.set( rot++ , pos.y );rotData.set( rot++ , rotation );
			rotData.set( rot++ , pos.x );rotData.set( rot++ , pos.y );rotData.set( rot++ , rotation );
			rotData.set( rot++ , pos.x );rotData.set( rot++ , pos.y );rotData.set( rot++ , rotation );
			
			rotData.set( rot++ , pos.x );rotData.set( rot++ , pos.y );rotData.set( rot++ , rotation );
			rotData.set( rot++ , pos.x );rotData.set( rot++ , pos.y );rotData.set( rot++ , rotation );
			rotData.set( rot++ , pos.x );rotData.set( rot++ , pos.y );rotData.set( rot++ , rotation );
			
			if( image != null ) {
				texData.set( tex++ , image.x1 );texData.set( tex++ , image.y2 );
				texData.set( tex++ , image.x1 );texData.set( tex++ , image.y1 );
				texData.set( tex++ , image.x2 );texData.set( tex++ , image.y1 );
				
				texData.set( tex++ , image.x1 );texData.set( tex++ , image.y2 );
				texData.set( tex++ , image.x2 );texData.set( tex++ , image.y1 );
				texData.set( tex++ , image.x2 );texData.set( tex++ , image.y2 );
			}
		}
		
		// Actually draw
		
		// Load up buffers if they haven't been loaded already
		if( vertBuffer == null ) {
			vertBuffer = gl.createBuffer();
		}
		
		if( colorBuffer == null ) {
			colorBuffer = gl.createBuffer();
		}
		
		if( texBuffer == null ) {
			texBuffer = gl.createBuffer();
		}
		
		if( rotBuffer == null ) {
			rotBuffer = gl.createBuffer();
		}
		
		gl.bindBuffer( ARRAY_BUFFER , vertBuffer );
		gl.bufferData( ARRAY_BUFFER , vertData , STREAM_DRAW );
		
		gl.bindBuffer( ARRAY_BUFFER , colorBuffer );
		gl.bufferData( ARRAY_BUFFER , colorData , STREAM_DRAW );
		
		gl.bindBuffer( ARRAY_BUFFER , rotBuffer );
		gl.bufferData( ARRAY_BUFFER , rotData , STREAM_DRAW );
		
		gl.bindBuffer( ARRAY_BUFFER , texBuffer );
		gl.bufferData( ARRAY_BUFFER , texData , STREAM_DRAW );
		
		WebShader prog = progNoTex;
		
		if( command.texture != null ) {
			WebTexture texture = (WebTexture)command.texture;
			gl.activeTexture( TEXTURE0 );
			gl.bindTexture( TEXTURE_2D , texture.texture );
			prog = progTex;
			
		}
		if( command.shader != null ) {
			prog = (WebShader)command.shader;
		}
		
		gl.useProgram( prog.program );
		
		WebGLUniformLocation loc = gl.getUniformLocation( prog.program , "ortho" );
		gl.uniformMatrix4fv( loc , false , getOrtho() );
		
		WebGLUniformLocation texLoc = gl.getUniformLocation( prog.program , "img" );
		gl.uniform1i( texLoc , 0 );
		
		gl.bindBuffer( ARRAY_BUFFER , vertBuffer ); 
		int vertHandle = gl.getAttribLocation( prog.program , "pos" );
		gl.enableVertexAttribArray( vertHandle );
		gl.vertexAttribPointer( vertHandle , 2 , FLOAT , false , 0 , 0 );
		
		gl.bindBuffer( ARRAY_BUFFER , colorBuffer );
		int colorHandle = gl.getAttribLocation( prog.program , "color" );
		gl.enableVertexAttribArray( colorHandle );
		gl.vertexAttribPointer( colorHandle , 4 , FLOAT , false , 0 , 0 );
		
		gl.bindBuffer( ARRAY_BUFFER , texBuffer );
		int texHandle = gl.getAttribLocation( prog.program , "texCoord" );
		gl.enableVertexAttribArray( texHandle );
		gl.vertexAttribPointer( texHandle , 2 , FLOAT , false , 0 , 0 );
		
		gl.bindBuffer( ARRAY_BUFFER , rotBuffer );
		int rotHandle = gl.getAttribLocation( prog.program , "rot" );
		gl.enableVertexAttribArray( rotHandle );
		gl.vertexAttribPointer( rotHandle , 3 , FLOAT , false , 0 , 0 );
		
		gl.drawArrays( TRIANGLES , 0 , command.getObjects().size() * 6 );
		
		gl.disableVertexAttribArray( rotHandle );
		gl.disableVertexAttribArray( texHandle );
		gl.disableVertexAttribArray( colorHandle );
		gl.disableVertexAttribArray( vertHandle );
	}

	@Override
	public Texture create( String filename , EventListener listener ) {
		WebTexture t = new WebTexture( gl , filename );
		t.addEventListener( listener );
		return t;
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
