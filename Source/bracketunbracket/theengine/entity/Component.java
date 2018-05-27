/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.entity;

/**
 * A Component in an Entity-Component-System. Any number of these are put
 * together in an {@link Entity} class and comprise an object in the game world
 * A Components should not require any methods, it should simply be a group of
 * data that will be operated on by a {@link GameSystem}.
 * 
 * @author Michael Eldred
 */
public class Component implements Cloneable {
	
	/**
	 * The parent is the entity that this component belongs to. 
	 */
	public Entity parent = null;
	
	public void setParent( Entity parent ) {
		this.parent = parent;
	}
	
	@Override
	public Component clone() {
		try {
			return (Component)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

}
