package bracketunbracket.theengine.sound;

public class MockAudioContext extends AudioContext {
	public String lastSound = "";

	@Override
	public void play(String name) {
		this.lastSound = name;
	}

	@Override
	public Sound newSound( String filename ) {
		return null;
	}

	@Override
	public Music newMusicTrack(String filename) {
		return null;
	}

	@Override
	public void playMusic(String name) {
		
	}

	@Override
	public void mute() {
	}

	@Override
	public void unmute() {
	}

	@Override
	public boolean isMute() {
		return false;
	}

	@Override
	public void setPause(boolean pause) {
		// TODO Auto-generated method stub
		
	}

}
