/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.stb.STBVorbisInfo;

import bracketunbracket.theengine.resources.FileLoader;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.ALC11.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryUtil.*;
/**
 * @author Michael Eldred
 */
public class LWJGLAudioContext extends AudioContext {

	public final long device;
	public final long context;
	
	private int sources[];
	private int musicSource[];
	private int d = 0;
	private int c = 0;
	
	private boolean isMute = true;
	
	public LWJGLAudioContext() {
		device = alcOpenDevice( (ByteBuffer)null );
		if( device == NULL ) {
			System.out.println( "VASD" );
		}
		
		ALCCapabilities deviceCaps = ALC.createCapabilities( device );
		
		context = alcCreateContext( device , (IntBuffer) null );
		alcMakeContextCurrent( context );
		AL.createCapabilities( deviceCaps );
		
		// Generate one for now
		musicSource = new int[ 1 ];
		musicSource[ 0 ] = alGenSources();
		
		int numSources = alcGetInteger( device , ALC_MONO_SOURCES );
		sources = new int[ numSources ];
		for( int i = 0; i < sources.length - 1; i++ ) {
			sources[ i ] = alGenSources();
		}
	}
	
	public void destroy() {
		
		for( int i = 0; i < sources.length; i++ ) {
			alDeleteSources( sources[ i ] );
		}
		
		alcDestroyContext( context );
		alcCloseDevice( device );
	}
	
	@Override
	public void play(String name) {
		int source = sources[ c++ % sources.length ];
		LWJGLSound s = (LWJGLSound)get( name );
		alSourcei( source , AL_BUFFER , s.buffer );
		alSourcei( source , AL_LOOPING,  AL_FALSE );
		System.out.println( "NO PITCH" );
		if( isMute )
			alSourcef( source , AL_GAIN , 0.0f );
		
		alSourcePlay( source );
	}
	
	public void play( ScriptedSound sound ) {
		int source = sources[ c++ % sources.length ];
		LWJGLSound s = (LWJGLSound)get( sound.sound );
		alSourcei( source , AL_BUFFER , s.buffer );
		alSourcei( source , AL_LOOPING,  AL_FALSE );
		System.out.println( "PITCH: " + sound.pitch );
		alSourcef( source , AL_PITCH , sound.pitch );
		
		
		if( isMute )
			alSourcef( source , AL_GAIN , 0.0f );
		
		alSourcePlay( source );
	}
	
	@Override
	public void playMusic( String name ) {
		int source = musicSource[ d++ % musicSource.length ];
		LWJGLMusic s = (LWJGLMusic)tracks.get( name );
		alSourcei( source , AL_BUFFER , s.buffer );
		alSourcei( source , AL_LOOPING,  AL_TRUE );
		
		if( isMute )
			alSourcef( source , AL_GAIN , 0.0f );
		
		alSourcePlay( source );
	}

	@Override
	public Sound newSound( String filename ) {
		int b = alGenBuffers();
		
		STBVorbisInfo info = STBVorbisInfo.malloc();
		ShortBuffer pcm = loadVorbis( filename , info );
		
		int format = info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
		alBufferData( b , format , pcm , info.sample_rate() );
		
		LWJGLSound s = new LWJGLSound( b );
		return s; 
	}
	
	@Override
	public Music newMusicTrack( String filename ) {
		int b = alGenBuffers();
		
		STBVorbisInfo info = STBVorbisInfo.malloc();
		ShortBuffer pcm = loadVorbis( filename , info );
		
		int format = info.channels() == 1 ? AL_FORMAT_MONO16 : AL_FORMAT_STEREO16;
		alBufferData( b , format , pcm , info.sample_rate() );
		
		LWJGLMusic s = new LWJGLMusic( b );
		return s; 
	}
	
	private ShortBuffer loadVorbis( String filename , STBVorbisInfo info ) {
		
		// Load the data from file
		ByteBuffer data = null;
		try {
			data = FileLoader.loadFilenameAsBuffer( "Sounds/" + filename );
		} catch( Exception exc ) {
			throw new RuntimeException( exc );
		}
		
		// Convert from ogg
		IntBuffer err = ByteBuffer.allocateDirect( 4 ).order( ByteOrder.nativeOrder() ).asIntBuffer();
		
		long decoder = stb_vorbis_open_memory( data , err , null );
		
		if( decoder == NULL ) {
			System.err.println( "HORRID SOUND THINGS" );
			return null;
		}
		
		// Put it into a new buffer
		
		stb_vorbis_get_info( decoder , info );
		
		int channels = info.channels();
		int length = stb_vorbis_stream_length_in_samples( decoder );
		
		ShortBuffer pcm = ByteBuffer.allocateDirect( 2 * length ).order(ByteOrder.nativeOrder()).asShortBuffer();
		pcm.limit( stb_vorbis_get_samples_short_interleaved( decoder , channels, pcm ) * channels );
		
		return pcm;
	}

	@Override
	public void mute() {
		for( int i = 0; i < musicSource.length; i++ ) {
			alSourcef( musicSource[ i ] , AL_GAIN , 0.0f );
		}
		for( int i = 0; i < sources.length - 1; i++ ) {
			alSourcef( sources[ i ] , AL_GAIN , 0.0f );
		}
		
		isMute = true;
	}

	@Override
	public void unmute() {
		for( int i = 0; i < musicSource.length; i++ ) {
			alSourcef( musicSource[ i ] , AL_GAIN , 1.0f );
		}
		for( int i = 0; i < sources.length - 1; i++ ) {
			alSourcef( sources[ i ] , AL_GAIN , 1.0f );
		}
		
		isMute = false;
	}

	@Override
	public boolean isMute() {
		return isMute;
	}

	@Override
	public void setPause( boolean pause ) {
		
		if( pause ) {
			for( int i = 0; i < musicSource.length; i++ ) {
				alSourcePause( musicSource[ i ] );
			}
			for( int i = 0; i < sources.length - 1; i++ ) {
				alSourcePause( sources[ i ] );
			}
		} else {
			for( int i = 0; i < musicSource.length; i++ ) {
				alSourcePlay( musicSource[ i ] );
			}
			for( int i = 0; i < sources.length - 1; i++ ) {
				alSourcePlay( sources[ i ] );
			}
		}
		
	}
}
