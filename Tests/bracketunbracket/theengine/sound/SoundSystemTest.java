/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.components.SoundComponent;
import bracketunbracket.theengine.event.GameEvent;

/**
 * @author Michael Eldred
 */
public class SoundSystemTest {
	@Test
	public void SoundPlayedWhenEventOccurs() {
		Entity e = new Entity();
		SoundComponent sc = new SoundComponent();
		sc.addResponse( "splash" , "act_splash" );
		e.add( sc );
		
		e.receiveEvent( new GameEvent( "splash" ) );
		e.swapEvents();
		
		MockAudioContext context = new MockAudioContext();
		
		SoundSystem system = new SoundSystem( new AudioEngine( context ) );
		system.process( e );
		
		assertEquals( "act_splash" , context.lastSound );
	}
}
