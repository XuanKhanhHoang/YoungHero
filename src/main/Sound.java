package main;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private Clip clip;
	private URL soundURL[] = new URL[30];
	private FloatControl fc;
	public int volumeScale = 3;
	float volume;

	public Sound() {
		soundURL[0] = getClass().getResource("/sound/backgroundSound.wav");
		soundURL[1] = getClass().getResource("/sound/coin_c_02-102844.wav");
		soundURL[2] = getClass().getResource("/sound/sword_01_attack.wav");
		soundURL[3] = getClass().getResource("/sound/fireSkill.wav");
	}

	public void setSound(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			try {
				clip = AudioSystem.getClip();
				clip.open(ais);
				fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				checkVolume();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void playSound() {
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		;
	}

	public void stop() {
		if (clip != null)
			clip.stop();
	}

	public void checkVolume() {
		switch (volumeScale) {
		case 0:
			volume = -80f;
			break;
		case 1:
			volume = -20f;
			break;
		case 2:
			volume = -10f;
			break;
		case 3:
			volume = -5f;
			break;
		case 4:
			volume = 1f;
			break;
		case 5:
			volume = 6f;
			break;
		}
		fc.setValue(volume);
	}
}
