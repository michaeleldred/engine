package bracketunbracket.theengine.ui;

import static org.junit.Assert.*;

import org.junit.Test;

import bracketunbracket.theengine.math.Rectangle;

public class UIPositionTest {
	@Test
	public void TestPositionSetsCorrectVerticalCoordinates() {
		Rectangle screen = new Rectangle( 0 , 0 , 100 , 100 );
		Rectangle inset = new Rectangle( 0 , 0 , 0 , 0 );
		
		UIPosition pos = new UIPosition( new Rectangle( 10 , 10 ), 5 , 5 , UIPosition.POSITION_CENTER );
		pos.position( screen , inset );
		
		assertEquals( 55.0f , pos.screenPos.y , 0.01f );
		
		pos.mask = UIPosition.POSITION_TOP;
		pos.position( screen , inset );
		
		assertEquals( 10.0f , pos.screenPos.y , 0.01f );
		
		pos.mask = UIPosition.POSITION_BOTTOM;
		pos.position( screen , inset );
		
		assertEquals( 90.0f , pos.screenPos.y , 0.01f );
	}
	
	@Test
	public void TestPositionSetsCorrectHorizontalCoordinates() {
		Rectangle screen = new Rectangle( 0 , 0 , 100 , 100 );
		Rectangle inset = new Rectangle( 0 , 0 , 0 , 0 );
		
		
		UIPosition pos = new UIPosition( new Rectangle( 10 , 10 ), 5 , 5 , UIPosition.POSITION_MIDDLE );
		pos.position( screen , inset );
		
		assertEquals( 55.0f , pos.screenPos.x , 0.01f );
		
		pos.mask = UIPosition.POSITION_LEFT;
		pos.position( screen , inset );
		
		assertEquals( 10.0f , pos.screenPos.x , 0.01f );
		
		pos.mask = UIPosition.POSITION_RIGHT;
		pos.position( screen , inset );
		
		assertEquals( 90.0f , pos.screenPos.x , 0.01f );
	}
	
	
	@Test
	public void TestVerticalPositionUsesInset() {
		Rectangle screen = new Rectangle( 0 , 0 , 100 , 100 );
		Rectangle inset = new Rectangle( 0 , 10 , 0 , 30 );
		
		UIPosition pos = new UIPosition( new Rectangle( 10 , 10 ), 5 , 5 , UIPosition.POSITION_CENTER );
		pos.position( screen , inset );
		
		assertEquals( 55.0f , pos.screenPos.y , 0.01f );
		
		pos.mask = UIPosition.POSITION_TOP;
		pos.position( screen , inset );
		
		assertEquals( 20.0f , pos.screenPos.y , 0.01f );
		
		pos.mask = UIPosition.POSITION_BOTTOM;
		pos.position( screen , inset );
		
		assertEquals( 60.0f , pos.screenPos.y , 0.01f );
	}
	
	@Test
	public void TestHorizontalPositionUsesInset() {
		Rectangle screen = new Rectangle( 0 , 0 , 100 , 100 );
		Rectangle inset = new Rectangle( 10 , 0 , 30 , 0 );
		
		
		UIPosition pos = new UIPosition( new Rectangle( 10 , 10 ), 5 , 5 , UIPosition.POSITION_MIDDLE );
		pos.position( screen , inset );
		
		assertEquals( 55.0f , pos.screenPos.x , 0.01f );
		
		pos.mask = UIPosition.POSITION_LEFT;
		pos.position( screen , inset );
		
		assertEquals( 20.0f , pos.screenPos.x , 0.01f );
		
		pos.mask = UIPosition.POSITION_RIGHT;
		pos.position( screen , inset );
		
		assertEquals( 60.0f , pos.screenPos.x , 0.01f );
	}
}
