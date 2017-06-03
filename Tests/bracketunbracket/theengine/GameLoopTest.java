package bracketunbracket.theengine;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael Eldred
 */
public class GameLoopTest {
	
	@Test
	public void GameLoopAddObjects() {
		GameLoop loop = new GameLoop();
		
		Tickable tickable = new Tickable() {
			public void tick() {}
		};
		
		loop.add( tickable );
		
		assertTrue( loop.tickables.contains( tickable ) );
	}
}
