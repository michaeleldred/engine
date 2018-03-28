package bracketunbracket.theengine.resources;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Michael
 */
public class LWJGLFilePlatform implements FilePlatform {
	
	public class LWJGLTextData extends TextData {
		private String filename;
		private InputStream stream;
		public LWJGLTextData( String filename ) {
			this.filename = filename;
		}

		@Override
		public void load() {
			stream = null;
			try {
				stream = FileLoader.loadFilenameAsStream( filename );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				@SuppressWarnings("resource")
				Scanner s = new Scanner(stream).useDelimiter("\\A");
				this.data = s.hasNext() ? s.next() : "";
				s.close();
				finished();
			} catch( Exception exc ) {
				exc.printStackTrace();
			}
		}
	}
	
	@Override
	public TextData getTextData(String filename) {
		return new LWJGLTextData( filename );
	}

}
