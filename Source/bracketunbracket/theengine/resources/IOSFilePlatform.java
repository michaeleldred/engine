package bracketunbracket.theengine.resources;

import java.util.Arrays;

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
					System.out.println( "Loading: " + filename );
					//String vals[] = filename.split( Character.toString( '.' ) , 2 );
					int index = filename.indexOf( '.' );
					String path = filename.substring( 0 , index );
					System.out.println( "Path: " + path );
					
					String type = filename.substring( index + 1 );
					
					System.out.println( "Type: " + type );
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
