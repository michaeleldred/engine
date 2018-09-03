/**
 * 
 */
package bracketunbracket.theengine.renderer;

import org.robovm.apple.coreanimation.CATransaction;
import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.glkit.GLKView;
import org.robovm.apple.glkit.GLKViewDelegate;
import org.robovm.apple.opengles.EAGLContext;

import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.math.Vector2;

import static viewcontrollers.GLES20.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author michaeleldred
 *
 */
public class IOSRenderContext extends RenderContext {

	private GameWindow gameWin = new GameWindow(1.0f , 1.0f);
	public final Renderer renderer;
	
	private List<RenderCommand> drawCommands = new ArrayList<RenderCommand>();
	private List<RenderCommand> addCommands = new ArrayList<RenderCommand>();
	
	private Object lock = new Object();
	
	private class Renderer extends NSObject implements GLKViewDelegate {

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
		
		boolean first = true;
		public IOSTexture texture;
		@Override
		public void draw(GLKView arg0, CGRect arg1) {
			glClearColor( 0.0f , 1.0f , 0.0f , 1.0f );
			glClear( GL_COLOR_BUFFER_BIT );
			
			if( first ) {
				IOSShader shader = new IOSShader( vertexShaderCode , fragmentNoTexShaderCode );
				shader.load();
				
				texture.load();
				System.out.println( "Tex ID: " + texture.getID() );
				
				first = false;
				
			}
		}
		
	}
	
	public IOSRenderContext() {
		renderer = new Renderer();
		renderer.texture = new IOSTexture( "Images/spritesheet.png" );
	}
	
	@Override
	public void execute(RenderCommand command) {
		synchronized( lock ) {
			addCommands.add( command );
		}
	}
	
	@Override
	public void render() {
		synchronized ( lock ) {
			drawCommands = addCommands;
			addCommands = new ArrayList<RenderCommand>();
		}
		
		addCommands.clear();
	}

	@Override
	public Texture create(String filename, EventListener listener) {
		// TODO: Add to renderer to load
		
		IOSTexture t = new IOSTexture( filename );
		t.addEventListener( listener );
		
		return t;
	}

	@Override
	public void setOffset(Vector2 offset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shader newShader(String vert, String frag) {
		// TODO: Add to renderer to load
		return new IOSShader( vert , frag );
	}

	@Override
	public void setGameWindow(GameWindow gameWin) {
		this.gameWin = gameWin;
	}

}