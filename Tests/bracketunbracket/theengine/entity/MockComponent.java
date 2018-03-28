/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

/**
 * @author Michael Eldred
 */
public class MockComponent extends Component {
	
	public boolean cloned = false;
	
	@Override
	public MockComponent clone() {
		cloned = true;
		return new MockComponent();
	}
}
