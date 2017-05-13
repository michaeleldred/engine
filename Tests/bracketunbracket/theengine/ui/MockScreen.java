/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

/**
 * @author Michael Eldred
 */
public class MockScreen extends Screen {
	public boolean wasUpdated = false;
	public boolean shouldUpdate = true;

	@Override
	public boolean update() {
		wasUpdated = true;
		return shouldUpdate;
	}

	@Override
	public void render() {
		
	}

	@Override
	public void create() {
		
	}

}
