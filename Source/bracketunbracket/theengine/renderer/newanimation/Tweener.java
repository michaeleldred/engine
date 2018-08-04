package bracketunbracket.theengine.renderer.newanimation;

/**
 * 
 * @author michaeleldred
 */
public abstract class Tweener {
	
	public final static Tweener linear = new Tweener() {
		@Override
		public float getRatioValue( float t ) {
			return t;
		}
	};
	
	public final static Tweener easeInQuad = new Tweener() {
		@Override
		public float getRatioValue( float t ) {
			return t * t;
		}
	};
	
	public final static Tweener easeOutQuad = new Tweener() {
		@Override
		public float getRatioValue( float t ) {
			return t * (2-t);
		}
	};
	public final static Tweener easeInQuint = new Tweener() {
		@Override
		public float getRatioValue( float t ) {
			return t*t*t*t*t;
		}
	};
	public final static Tweener easeOutQuint = new Tweener() {
		@Override
		public float getRatioValue( float t ) {
			return 1 + (--t)*t*t*t*t;
		}
	};
	
	public final static Tweener easeOutElastic = new Tweener() {
		private final static double p = 0.5;
		
		@Override
		public float getRatioValue( float t ) {
			return (float) (Math.pow( 2 , -10*t ) * Math.sin( ( t - p/4 ) * 2*Math.PI/p) + 1);
		}
	};
	
	
	public float getValue( float min , float max , int current , int length ) {
		return min + ( max - min ) * getRatioValue( (float)current / (float)length );
	}
	
	public abstract float getRatioValue( float t );
}