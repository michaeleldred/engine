package bracketunbracket.theengine.renderer.animation;

import bracketunbracket.theengine.renderer.Animation;

/**
 * @author Michael
 */
public class MovieAnimation extends Animation {

	public String[] frames;
	private int frameNum = 0;
	private float total = 0.0f;
	
	public MovieAnimation( float length , String ...frames ) {
		super( length );
		this.frames = frames;
	}

	@Override
	public void update( float delta ) {
		total += delta;
		
		while( total > length ) {
			total -= length;
			frameNum = ( ++frameNum ) % ( frames.length - 1 );
		}
		
		this.source.imName = frames[ frameNum ];
	}

}
