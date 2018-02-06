/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.SoundComponent;
import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.GameEvent;

/**
 * @author Michael Eldred
 */
public class SoundSystem extends GameSystem {

	private final AudioEngine engine;
	
	public SoundSystem( AudioEngine e ) {
		this.engine = e;
	}

	@Override
	public void tick(List<Entity> entities) {
		List<Entity> sorted = sort( entities , SoundComponent.class );
		
		for( Entity e : sorted ) {
			process( e );
		}
		
		events.clear();
	}

	public void process( Entity e ) {
		SoundComponent c = e.getComponentByType( SoundComponent.class );
		for( Event evt : e.events ) {
			if( evt instanceof GameEvent ) {
				String name = ((GameEvent)evt).name;
				if( c.responses.containsKey( name ) ) {
					engine.play( c.responses.get( name ) );
				}
			}
		}
	}
}
