/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael Eldred
 */
public class AudioEngineTest {
	@Test
	public void CreateEngineWithContext() {
		MockAudioContext c = new MockAudioContext();
		AudioEngine engine = new AudioEngine( c , null );
		
		assertEquals( c , engine.audioContext );
	}
	
	@Test
	public void PlaySoundFromContext() {
		MockAudioContext c = new MockAudioContext();
		AudioEngine engine = new AudioEngine( c , null );
		
		engine.play( "test" );
		
		assertEquals( "test" , c.lastSound );
	}
	
	@Test
	public void AddSoundToContext() {
		MockAudioContext c = new MockAudioContext();
		MockSound sound = new MockSound();
		c.add( "test" , sound );
		
		assertEquals( sound , c.get( "test" ) );
	}
}
