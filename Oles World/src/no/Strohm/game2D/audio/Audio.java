package no.Strohm.game2D.audio;

import no.Strohm.game2D.Game;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Created by Ole on 10/03/2015.
 */
public class Audio {

	public static final Audio test = new Audio("trumpadole.wav");

	private final String fileName;

	public Audio(String fileName) {
		this.fileName = fileName;
	}

	public void play() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							Game.class.getResourceAsStream("/audio/" + fileName));
					clip.open(inputStream);
					FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					gainControl.setValue(-20f);
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}

}
