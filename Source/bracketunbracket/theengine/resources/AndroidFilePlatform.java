/**
 * 
 */
package bracketunbracket.theengine.resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author michaeleldred
 *
 */
public class AndroidFilePlatform implements FilePlatform {

	class AndroidTextData extends TextData {
		private final String filename;
		
		public AndroidTextData( String filename ) {
			this.filename = filename;
		}
		
		@Override
		public void load() {
			InputStream in = FileLoader.loader.load( filename );
			
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[ 1024 ];
			int length;
			
			try {
				while( ( length = in.read( buffer )) != -1 ) {
					result.write( buffer , 0 , length );
				}
				data = result.toString("UTF-8");
				finished();
			} catch (IOException exc) {
				throw new RuntimeException( exc );
			}
		}
		
	}
	
	/* (non-Javadoc)
	 * @see bracketunbracket.theengine.resources.FilePlatform#getTextData(java.lang.String)
	 */
	@Override
	public TextData getTextData(String filename) {
		return new AndroidTextData( filename );
	}

}
