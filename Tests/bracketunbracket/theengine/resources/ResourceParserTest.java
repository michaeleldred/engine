/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.resources;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import bracketunbracket.theengine.resources.ResourceParser.UnloadedResource;

/**
 * @author Michael Eldred
 */
public class ResourceParserTest {
	@Test
	public void AddLoaderToParser() {
		MockLoader loader = new MockLoader();
		ResourceParser parser = new ResourceParser();
		parser.addLoader( "mock" , loader );
		
		assertTrue( parser.loads( "mock" ) );
	}
	
	
	@Test
	public void ResourcesCreatedInParser() {
		ResourceManager res = new ResourceManager();
		ResourceParser parser = new ResourceParser();
		MockLoader loader = new MockLoader();
		parser.addLoader( "mock" , loader );
		
		List<UnloadedResource> vals = parser.parseJSON( res , "{ \"resources\": [ { \"name\":\"test\",\"type\":\"mock\",\"kind\":\"pizza\" } ] }" );
		
		assertEquals( 1 , vals.size() );
	}
	
	@Test
	public void ResourceLoaderGetsProperties() {
		ResourceManager res = new ResourceManager();
		ResourceParser parser = new ResourceParser();
		MockLoader loader = new MockLoader();
		parser.addLoader( "mock" , loader );
		
		List<UnloadedResource> vals = parser.parseJSON( res , "{ \"resources\": [ { \"name\":\"test\",\"type\":\"mock\",\"kind\":\"pizza\" } ] }" );
		
		try {
			vals.get( 0 ).load();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertEquals( "pizza" , loader.vals.get( "kind" ) );
	}
	
}
class MockLoader implements ResourceLoader {

	public Map<String,String> vals;
	
	@Override
	public void create( ResourceManager manager , Map<String, String> vals ) {
		this.vals = vals;
	}
	
}