package objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class StoryImage_Child extends Entity {

	public StoryImage_Child(GamePanel gp) {
		super(gp);
		name = "StoryImage_Child";
		type = type_obstacle;
		try {
			down1 = ImageIO.read(new FileInputStream("res/player/infantPlayer.png"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
