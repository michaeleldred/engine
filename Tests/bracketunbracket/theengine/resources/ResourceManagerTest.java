/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.resources;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael Eldred
 */
public class ResourceManagerTest {
	@Test
	public void AddResourceToManager() {
		MockResource r = new MockResource();
		ResourceManager m = new ResourceManager( null );
		
		m.add( "Test" , r );
		
		assertEquals( r , m.getResource( "Test" ) );
	}
}
