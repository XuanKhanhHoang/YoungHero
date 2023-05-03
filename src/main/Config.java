package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
	GamePanel gp;

	public Config(GamePanel gp) {
		this.gp = gp;
	}

	public void saveConfig() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter("src/config.txt"));

			bw.write(String.valueOf(gp.music.volumeScale));
			bw.newLine();
			bw.write(String.valueOf(gp.singleSound.volumeScale));
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadConfig() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/config.txt"));
			String s = br.readLine();
			gp.music.volumeScale = Integer.parseInt(s);
			s = br.readLine();
			gp.singleSound.volumeScale = Integer.parseInt(s);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
