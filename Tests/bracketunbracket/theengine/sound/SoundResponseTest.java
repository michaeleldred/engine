package bracketunbracket.theengine.sound;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael
 */
public class SoundResponseTest {

	@Test
	public void TestInheritsChildAttributes() {
		SoundResponse child = new MockSoundResponse( 0.8f );
		SoundResponse parent = new SoundResponse( child );
		
		assertEquals( 0.8f , parent.getPitch() , 0.0001f );
	}
}

/**
 * @author Michael
 */
class MockSoundResponse extends SoundResponse {

	public MockSoundResponse( float pitch ) {
		this.pitch = pitch;
	}
}