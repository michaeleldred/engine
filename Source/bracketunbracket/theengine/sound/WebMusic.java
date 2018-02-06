package bracketunbracket.theengine.sound;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLAudioElement;
import org.teavm.jso.dom.html.HTMLDocument;

/**
 * @author Michael
 */
public class WebMusic extends Music implements EventListener<Event> {
	public final HTMLAudioElement audioElement;
	private static HTMLDocument document; 
	public WebMusic( String filename ) {
		
		document = Window.current().getDocument();
		
		audioElement = document.createElement( "audio" ).cast();
		audioElement.setSrc( "Sounds/" + filename );
		audioElement.setAttribute( "preload" , "auto" );
		audioElement.setAttribute( "controls" , "none" );
		audioElement.setAttribute( "style" , "display:none;" );
		document.getBody().appendChild( audioElement );
		
		audioElement.addEventListener( "loadeddata" , this );
	}
	@Override
	public void handleEvent(Event event) {
		loaded();
	}
}
