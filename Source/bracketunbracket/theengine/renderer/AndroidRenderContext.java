/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import bracketunbracket.theengine.math.Vector2;

import static android.opengl.GLES20.*;

/**
 * @author Michael Eldred
 */
public class AndroidRenderContext extends RenderContext {
	
	private List<RenderCommand> drawCommands = new ArrayList<RenderCommand>();
	private List<RenderCommand> addCommands = new ArrayList<RenderCommand>();
	
	private Map<String,AndroidTexture> textures = new HashMap<String, AndroidTexture>();
	
	private final Object lock = new Object();
	private final Object texLock = new Object();
	
	public final GLSurfaceView view;
	public final GLRenderer renderer;
	
	public AndroidRenderContext( Activity activity , GameWindow window ) {
		AndroidTexture.context = activity;
		renderer = new GLRenderer( this , window );
		
		view = new GLSurfaceView( activity );
		view.setEGLContextClientVersion( 2 );
		view.setEGLConfigChooser( false );
		view.setRenderer( renderer );
		view.setRenderMode( GLSurfaceView.RENDERMODE_CONTINUOUSLY );

		activity.setContentView( view );
		
	}
	
	public GameWindow getWindow() {
		return renderer.window;
	}
	@Override
	public void render() {
		synchronized ( lock ) {
			drawCommands = addCommands;
			addCommands = new ArrayList<RenderCommand>();
		}
		
		addCommands.clear();
	}
	
	List<RenderCommand> getCommands() {
		synchronized ( lock ) {
			return drawCommands;
		}
	}
	
	Map<String,AndroidTexture> getTextures() {
		synchronized( texLock ) {
			return textures;
		}
	}

	@Override
	public Texture create(String filename) {
		synchronized( texLock ) {
			if( textures.containsKey( filename ) ) {
				return textures.get( filename );
			}
			
			AndroidTexture t = new AndroidTexture( filename );
			textures.put( filename , t );
			return t;
		}
	}

	@Override
	public void execute(RenderCommand command) {
		synchronized( lock ) {
			addCommands.add( command );
		}
	}

	@Override
	public void setOffset(Vector2 offset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shader newShader( String vert, String frag ) {
		return new AndroidShader( vert , frag );
	}
	
}
class GLRenderer implements GLSurfaceView.Renderer {
	
	public final static String vertexShaderCode =
	        "attribute vec2 pos;\n" +
	        "attribute vec4 color;\n" +
	        "attribute vec2 texCoord;\n" +
	        "attribute vec3 rot;\n" +
	        "varying vec4 v_color;\n" +
	        "varying vec2 v_texCoord;\n" +
	        "uniform mat4 ortho;\n" +
	        "void main() {\n" +
	        "  vec2 npos = pos - rot.xy;\n" +
	        "  float s = sin( rot.z );\n" +
	        "  float c = cos( rot.z );\n" +
	        "  mat2 r = mat2( c , -s , s , c );\n" +
			"  npos = r * npos;\n" +
	        "  npos += rot.xy;\n" +
	        "  gl_Position = vec4( npos.xy , 0 , 1 ) * ortho;\n" +
	        "  v_color = color;\n" +
	        "  v_texCoord = texCoord;\n" +
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
		        "varying vec2 v_texCoord;\n" +
		        "varying vec4 v_color;\n" +
		        "void main() {\n" +
		        "  vec4 t = v_color;" +
		        "  gl_FragColor = t;\n" +
		        "}\n";

	private AndroidShader progTex = null;
	private AndroidShader progNoTex = null;
	private final AndroidRenderContext context;
	public GameWindow window;
	
	// Store the float buffers as they do not need to be recreated every frame
	FloatBuffer vertBuffer;
	FloatBuffer colorBuffer;
	FloatBuffer texBuffer;
	FloatBuffer rotBuffer;
	
	FrameTracker tracker = new FrameTracker( "RENDERFPS" );
	
	
	public GLRenderer( AndroidRenderContext context , GameWindow window ) {
		this.context = context;
		this.window = window;
	}
	
	public void onDrawFrame( GL10 gl ) {
		glClear( GL_COLOR_BUFFER_BIT );
		
		for( AndroidTexture t : context.getTextures().values() ) {
			if( !t.isLoaded() ) {
				t.load();
			}
		}
		
		List<RenderCommand> commands = context.getCommands();
		
		Vector2 dim = window.getScaledDimensions();
		float width = dim.x;
		float height = dim.y;
		
		float ortho[] = new float[ 16 ];
		Matrix.orthoM( ortho , 0 , -width/2, width/2 , height/2, -height/2, 1.0f, -1.0f );

		// Don't bother drawing if nothing to render
		if( commands.size() <= 0 )
			return;
		
		final int MAX_BATCH_SPRITES = RenderCommand.MAX_OBJECTS;
		
		float vertData[] = new float[ 12 * MAX_BATCH_SPRITES ];
		float colorData[] = new float[ 24 * MAX_BATCH_SPRITES ];
		float texData[] = new float[ 12 * MAX_BATCH_SPRITES ];
		float rotData[] = new float[ 18 * MAX_BATCH_SPRITES ];
		
		float w,h;
		
		for( RenderCommand command : commands ) {
			
			int vert = 0;
			int color = 0;
			int tex = 0;
			int rot = 0;
			
			for( RenderObject current : command.getObjects() ) {
				
				Vector2 pos = current.getAbsolutePosition();
				
				float r,g,b,a;
				a = current.color.alpha;
				
				r = current.color.red * a;
				g = current.color.green * a;
				b = current.color.blue * a;
				a = current.color.alpha;
				
				Image image = current.image;
				
				float rotation = (float)Math.toRadians( current.rotation );
				
				// Get the width and height of the new object
				if( image != null ) {
					w = image.w / 2.0f;
					h = image.h / 2.0f;
				} else {
					w = current.width / 2.0f;
					h = current.height / 2.0f;
				}
				
				// Set the scale
				w *= current.scale;
				h *= current.scale;
				
				vertData[ vert++ ] = pos.x - w; vertData[ vert++ ] = pos.y + h;
				vertData[ vert++ ] = pos.x - w; vertData[ vert++ ] = pos.y - h;
				vertData[ vert++ ] = pos.x + w; vertData[ vert++ ] = pos.y - h;
				
				vertData[ vert++ ] = pos.x - w; vertData[ vert++ ] = pos.y + h;
				vertData[ vert++ ] = pos.x + w; vertData[ vert++ ] = pos.y - h;
				vertData[ vert++ ] = pos.x + w; vertData[ vert++ ] = pos.y + h;
				
				colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
				colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
				colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
				
				colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
				colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
				colorData[ color++ ] = r;colorData[ color++ ] = g;colorData[ color++ ] = b;colorData[ color++ ] = a;
				
				rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
				rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
				rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
				
				rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
				rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
				rotData[ rot++ ] = pos.x; rotData[ rot++ ] = pos.y; rotData[ rot++ ] = rotation;
				
				if( image != null ) {
					
					float xOff = 0;// +( 1 / ( 2 * image.w  ) );
					float yOff = 0;// -( 1 / ( 2 * image.h ) );
					
					texData[ tex++ ] = image.x1 + xOff; texData[ tex++ ] = image.y2 + yOff;
					texData[ tex++ ] = image.x1 + xOff; texData[ tex++ ] = image.y1 + yOff;
					texData[ tex++ ] = image.x2 + xOff; texData[ tex++ ] = image.y1 + yOff;

					texData[ tex++ ] = image.x1 + xOff; texData[ tex++ ] = image.y2 + yOff;
					texData[ tex++ ] = image.x2 + xOff; texData[ tex++ ] = image.y1 + yOff;
					texData[ tex++ ] = image.x2 + xOff; texData[ tex++ ] = image.y2 + yOff;
				}
				
			}
			// Pass it to opengl
			
			AndroidShader prog = command.texture == null ? progNoTex : progTex;
			
			//vertBuffer.position( 0 );
			vertBuffer.put( vertData );
			vertBuffer.position( 0 );
			
			colorBuffer.put( colorData );
			colorBuffer.position( 0 );
			
			texBuffer.put( texData );
			texBuffer.position( 0 );
			
			rotBuffer.put( rotData );
			rotBuffer.position( 0 );
			
			if( command.texture != null ) {
				AndroidTexture t = (AndroidTexture)command.texture;
				
				glActiveTexture( GL_TEXTURE0 );
				glBindTexture( GL_TEXTURE_2D , t.texID );
			}
			
			if( command.shader != null ) {
				prog = (AndroidShader)command.shader;
			}
			
			if( !prog.loaded ) {
				prog.load();
				Log.d( "SHADER" , "LOADING" );
			}
			
			glUseProgram( prog.program );
			
			int loc = glGetUniformLocation( prog.program , "ortho" );
			glUniformMatrix4fv( loc , 1 , false , ortho , 0 );
			
			loc = glGetUniformLocation( prog.program , "screen" );
			glUniform2f( loc , width , height );
			
			
			int verthandle = glGetAttribLocation( prog.program , "pos" );
			glEnableVertexAttribArray( verthandle );
			glVertexAttribPointer( verthandle , 2 , GL_FLOAT , false , 8 , vertBuffer );
			
			int colorHandle = glGetAttribLocation( prog.program , "color" );
			glEnableVertexAttribArray( colorHandle );
			glVertexAttribPointer( colorHandle , 4 , GL_FLOAT , false , 16 , colorBuffer );
			
			int rotHandle = glGetAttribLocation( prog.program , "rot" );
			glEnableVertexAttribArray( rotHandle );
			glVertexAttribPointer( rotHandle , 3 , GL_FLOAT , false , 12 , rotBuffer );
			
			int texHandle = glGetAttribLocation( prog.program , "texCoord" );
			glEnableVertexAttribArray( texHandle );
			glVertexAttribPointer( texHandle , 2 , GL_FLOAT , false , 8 , texBuffer );
			
			int texLoc = glGetUniformLocation( prog.program , "img" );
			
			// Check to see if the location is found
			if( texLoc >= 0 )
				glUniform1i( texLoc , 0 );
			
			glDrawArrays( GL_TRIANGLES , 0 , command.getObjects().size() * 6 );
			
			glDisableVertexAttribArray( texHandle );
			glDisableVertexAttribArray( rotHandle );
			glDisableVertexAttribArray( colorHandle );
			glDisableVertexAttribArray( verthandle );
			
		} // End Command
		
		tracker.frame();
	}

	public void onSurfaceChanged( GL10 gl , int width , int height ) {
		Log.d( "POS" , "width: " + width );
		window.resize( width , height );
		Log.d( "POS" , "window: " + window.bounds );
	}

	public void onSurfaceCreated( GL10 gl , EGLConfig config ) {
		glClearColor( 0.5f , 0.8f , 1.0f , 1.0f );
		
		glEnable( GL_BLEND );
		glBlendFunc( GL_ONE , GL_ONE_MINUS_SRC_ALPHA );
		
		glEnable( GL_ALPHA );
		
		glDisable( GL_DEPTH_TEST );
		
		vertBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 12 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		colorBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 24 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		texBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 12 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		rotBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 18 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		
		progTex = new AndroidShader( vertexShaderCode , fragmentShaderCode );
		progNoTex = new AndroidShader( vertexShaderCode , fragmentNoTexShaderCode );
	}

}