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
	 * @author Michael
	 */
	public interface StringLoader {
		public String loadFilenameAsString( String filename );
		public void save( String filename , String data );
	}

	public interface ByteLoader {
		public InputStream load( String filename );
		public OutputStream save( String filename );
		public boolean loadsBytes();
		public StringLoader getStringLoader();
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
	
	public static String loadFilenameAsString( String filename ) throws Exception {
		if( !loader.loadsBytes() ) {
			return loader.getStringLoader().loadFilenameAsString( filename );
		}
		InputStream in = loader.load( filename );
		ByteArrayOutputStream out = new ByteArrayOutputStream( 4096 );
		byte[] tmp = new byte[ 4096 ];
		while( true ) {
			int r = in.read( tmp );
			if( r == -1 ) break;
			out.write( tmp );
		}
		
		return new String( out.toByteArray() ).trim();
	}
	
	public static void save( String filename , String data ) throws Exception {
		if( !loader.loadsBytes() ) {
			loader.getStringLoader().save( filename , data );
			return;
		}
		OutputStream out = loader.save( filename );
		out.write( data.getBytes() );
	}
	
	public static String streamToString( InputStream in ) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream( 4096 );
		byte[] tmp = new byte[ 4096 ];
		while( true ) {
			int r = in.read( tmp );
			if( r == -1 ) break;
			out.write( tmp );
		}
		
		return new String( out.toByteArray() ).trim();
	}
}
