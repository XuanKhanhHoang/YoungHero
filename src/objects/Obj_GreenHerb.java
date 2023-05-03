package objects;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_GreenHerb extends Entity {

	public Obj_GreenHerb(GamePanel gp) {
		super(gp);
		name = "Special Herb";
		collision = true;
		type = type_consumable;
		solidArea = new Rectangle(10, 10, 27, 27);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/herb.png"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
