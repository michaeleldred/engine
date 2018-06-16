package bracketunbracket.theengine.sound;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLAudioElement;
import org.teavm.jso.dom.html.HTMLDocument;

/**
 * @author Michael
 */
public class WebSound extends Sound implements EventListener<Event> {
	
	
	public final HTMLAudioElement audioElement;
	private static HTMLDocument document; 
	public WebSound( String filename , bracketunbracket.theengine.event.EventListener listener ) {
		addEventListener( listener );
		
		document = Window.current().getDocument();
		
		audioElement = document.createElement( "audio" ).cast();
		audioElement.setSrc( "Sounds/" + filename );
		audioElement.setAttribute( "preload" , "auto" );
		audioElement.setAttribute( "controls" , "none" );
		audioElement.setAttribute( "style" , "display:none;" );
		document.getBody().appendChild( audioElement );
		
		audioElement.addEventListener( "loadeddata" , this );
		// TODO: Safari doesn't load ogg's, set errors to load
		audioElement.addEventListener( "error" , this );
	}
	@Override
	public void handleEvent(Event event) {
		loaded();
	}
}
