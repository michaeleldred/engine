package bracketunbracket.theengine.input;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.events.MouseEvent;
import org.teavm.jso.dom.html.HTMLDocument;

import bracketunbracket.theengine.event.EventManager;
import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.GameWindow;

/**
 * @author Michael
 */
public class WebInputManager {
	private static HTMLDocument document = Window.current().getDocument();
	private EventManager manager;
	private GameWindow gameWindow;
	public WebInputManager( EventManager manager , GameWindow window ) {
		this.manager = manager;
		this.gameWindow = window;
	}
	
	public void update() {
		document.getElementById( "game" ).addEventListener( "mousedown" , new EventListener<MouseEvent>() {
			@Override
			public void handleEvent(MouseEvent event) {
				
				int button = event.getButton();
				float x = event.getClientX() - gameWindow.getWidth() / 2.0f;
				float y = event.getClientY() - gameWindow.getHeight() / 2.0f;
				
				x /= gameWindow.getWidth() / 2.0f;
				y /= gameWindow.getHeight() / 2.0f;
				
				Vector2 v = gameWindow.getScaledDimensions();
				x *= ( v.x / 2.0f );
				y *= ( v.y / 2.0f );
				
				manager.sendEvent( new PointerEvent( button , x , y , true ) );
			}
		} );
		
		document.getElementById( "game" ).addEventListener( "mouseup" , new EventListener<MouseEvent>() {
			@Override
			public void handleEvent(MouseEvent event) {
				
				int button = event.getButton();
				float x = event.getClientX() - gameWindow.getWidth() / 2.0f;
				float y = event.getClientY() - gameWindow.getHeight() / 2.0f;
				
				x /= gameWindow.getWidth() / 2.0f;
				y /= gameWindow.getHeight() / 2.0f;
				
				Vector2 v = gameWindow.getScaledDimensions();
				x *= ( v.x / 2.0f );
				y *= ( v.y / 2.0f );
				
				manager.sendEvent( new PointerEvent( button , x , y , false ) );
			}
		} );
		
		document.getElementById( "game" ).addEventListener( "blur" , new EventListener<Event>() {
			@Override
			public void handleEvent(Event event) {
				manager.sendEvent( new WindowEvent( WindowEvent.WINDOW_UNFOCUSED ) );
			}
		} );
		
		document.getElementById( "game" ).addEventListener( "focus" , new EventListener<Event>() {
			@Override
			public void handleEvent(Event event) {
				manager.sendEvent( new WindowEvent( WindowEvent.WINDOW_FOCUSED ) );
			}
		} );
	}
}
