import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JOptionPane;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * Uses audio files to play sound clips at certain events.
 * 
 * @author julianda, chois3, and jinm
 *
 */
public class PlayMusic {

	public static void playMusic(String filename) {
		InputStream music;
		try {
			music = new FileInputStream(new File(filename));
			AudioStream audio = new AudioStream(music);
			AudioPlayer.player.start(audio);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error");
		}

	}
}
