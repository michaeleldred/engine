package bracketunbracket.theengine.entity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class GameSystemTest {
	@Test
	public void SortEntities() {
		List<Entity> entities = new ArrayList<Entity>();
		
		Entity e = new Entity();
		entities.add( e );
		
		Entity e1 = new Entity();
		e1.add( new MockComponent() );
		entities.add( e1 );
		
		GameSystem sys = new MockGameSystem();
		
		List<Entity> sortedEntities = sys.sort( entities , MockComponent.class );
		
		assertFalse( sortedEntities.contains( e ) );
		assertTrue( sortedEntities.contains( e1 ) );
		
	}
}
