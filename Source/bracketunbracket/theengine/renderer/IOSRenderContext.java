/**
 * 
 */
package bracketunbracket.theengine.renderer;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.glkit.GLKView;
import org.robovm.apple.glkit.GLKViewDelegate;

import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.math.Vector2;

import static viewcontrollers.GLES20.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author michaeleldred
 *
 */
public class IOSRenderContext extends RenderContext {

	private GameWindow gameWin = new GameWindow(1.0f , 1.0f);
	public final IOSRenderer renderer;

	private List<RenderCommand> addCommands = new ArrayList<RenderCommand>();
	private List<RenderCommand> contextCommands = new ArrayList<RenderCommand>();
	
	private Map<String,IOSTexture> textures = new HashMap<String,IOSTexture>();
	
	private Object lock = new Object();
	private Object texLock = new Object();
	
	public IOSRenderContext() {
		renderer = new IOSRenderer( this , contextCommands );
	}
	
	@Override
	public void execute(RenderCommand command) {
		synchronized( lock ) {
			addCommands.add( command );
		}
	}
	
	@Override
	public void render() {
		synchronized( lock ) {
			contextCommands.clear();
			contextCommands.addAll( addCommands );
			
			addCommands.clear();
		}
	}

	@Override
	public Texture create(String filename, EventListener listener) {
		if( textures.containsKey( filename ) ) {
			return textures.get( filename );
		}
		IOSTexture t = new IOSTexture( filename );
		t.addEventListener( listener );
		
		synchronized( texLock ) {
			textures.put( filename , t );
		}
		return t;
	}

	@Override
	public void setOffset(Vector2 offset) {
	}

	@Override
	public Shader newShader(String vert, String frag) {
		return new IOSShader( vert , frag );
	}

	@Override
	public void setGameWindow(GameWindow gameWin) {
		this.gameWin = gameWin;
		renderer.setGameWindow( gameWin );
	}
	
	public List<RenderCommand> getCommands() {
		System.out.println( "ID: " + System.identityHashCode( contextCommands ) );
		System.out.println( "Getting: " + contextCommands.size() + " commands" );
		return contextCommands;
	}
	
	public Map<String , IOSTexture> getTextures() {
		synchronized ( texLock ) {
			return textures;
		}
	}

}

class IOSRenderer extends NSObject implements GLKViewDelegate {

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
	
	     
	public IOSRenderContext context;
	
	private IOSShader progTex = null;
	private IOSShader progNoTex = null;
	
	private FloatBuffer vertBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer texBuffer;
	private FloatBuffer rotBuffer;
	private GameWindow gameWindow = new GameWindow( 1.0f , 1.0f );
	
	private List<RenderCommand> commands;
	
	public IOSRenderer( IOSRenderContext context , List<RenderCommand> commands ) {
		vertBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 12 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		colorBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 24 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		texBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 12 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		rotBuffer = ByteBuffer.allocateDirect( RenderCommand.MAX_OBJECTS * 18 * 4 ).order( ByteOrder.nativeOrder() ).asFloatBuffer();
		this.context = context;
		this.commands = commands;
	}
	
	public void setGameWindow( GameWindow window ) {
		this.gameWindow = window;
	}
	
	@Override
	public void draw(GLKView arg0, CGRect rect ) {
		gameWindow.resize( (float)rect.getWidth() , (float)rect.getHeight() );
		if( progTex == null ) {
			progTex = new IOSShader( vertexShaderCode , fragmentShaderCode );
			progNoTex = new IOSShader( vertexShaderCode , fragmentNoTexShaderCode );
		}
		
		glDisable( GL_DEPTH_TEST );
		glClearColor( 0.5f , 0.8f , 1.0f , 1.0f );
		glEnable( GL_BLEND );
		glBlendFunc( GL_SRC_ALPHA , GL_ONE_MINUS_SRC_ALPHA );
		glEnable( GL_ALPHA );
		
		glClear( GL_COLOR_BUFFER_BIT );
		// Load textures in thread
		for( IOSTexture t : context.getTextures().values() ) {
			if( !t.isLoaded ) {
				t.load();
			}
		}
		
		Vector2 dim = gameWindow.getScaledDimensions();
		float width = dim.x;
		float height = dim.y;
		
		float ortho[] = getOrtho();
		
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
				a = current.getColor().alpha;
				
				r = current.getColor().red * a;
				g = current.getColor().green * a;
				b = current.getColor().blue * a;
				
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
			
			IOSShader prog = command.texture == null ? progNoTex : progTex;
			
			vertBuffer.put( vertData );
			vertBuffer.position( 0 );
			
			colorBuffer.put( colorData );
			colorBuffer.position( 0 );
			
			texBuffer.put( texData );
			texBuffer.position( 0 );
			
			rotBuffer.put( rotData );
			rotBuffer.position( 0 );
			
			if( command.texture != null ) {
				IOSTexture t = (IOSTexture) command.texture;
				glActiveTexture( GL_TEXTURE0 );
				glBindTexture( GL_TEXTURE_2D , t.texID );
			}
			
			if( command.shader != null ) {
				prog = (IOSShader)command.shader;
			}
			
			if( !prog.loaded ) {
				prog.load();
			}
			
			glUseProgram( prog.program );
			
			int loc = glGetUniformLocation( prog.program , "ortho" );
			glUniformMatrix4fv( loc , 1 , false , ortho);
			
			loc = glGetUniformLocation( prog.program , "screen" );
			glUniform2f( loc , width , height );
			
			int verthandle = glGetAttribLocation( prog.program , "pos" );
			glEnableVertexAttribArray( verthandle );
			glVertexAttribPointer( verthandle , 2 , GL_FLOAT , false , 8 , vertBuffer );
			
			int colorhandle = glGetAttribLocation( prog.program , "color" );
			glEnableVertexAttribArray( colorhandle );
			glVertexAttribPointer( colorhandle , 4 , GL_FLOAT , false , 16 , colorBuffer );
			
			int rotHandle = glGetAttribLocation( prog.program , "rot" );
			glEnableVertexAttribArray( rotHandle );
			glVertexAttribPointer( rotHandle , 3 , GL_FLOAT , false , 12 , rotBuffer );
			
			int texHandle = glGetAttribLocation( prog.program , "texCoord" );
			glEnableVertexAttribArray( texHandle );
			glVertexAttribPointer( texHandle , 2 , GL_FLOAT , false , 8 , texBuffer );
			
			int texLoc = glGetUniformLocation( prog.program , "img" );
			
			if( texLoc >= 0 ) {
				glUniform1i( texLoc , 0 );
			}

			glDrawArrays( GL_TRIANGLES , 0 , command.getObjects().size() * 6 );
			
			glDisableVertexAttribArray( texHandle );
			glDisableVertexAttribArray( rotHandle );
			glDisableVertexAttribArray( colorhandle );
			glDisableVertexAttribArray( verthandle );
			
		} // End command
		
		
		
	}
	
	public float[] getOrtho() {
		
		Vector2 dim = gameWindow.getScaledDimensions();
		float zFar = -1.0f;
		float zNear = 1.0f;
		
		float[] ortho = new float[ 16 ];
		float ri = dim.x / 2.0f;// + offset.x;
		float le = -dim.x / 2.0f;// - offset.x;
		
		float t = dim.y / 2.0f;// + offset.y;
		float b = -dim.y / 2.0f;// - offset.y;
		
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