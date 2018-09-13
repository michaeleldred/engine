/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.renderer.GameWindow;

/**
 * @author Michael Eldred
 */
public class ScreenManagerTest {
	@Test
	public void SetCurrentScreen() {
		ScreenManager screenManager = new ScreenManager( null );
		Screen screen = new MockScreen();
		
		screenManager.setScreen( screen );
		
		assertEquals( screen , screenManager.getCurrentScreen() );
	}
	
	@Test
	public void ScreenUpdateOnManagerUpdate() {
		ScreenManager screenManager = new ScreenManager( null );
		MockScreen screen = new MockScreen();
		
		screenManager.setScreen( screen );
		
		screenManager.update();
		
		assertTrue( screen.wasUpdated );
	}
	
	@Test
	public void ScreenGetManagerEvents() {
		ScreenManager screenManager = new ScreenManager( null );
		MockScreen screen = new MockScreen();
		
		screenManager.setScreen( screen );
		
		Event event = new Event();
		
		screenManager.receive( event );
		
		assertTrue( screen.events.contains( event ) );
	}
	
	@Test
	public void ScreenGetsParentManager() {
		GameWindow window = new GameWindow();
		ScreenManager screenManager = new ScreenManager( window );
		Screen screen = new MockScreen();
		
		screenManager.setScreen( screen );
		
		assertEquals( window , screen.getParent() );
	}
	
	@Test
	public void ParentRemovedAfterScreenSwitch() {
		ScreenManager screenManager = new ScreenManager( null );
		Screen screen = new MockScreen();
		
		screenManager.setScreen( screen );
		
		screenManager.setScreen( new MockScreen() );
		
		assertEquals( null , screen.getParent() );
	}
	
	@Test
	public void PushScreenToFront() {
		ScreenManager screenManager = new ScreenManager( null );
		Screen screen = new MockScreen();
		Screen screen2 = new MockScreen();
		
		screenManager.setScreen( screen );
		screenManager.pushScreen( screen2 );
		
		assertTrue( screenManager.screens.contains( screen ) );
		assertTrue( screenManager.screens.contains( screen2 ) );
		
		screenManager.popScreen();
		
		assertTrue( screenManager.screens.contains( screen ) );
		assertFalse( screenManager.screens.contains( screen2 ) );
	}
	
	@Test
	public void NextScreenDoesNotGetUpdateWhenTrue() {
		ScreenManager screenManager = new ScreenManager( null );
		MockScreen screen = new MockScreen();
		MockScreen screen2 = new MockScreen();
		screen2.shouldUpdate = true;
		
		screenManager.setScreen( screen );
		screenManager.pushScreen( screen2 );
		
		screenManager.update();
		
		assertFalse( screen.wasUpdated );
		assertTrue( screen2.wasUpdated );
	}
	
	@Test
	public void NextScreenDoesGetUpdateWhenFalse() {
		ScreenManager screenManager = new ScreenManager( null );
		MockScreen screen = new MockScreen();
		MockScreen screen2 = new MockScreen();
		screen2.shouldUpdate = false;
		
		screenManager.setScreen( screen );
		screenManager.pushScreen( screen2 );
		
		screenManager.update();
		
		assertTrue( screen.wasUpdated );
		assertTrue( screen2.wasUpdated );
	}
	
	@Test
	public void OnlyTheTopScreenGetsEvents() {
		ScreenManager screenManager = new ScreenManager( null );
		Screen screen = new MockScreen();
		Screen screen2 = new MockScreen();
		Event test = new Event();
		
		screenManager.setScreen( screen );
		screenManager.pushScreen( screen2 );
		
		screenManager.receive( test );
		
		assertTrue( screen2.events.contains( test ) );
		assertFalse( screen.events.contains( test ) );
	}
	
	@Test
	public void PushedScreenGetParent() {
		GameWindow window = new GameWindow();
		ScreenManager manager = new ScreenManager( window );
		MockScreen screen1 = new MockScreen();
		
		manager.pushScreen( new MockScreen() );
		manager.pushScreen( screen1 );
		
		assertEquals( window , screen1.parent );
	}
	
	@Test
	public void IfThereIsNoScreenDontReceieveEvent() {
		ScreenManager screenManager = new ScreenManager( null );
		screenManager.receive( new Event() );
		
		assertTrue( true );
	}
	
	@Test
	public void DontDrawUIForBackgroundScreen() {
		ScreenManager screenManager = new ScreenManager( null );
		MockScreen screen = new MockScreen();
		MockScreen screen2 = new MockScreen();
		
		screenManager.setScreen( screen );
		screenManager.pushScreen( screen2 );
		
		screenManager.update();
		
		assertFalse( screen.didPostRender );
		assertTrue( screen2.didPostRender );
	}
}
