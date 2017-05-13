/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.event;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Michael Eldred
 */
public class EventManagerTest {
	@Test
	public void AddListenerToManager() {
		EventManager eventManager = new EventManager();
		MockListener listener = new MockListener();
		
		eventManager.addListener( listener , Event.class );
		
		assertTrue( eventManager.evListeners.get( Event.class ).contains( listener ) );
	}
	
	public void EventIsPassedToListeners() {
		EventManager eventManager = new EventManager();
		MockListener listener = new MockListener();
		
		eventManager.addListener( listener , Event.class );
		
		Event event = new Event();
		eventManager.sendEvent( event );
		
		assertTrue( listener.events.contains( event ) );
	}
	
	@Test
	public void ListenerOnlyGetsEventsItWants() {
		EventManager eventManager = new EventManager();
		MockListener listener = new MockListener();
		
		eventManager.addListener( listener , MockEvent.class );
		
		eventManager.sendEvent( new Event() );
		eventManager.sendEvent( new MockEvent() );
		
		assertEquals( 1 , listener.events.size() );
	}
}

class MockEvent extends Event {
	
}