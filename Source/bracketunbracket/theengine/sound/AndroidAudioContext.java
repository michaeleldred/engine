/*
 * Soli Deo gloria
 */
package bracketunbracket.theengine.sound;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * @author Michael Eldred
 */
public class AndroidAudioContext extends AudioContext {

	private SoundPool pool;
	private AssetManager manager;	
	private boolean isMute = false;
	
	private int[] sounds = new int[ 250 ];
	private int[] music = new int[ 5 ];
	int c = 0;
	int m = 0;
	
	public AndroidAudioContext( AssetManager manager ) {
		pool = new SoundPool( 255 , AudioManager.STREAM_MUSIC , 0 );
		this.manager = manager;
	}
	
	@Override
	public void play(String name) {
		AndroidSound curr = (AndroidSound)get( name );
		float vol = isMute ? 0.0f : 0.99f;
		int s = pool.play( curr.id , vol , vol , 1, 0, 1.0f );
		sounds[ c++ % 255 ] = s;
	}

	@Override
	public Sound newSound(String filename) {
		AndroidSound ret = new AndroidSound();
		try {
			AssetFileDescriptor fd = manager.openFd( "Sounds/" + filename );
			Log.d( "SOUND", "FD=" + fd );
			int id = pool.load( fd , 1 );
			ret.id = id;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	@Override
	public Music newMusicTrack(String filename) {
		AndroidMusic retVal = null;
		
		try {
			AssetFileDescriptor fd = manager.openFd( "Sounds/" + filename );
			Log.d( "SOUND", "FD=" + fd );
			int id = pool.load( fd , 1 );
			retVal = new AndroidMusic( id );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return retVal;
	}

	@Override
	public void playMusic(String name) {
		AndroidMusic curr = (AndroidMusic)tracks.get( name );
		float vol = isMute ? 0.0f : 0.99f;
		music[ m++ ] = pool.play( curr.id , vol , vol , 1, 0, 1.0f );
	}

	@Override
	public void mute() {
		isMute = true;
		for( int i = 0; i < sounds.length; i++ ) {
			pool.setVolume( sounds[ i ], 0.0f , 0.0f );
		}
		for( int i = 0; i < music.length; i++ ) {
			pool.setVolume( music[ i ], 0.0f , 0.0f );
		}
	}

	@Override
	public void unmute() {
		isMute = false;
		for( int i = 0; i < sounds.length; i++ ) {
			pool.setVolume( sounds[ i ], 0.99f , 0.99f );
		}
		for( int i = 0; i < music.length; i++ ) {
			pool.setVolume( music[ i ], 0.99f , 0.99f );
		}
	}

	@Override
	public boolean isMute() {
		return isMute;
	}

	@Override
	public void setPause( boolean pause ) {
		if( pause )
			pool.autoPause();
		else
			pool.autoResume();
	}

}
