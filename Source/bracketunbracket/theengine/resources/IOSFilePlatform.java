package bracketunbracket.theengine.resources;

import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSErrorException;
import org.robovm.apple.foundation.NSString;
import org.robovm.apple.foundation.NSStringEncoding;

public class IOSFilePlatform implements FilePlatform {

	@Override
	public TextData getTextData(final String filename) {
		TextData data = new TextData() {
			
			@Override
			public void load() {
				try {
					// Would rather do this with a split, but there seems to be
					// a bug in the robovm implementation of String.split that
					// splits on the first letter.
					int index = filename.indexOf( '.' );
					
					String path = filename.substring( 0 , index );
					String type = filename.substring( index + 1 );
					
					String location = NSBundle.getMainBundle().findResourcePath( path , type );
					data = NSString.readFile( location , NSStringEncoding.UTF8 );
					finished();
				} catch (NSErrorException e) {
					e.printStackTrace();
				}
				
			}
		};
		 
		return data;
	}
	
}
