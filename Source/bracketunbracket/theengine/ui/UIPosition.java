/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

import bracketunbracket.theengine.math.Rectangle;
import bracketunbracket.theengine.math.Vector2;

/**
 * Defines a position for a UIObject in the screen. It is more flexible than an
 * absolute position because it allows for an offset from the side of the screen.
 * 
 * @author Michael Eldred
 */
public class UIPosition {
	public final static int POSITION_CENTER = 1;
	public final static int POSITION_TOP = 2;
	public final static int POSITION_BOTTOM = 4;
	
	public final static int POSITION_MIDDLE = 8;
	public final static int POSITION_LEFT = 16;
	public final static int POSITION_RIGHT = 32;
	
	public int mask = 0;
	
	public final Vector2 screenPos = new Vector2();
	public final Rectangle bounds;
	
	private final float xOffset;
	private final float yOffset;
	
	public UIPosition( float xOffset , float yOffset , int position ) {
		this( new Rectangle() , xOffset , yOffset , position );
	}
	
	public UIPosition( Rectangle bounds , float xOffset , float yOffset , int position ) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.mask = position;
		
		this.bounds = bounds;
	}
	
	public void position( Rectangle screenBounds ) {
		
		if( ( mask & POSITION_CENTER ) != 0 ) {
			screenPos.y = screenBounds.y + screenBounds.h / 2.0f + yOffset; 
		} else if( ( mask & POSITION_TOP ) != 0 ) {
			screenPos.y =  screenBounds.y + ( bounds.h ) / 2.0f + yOffset;
		} else if( ( mask & POSITION_BOTTOM ) != 0 ) {
			screenPos.y = ( screenBounds.y + screenBounds.h ) - ( bounds.h ) / 2.0f - yOffset;
		}
		
		if( ( mask & POSITION_MIDDLE ) != 0 ) {
			screenPos.x = screenBounds.x + screenBounds.w / 2.0f + xOffset; 
		} else if( ( mask & POSITION_LEFT ) != 0 ) {
			screenPos.x =  screenBounds.x + ( bounds.w ) / 2.0f + xOffset;
		} else if( ( mask & POSITION_RIGHT ) != 0 ) {
			screenPos.x =  ( screenBounds.x + screenBounds.w ) - ( bounds.w ) / 2.0f - xOffset;
		}
		
		// Update bounds
		bounds.x = screenPos.x - ( bounds.w / 2.0f );
		bounds.y = screenPos.y - ( bounds.h / 2.0f );
	}
}
