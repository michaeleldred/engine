package bracketunbracket.theengine.renderer;

import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.events.Event;
import org.teavm.jso.dom.events.EventListener;
import org.teavm.jso.dom.html.HTMLDocument;
import org.teavm.jso.dom.html.HTMLImageElement;
import org.teavm.jso.webgl.WebGLRenderingContext;
import org.teavm.jso.webgl.WebGLTexture;

import static org.teavm.jso.webgl.WebGLRenderingContext.*;

/**
 * @author Michael
 */
public class WebTexture implements Texture , EventListener<Event> {

	private WebGLRenderingContext gl;
	public WebGLTexture texture;
	private static int id;
	private int texID;
	private HTMLImageElement image;
	private HTMLDocument document; 
	
	public WebTexture( WebGLRenderingContext gl , String filename ) {
		System.out.println( "Loading: " + filename );
		this.gl = gl;
		texture = gl.createTexture();
		this.texID = id++;
		document = Window.current().getDocument();
		
		image = document.createElement( "img" ).cast();
		document.getBody().appendChild( image );
		image.getStyle().setProperty( "display" , "none" );
		image.setSrc( "Images/" + filename );
		image.addEventListener( "load" , this );
	}
	
	@Override
	public void handleEvent( Event evt ) {
		// Load the texture
		gl.bindTexture( TEXTURE_2D , texture );
		gl.texImage2D( TEXTURE_2D , 0 , RGBA , RGBA , UNSIGNED_BYTE , image );
		
		gl.texParameterf( TEXTURE_2D , TEXTURE_WRAP_S , CLAMP_TO_EDGE );
		gl.texParameterf( TEXTURE_2D , TEXTURE_WRAP_T , CLAMP_TO_EDGE );
		
		gl.texParameteri( TEXTURE_2D , TEXTURE_MAG_FILTER , LINEAR );
		gl.texParameteri( TEXTURE_2D , TEXTURE_MIN_FILTER , LINEAR );
	}
	
	public void load() {
		
	}
	@Override
	public int getID() {
		return texID;
	}

}

