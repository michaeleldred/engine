/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.event.EventListener;
import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.GameWindow;
import bracketunbracket.theengine.renderer.Image;
import bracketunbracket.theengine.renderer.RenderCommand;
import bracketunbracket.theengine.renderer.RenderContext;
import bracketunbracket.theengine.renderer.Shader;
import bracketunbracket.theengine.renderer.Texture;

/**
 * @author Michael Eldred
 */
public class MockRenderContext extends RenderContext {
	
	public final List<RenderCommand> commands = new ArrayList<RenderCommand>();
	
	public int imageGet = 0;

	@Override
	public void render() {
		
	}
	@Override
	public Texture create(String filename , EventListener listener ) {
		return null;
	}

	@Override
	public Image getImage(String name) {
		imageGet++;
		return super.getImage( name );
	}
	
	@Override
	public void execute(RenderCommand command) {
		commands.add( command );
	}
	@Override
	public void setOffset(Vector2 offset) {
	}
	@Override
	public Shader newShader(String frag, String vert) {
		return null;
	}
	@Override
	public void setGameWindow(GameWindow gameWin) {
		// TODO Auto-generated method stub
		
	}

}
