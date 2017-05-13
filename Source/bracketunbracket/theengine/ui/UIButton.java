/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.input.PointerEvent;
import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author Michael Eldred
 */
public class UIButton extends UIObject {
	
	public boolean click = false;
	
	public RenderObject up;
	public RenderObject down;
	
	private int pointer = -1;
	
	private ActionListener listener = null;
	
	public UIButton( UIPosition pos , RenderObject up , RenderObject down ) {
		super( pos );
		this.up = up;
		this.down = down;
		
		this.drawable = up;
	}

	@Override
	public boolean receive(Event event) {
		if( !( event instanceof PointerEvent ) )
			return false;
		
		PointerEvent evt = (PointerEvent)event;
		
		// Click
		if( position.bounds.contains( evt.x , evt.y ) && evt.isDown ) {
			pointer = evt.button;
			this.drawable = down;
			click = true;
			return true;
		}
		
		// Release
		if( evt.button == pointer && !evt.isDown ) {
			
			if( listener != null && position.bounds.contains( evt.x , evt.y ) )
				listener.action( this );
			
			this.drawable = up;
			pointer = -1;
		}
		
		return false;
	}
	
	public void onClick( ActionListener listener ) {
		this.listener = listener;
	}
}
