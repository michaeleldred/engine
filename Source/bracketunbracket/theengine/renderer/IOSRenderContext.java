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

		@Override
		public void draw(GLKView arg0, CGRect arg1) {
			glClearColor( 0.0f , 1.0f , 0.0f , 1.0f );
			glClear( GL_COLOR_BUFFER_BIT );
		}
		
	}
	
	public IOSRenderContext() {
		renderer = new Renderer();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOffset(Vector2 offset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Shader newShader(String vert, String frag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGameWindow(GameWindow gameWin) {
		this.gameWin = gameWin;
	}

}