package main;

import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Young Hero");

		GamePanel gamePanel = new GamePanel();
		Image icon = null;
		try {
			icon = ImageIO.read(new FileInputStream("res/maps/armor_player_running_front_e.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		window.setIconImage(icon);
		window.add(gamePanel);
		gamePanel.config.loadConfig();
		window.pack();

		window.setLocationRelativeTo(null);
		window.setVisible(true);

	}

}
