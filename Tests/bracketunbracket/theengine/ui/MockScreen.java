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
	public boolean didPostRender = false;

	@Override
	public boolean update() {
		wasUpdated = true;
		return shouldUpdate;
	}

	@Override
	public void render() {
		
	}
	
	@Override
	public void postRender() {
		didPostRender = true;
	}

	@Override
	public void create() {
		
	}

}
