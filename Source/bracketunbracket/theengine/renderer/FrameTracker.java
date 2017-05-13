package bracketunbracket.theengine.renderer;

import android.util.Log;

public class FrameTracker {
	
	private int frame = 0;
	private long lastTime = System.nanoTime();
	private final String tag;
	
	public FrameTracker() {
		this( "" );
	}
	
	public FrameTracker( String tag ) {
		this.tag = tag;
	}
	
	public void frame() {
		frame++;
		long thisTime = System.nanoTime();
		if( ( thisTime - lastTime ) > 1000000000L ) {
			Log.d( tag, frame + "" );
			frame = 0;
			lastTime = thisTime;
		}
		
	}
}