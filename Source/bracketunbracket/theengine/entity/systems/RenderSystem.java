/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity.systems;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.entity.Entity;
import bracketunbracket.theengine.entity.GameSystem;
import bracketunbracket.theengine.entity.components.PositionComponent;
import bracketunbracket.theengine.entity.components.RenderComponent;
import bracketunbracket.theengine.entity.components.Tile;
import bracketunbracket.theengine.entity.components.StaticTileMap;
import bracketunbracket.theengine.entity.components.TileMapComponent;
import bracketunbracket.theengine.entity.components.TilemapRenderComponent;
import bracketunbracket.theengine.event.Event;
import bracketunbracket.theengine.event.GameEvent;
import bracketunbracket.theengine.math.Vector2;
import bracketunbracket.theengine.renderer.Camera;
import bracketunbracket.theengine.renderer.Color;
import bracketunbracket.theengine.renderer.RenderObject;

/**
 * @author Michael Eldred
 */
public class RenderSystem extends GameSystem {
	
	public final List<RenderObject> objects = new ArrayList<RenderObject>();
	
	private Camera camera = new Camera();
	
	@Override
	public void tick(List<Entity> entities) {
		RenderObject root = new RenderObject( new Vector2() , new Color( 1.0f , 1.0f , 1.0f , 1.0f ), 0.0f, 0.0f, -1 );
		for( Event evt : events ) {
			if( evt instanceof GameEvent ) {
				GameEvent e = ((GameEvent)evt);
				if( e.name.equals( "hit" ) ) {
					camera.shake( 20 );
				} else if( e.name.equals( "bighit" ) ) {
					camera.shake( 40 );
				}
			}
		}
		events.clear();
		
		camera.update();
		
		// Go through all of the entities and check for the ones that can be
		// rendered
		for( Entity current : entities ) {
			PositionComponent position = current.getComponentByType( PositionComponent.class );
			RenderComponent render = current.getComponentByType( RenderComponent.class );
			
			// Check for the correct components before going any further
			if( render == null || position == null ) {
				continue;
			}
			
			// update the position of the object
			render.obj.position = position.position;
			
			root.addChild( render.obj );
		}
		
		// Render tilemaps
		List<Entity> sorted = sort( entities , PositionComponent.class , TileMapComponent.class );
		for( Entity e : sorted ) {
			
			StaticTileMap tm = e.getComponentByType( TileMapComponent.class ).map;
			Vector2 pos = e.getComponentByType( PositionComponent.class ).position;
			float alpha = e.getComponentByType( TilemapRenderComponent.class ).obj.color.alpha;
			int layer = e.getComponentByType( TilemapRenderComponent.class ).obj.layer;

			
			int midX = tm.width / 2;
			int midY = tm.height / 2;
			
			RenderObject map = e.getComponentByType( TilemapRenderComponent.class ).obj;
			map.position = pos;
			map.clearChildren();
			
			//TODO: Store the map for later?
			
			for( int i = -midX; i < midX; i++ ) {
				for( int j = -midY; j < midY; j++ ) {
					if( tm.get( i + midX , j + midY ) == null )
						continue;
					
					float x = i * 70;
					float y = j * 70;
					
					Tile t = tm.get( i + midX , j + midY );
					
					String texture = t.getImageName();
					
					if( t.obj == null  )
						t.obj = new RenderObject( null , new Color( 1.0f , 1.0f , 1.0f , alpha ) , 0.0f , 0.0f , texture , layer , 0 );
					
					if( !texture.equals( t.obj.imName ) )
						t.obj.imName = texture;
					
					t.obj.position = new Vector2( x , y );
				
					
					map.addChild( t.obj );
				}
			}
			
			root.addChild( map );
		}
		
		root.position = camera.offset;
		
		objects.add( root );
	}
}
