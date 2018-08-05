/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.components;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.entity.Component;

/**
 * @author Michael Eldred
 */
public class AnimationComponent extends Component {
	
	public final List<String> frames = new ArrayList<String>();
	public int currentFrame = 0;
	public int ticks = 0;
	public int ticksPerFrame = 0;
	public boolean loop = true;
	
	public AnimationComponent( int ticks , String...frames ) {
		this( ticks , true , frames );
	}
	
	public AnimationComponent( int ticks , boolean loop , String...frames ) {
		
		for( String frame : frames ) {
			this.frames.add( frame );
		}
		
		ticksPerFrame = ticks;
		currentFrame = 0;
		this.loop = loop;
	
	}
}
