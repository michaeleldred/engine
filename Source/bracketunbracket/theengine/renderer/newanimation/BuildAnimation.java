/**
 * 
 */
package bracketunbracket.theengine.renderer.newanimation;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author michaeleldred
 *
 */
public class BuildAnimation extends Animation {
	
	private List<Animation> animations = new ArrayList<Animation>();
	boolean test = false;

	public BuildAnimation(RenderObject destination ) {
		super( destination, 0, null );
	}
	
	@Override
	public void update(int ticks) {
		super.update(ticks);
		
		Animation curr = getAnimationForFrame( currentTick );
		if( curr.destination != destination )
			curr.destination = destination;
		
		curr.update( ticks );
		curr.doUpdate();
	}

	@Override
	public void doUpdate() {
	}
	
	@Override
	public void reset() {
		super.reset();
		
		for( Animation anim : animations ) {
			anim.reset();
		}
	}

	public BuildAnimation add(Animation animation) {
		animations.add( animation );
		this.length += animation.length;
		return this;
	}

	protected Animation getAnimationForFrame( int currentTick ) {
		// TODO: Might be able to do this faster
		// TODO: try for looping
		
		int min = 0;
		for( int i = 0; i < animations.size() - 1; i++ ) {
			if( currentTick > min && currentTick < min + animations.get( i ).length ) {
				return animations.get( i );
			}
		}
		// If it can't find one, return the last one
		return animations.get( animations.size() - 1 );
	}

}