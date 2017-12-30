package bracketunbracket.theengine;

import bracketunbracket.theengine.event.EventManager;
import bracketunbracket.theengine.platform.Platform;
import bracketunbracket.theengine.renderer.GameWindow;
import bracketunbracket.theengine.renderer.Renderer;
import bracketunbracket.theengine.sound.AudioEngine;
import bracketunbracket.theengine.sound.LuaScriptRunner;

/**
 * @author Michael
 */
public class GameEngine {
	public final GameWindow window;
	public final EventManager eventManager = new EventManager();
	private final Platform platform;
	
	public GameEngine( Platform platform ) throws Exception {
		
		this.platform = platform;
		// Set up audio
		/*LuaScriptRunner runner = new LuaScriptRunner();
		AudioEngine engine = new AudioEngine( platform.getAudioContext() , runner );
		runner.addObject( "engine" , engine );
		runner.addLib( "Sounds/sounds.lua" );
		eventManager.addListener( engine );*/
		
		// Setup the Game Window
		window = new GameWindow( 800 , 600 );
		window.renderContext = platform.getRenderContext();
		window.renderer = new Renderer( platform.getRenderContext() );
		//window.audioEngine = engine;
		window.renderContext.setGameWindow( window );
		eventManager.addListener( window.screenManager );
		
		//parser.addLoader( "font" , new FontLoader( window.renderContext ) );
		
		/*parser.addLoader( "sound" , new SoundLoader( window.audioEngine ) );
		parser.addLoader( "soundevent" , new SoundLoader( window.audioEngine ) );
		parser.addLoader( "music" , new SoundLoader( window.audioEngine ) );*/
		
		//resourceManager = new ResourceManager( parser );
	}
	
	public void update() {
		window.screenManager.update();
		window.renderer.render();
		platform.getRenderContext().render();
	}
	
	public void destroy() {
		window.screenManager.destroy();
		//platform.getAudioContext().destroy();
		//platform.getRenderContext().destroy();
	}
}
