/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

import java.util.ArrayList;
import java.util.List;

import bracketunbracket.theengine.math.Vector2;

public class RenderObject {
	public Vector2 position;
	public float width;
	public float height;
	public int layer = 0;
	public Color color;
	public String imName = null;
	public float rotation;
	public Image image;
	public boolean flip = false;
	public String effect = null;
	
	public RenderObject parent;
	
	public final List<RenderObject> children = new ArrayList<RenderObject>();
	
	public RenderObject( Vector2 position , Color color , float width , float height , int layer ) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.color = color;
		this.layer = layer;
	}
	
	public RenderObject( Vector2 position , Color color , float width , float height , String texName , int layer , float rotation ) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.color = color;
		this.imName = texName;
		this.layer = layer;
		this.rotation = rotation;
	}
	
	public RenderObject( RenderObject source ) {
		this.position = source.position;
		this.width = source.width;
		this.height = source.height;
		this.layer = source.layer;
		this.color = new Color( source.color );
		this.imName = source.imName;
		this.rotation = source.rotation;
	}
	
	public void addChild( RenderObject obj ) {
		obj.parent = this;
		children.add( obj );
	}

	public Vector2 getAbsolutePosition() {
		if( parent == null )
			return position;
		return parent.getAbsolutePosition().clone().add( position );
	}
	
	public void set( RenderObject source ) {
		this.position = source.position;
		this.width = source.width;
		this.height = source.height;
		this.layer = source.layer;
		if( source.color != null )
			this.color = new Color( source.color );
		this.imName = source.imName;
		this.rotation = source.rotation;
		this.image = source.image;
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj instanceof RenderObject ) {
			RenderObject other = (RenderObject)obj;
			
			// Check position
			if( this.position != null ) {
				if( !this.getAbsolutePosition().equals( other.getAbsolutePosition() ) ) {
					return false;
				}
			} else if( other.position != null ) {
				return false;
			}
			
			// Check color
			if( this.color != null ) {
				if( !this.color.equals( other.color ) ) {
					return false;
				}
			} else if( other.color != null ) {
				return false;
			}
			
			// Check image
			if( this.imName != null ) {
				if( !this.imName.equals( other.imName ) ) {
					return false;
				}
			} else if( other.imName != null ) {
				return false;
			}
			
			// Check for effect
			if( this.effect != null ) {
				if( !this.effect.equals( other.effect ) ) {
					return false;
				}
			} else if( other.effect != null ) {
				return false;
			}
			
			return this.layer == other.layer && this.flip == other.flip;
		} else {
			return false;
		}
	}
}
