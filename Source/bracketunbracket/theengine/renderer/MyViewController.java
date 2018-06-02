/**
 * 
 */
package bracketunbracket.theengine.renderer;

import org.robovm.apple.coregraphics.CGRect;
import org.robovm.apple.glkit.GLKView;
import org.robovm.apple.opengles.EAGLContext;
import org.robovm.apple.opengles.EAGLRenderingAPI;
import org.robovm.apple.uikit.UIScreen;
import org.robovm.apple.uikit.UIViewController;

/**
 * @author michaeleldred
 *
 */
public class MyViewController extends UIViewController {

    public MyViewController() {
    	EAGLContext context = new EAGLContext( EAGLRenderingAPI.OpenGLES2 );
        GLKView view = new GLKView( UIScreen.getMainScreen().getBounds() ) {
        	@Override
        	public void draw( CGRect rect ) {
        		//GLES20.glClearColor( 1.0f , 0.5f , 0.33f , 1.0f );
        		//GLES20.glClear( GLES20.GL_COLOR_BUFFER_BIT );
        	}
        };
        view.setContext( context );
    }
}