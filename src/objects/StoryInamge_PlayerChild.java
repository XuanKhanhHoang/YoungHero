package objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class StoryInamge_PlayerChild extends Entity {
	public StoryInamge_PlayerChild(GamePanel gp) {
		super(gp);
		name = "StoryInamge_PlayerChild";
		type = type_obstacle;
//		collision=true;
		try {
			down2 = ImageIO.read(new FileInputStream("res/player/playerChild_running_left_lf.png"));
			down1 = ImageIO.read(new FileInputStream("res/player/playerChild_running_right_lf.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void change() {
		down1 = down2;
		worldX = 41 * gp.TitleSize;
		worldY = 45 * gp.TitleSize;
	}

}
