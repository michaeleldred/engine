package bracketunbracket.theengine.sound;

import bracketunbracket.theengine.event.EventListener;

public class MockAudioContext extends AudioContext {
	public String lastSound = "";

	@Override
	public void play(String name) {
		this.lastSound = name;
	}
	
	@Override
	public void play(SoundResponse response) {
		this.lastSound = response.getSound();
	}

	@Override
	public Sound newSound( String filename , EventListener listener ) {
		return null;
	}

	@Override
	public Music newMusicTrack(String filename , EventListener listener ) {
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
	}

}
