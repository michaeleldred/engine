/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.input;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import bracketunbracket.theengine.event.EventManager;
import bracketunbracket.theengine.input.PointerEvent.EventType;
import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.GameWindow;

/**
 * @author Michael Eldred
 */
public class AndroidInputManager implements View.OnTouchListener {
	
	private EventManager manager;
	private GameWindow window;
	
	public AndroidInputManager( EventManager manager , GameWindow window ) {
		this.manager = manager;
		this.window = window;
	}

	@Override
	public boolean onTouch( View view, MotionEvent event ) {
		
		int action = event.getActionMasked();
		int index  = event.getActionIndex();
		int id = event.getPointerId( index );
		
		float x = 0 , y = 0;
		boolean isDown = false;
		
		x = event.getX( index ) - view.getWidth() / 2;
		y = event.getY( index ) - view.getHeight() / 2;
		
		// Normalise
		x /= ( view.getWidth() / 2 );
		y /= ( view.getHeight() / 2 );
		
		Vector2 v = window.getScaledDimensions();
		x *= ( v.x / 2.0f );
		y *= ( v.y / 2.0f );
		
		
		// Handle the event to get the correct parameters
		switch( action ) {
		case MotionEvent.ACTION_MOVE:
			isDown = true;
			// Go through the active pointers and set positions.
			for( int i = 0; i < event.getPointerCount(); i++ ) {
				int d = event.getPointerId( i );
				
				x = event.getX( i ) - view.getWidth() / 2;
				y = event.getY( i ) - view.getHeight() / 2;
				x /= ( view.getWidth() / 2 );
				y /= ( view.getHeight() / 2 );
				x *= ( v.x / 2.0f );
				y *= ( v.y / 2.0f );
				
				manager.sendEvent( new PointerEvent( d , x , y , isDown , EventType.MOVE ) );
				Log.d( "POINT" , "MOVE: " + new PointerEvent( d , x , y , isDown ).toString() );
			}
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
		case MotionEvent.ACTION_DOWN:
			isDown = true;
			manager.sendEvent( new PointerEvent( id , x , y , isDown , EventType.DOWN ) );
			Log.d( "POINT" , "DOWN:" + new PointerEvent( id , x , y , isDown ).toString() );
			break;
		case MotionEvent.ACTION_POINTER_UP:
		case MotionEvent.ACTION_UP:
			isDown = false;
			manager.sendEvent( new PointerEvent( id , x , y , isDown , EventType.UP ) );
			Log.d( "POINT" , "UP:" + new PointerEvent( id , x , y , isDown ).toString() );
			break;
		default:
			return true;
		}
		
		return true;
	}
	
	
}
