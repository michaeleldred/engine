/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.resources;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.lwjgl.BufferUtils;

/**
 * @author Michael Eldred
 */
public class FileLoader {
	
	/**
	 * Designed to be used for things that are not game resources but need to 
	 * be saved on the local system, such as high-scores, settings, etc.
	 * 
	 * @author Michael Eldred
	 */
	public interface LocalStorage {
		public void save();
		public void load();
		public String get( String key );
		public void put( String key , String value );
	}

	/**
	 * Loads byte data from the machine.
	 * 
	 * @author Michael Eldred
	 */
	public interface ByteLoader {
		public InputStream load( String filename );
		public OutputStream save( String filename );
		public LocalStorage getLocalStorage();
	}
	
	private static ByteLoader loader = null;
	
	public static void setByteLoader( ByteLoader l ) {
		loader = l;
	}
	
	public static InputStream loadFilenameAsStream( String filename ) throws Exception {
		return loader.load( filename );
	}
	
	public static ByteBuffer loadFilenameAsBuffer( String filename ) throws Exception {
		InputStream in = loader.load( filename );
		ByteArrayOutputStream out = new ByteArrayOutputStream( 4096 );
		byte[] tmp = new byte[ 4096 ];
		while( true ) {
			int r = in.read( tmp );
			if( r == -1 ) break;
			out.write( tmp , 0 , r );
		}
		
		ByteBuffer data = BufferUtils.createByteBuffer( out.size() );
		data.put( out.toByteArray() );
		data.flip();
		in.close();
		out.close();
		
		return data;
	}
	
	public static LocalStorage getLocalStorage() {
		return loader.getLocalStorage();
	}
}
