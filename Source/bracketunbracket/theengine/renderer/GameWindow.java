/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import bracketunbracket.theengine.math.Rectangle;
import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.sound.AudioEngine;
import bracketunbracket.theengine.ui.ScreenManager;

/**
 * @author Michael Eldred
 */
public class GameWindow {
	
	/**
	 * The actual width of the platforms display area
	 */
	private float width;
	
	/**
	 * The actual height of the platforms display area
	 */
	private float height;
	
	/**
	 * The desired width of the game
	 */
	private float gameHeight;
	
	/**
	 * The desired height of the game
	 */
	private float gameWidth;
	
	public RenderContext renderContext;
	
	public Renderer renderer;
	
	public AudioEngine audioEngine;
	
	public ScreenManager screenManager;
	
	public final Rectangle bounds = new Rectangle( 0 , 0 );
	
	public GameWindow() {
		this( 0 , 0 );
	}
	/**
	 * Creates a game window of a specific size, does not attempt to resize it at all
	 * 
	 * @param width  The actual width of the platforms display area
	 * @param height The actual height of the platforms display area
	 */
	public GameWindow( float width , float height ) {
		this( width , height , width , height );
	}
	
	/**
	 * Creates a game window that fits within the game size
	 * 
	 * @param width  The actual width of the platforms display area
	 * @param height The actual height of the platforms display area
	 */
	public GameWindow( float width , float height , float gameWidth , float gameHeight ) {
		this.width = width;
		this.height = height;
		this.gameWidth = gameWidth;
		this.gameHeight = gameHeight;
		 
		this.bounds.w = getScaledDimensions().x;
		this.bounds.h = getScaledDimensions().y;
		
		this.bounds.x = -this.bounds.w / 2;
		this.bounds.y = -this.bounds.h / 2;
		
		this.screenManager = new ScreenManager( this );
	}
	
	public void resize( float newWidth , float newHeight ) {
		this.width = newWidth;
		this.height = newHeight;
		
		Vector2 newDim = getScaledDimensions();
		
		this.bounds.w = newDim.x;
		this.bounds.h = newDim.y;
		
		this.bounds.x = -this.bounds.w / 2;
		this.bounds.y = -this.bounds.h / 2;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	/**
	 * Gets a set of dimensions that matches the original coordinates, but
	 * changes one of them to match the windows aspect ratio.
	 * 
	 * @return
	 */
	public Vector2 getScaledDimensions() {
		
		float w = gameWidth;
		float h = gameHeight;
		
		float wDiff = width - w;
		float hDiff = height - h;
		
		if( wDiff > hDiff ) {
			// If the diff on the width is greater, add some more to it to make
			// it math the aspect ratio
			
			// Calculates the amount that is needed to be added to width to
			// make the the aspect ratios match
			float d = h * ( width / height ) - w;
			w += d;
		} else {
			// If the diff on the height is greater, add some more to it to make
			// it math the aspect ratio
			
			// Calculates the amount that is needed to be added to height to
			// make the the aspect ratios match
			float d = w / ( width / height ) - h;
			h += d;
		}
		
		return new Vector2( w , h );
	}
}
