/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.renderer;

/**
 * @author Michael Eldred
 */
public class Color {
	
	public final static Color WHITE = new Color( 1.0f , 1.0f , 1.0f );
	
	public float red = 0.0f;
	public float green = 0.0f;
	public float blue = 0.0f;
	public float alpha = 1.0f;
	
	public Color( float red , float green , float blue ) {
		this( red , green , blue , 1.0f );
	}
	
	public Color( float red , float green , float blue , float alpha ) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}
	
	public Color( Color source ) {
		if( source != null ) {
			this.red   = source.red;
			this.green = source.green;
			this.blue  = source.blue;
			this.alpha = source.alpha;
		}
	}
	
	@Override
	public boolean equals( Object obj ) {
		if( obj instanceof Color ) {
			Color other = (Color)obj;
			return this.red == other.red && this.green == other.green &&
					this.blue == other.blue && this.alpha == other.alpha;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "rgba( " + red + ", " + green + ", " + blue + ", " + alpha + " )"; 
	}
	
	public Color mult( Color other ) {
		return new Color( red * other.red , green * other.green, blue * other.blue , alpha * other.alpha );
	}
	
	/**
	 * Multiplies all of te colors by num, bur leaves the alpha unchanged.
	 * @param num
	 * @return
	 */
	public Color mult( float num ) {
		return new Color( red * num , green * num , blue * num );
	}
}
