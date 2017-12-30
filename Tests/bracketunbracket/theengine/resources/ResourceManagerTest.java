package bracketunbracket.theengine.resources;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

/**
 * @author Michael
 */
public class ResourceManagerTest {
	@Test
	public void AddingResourceIncrementsUnloadedAmount() {
		ResourceManager manager = new ResourceManager();
		manager.add( new Resource( null ) {} );
		
		assertEquals( 1 , manager.getUnloaded() );
	}
	
	@Test
	public void WhenLoadedAddToLoaded() {
		ResourceManager manager = new ResourceManager();
		MockResource resource = new MockResource( null );
		manager.add( resource );
		
		resource.finished();
		
		assertEquals( 1 , manager.getLoaded() );
		assertEquals( 0 , manager.getUnloaded() );
	}
}

class MockResource extends Resource {

	public MockResource(HashMap<String, String> values) {
		super(values);
	}
	
}