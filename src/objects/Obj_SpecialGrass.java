package objects;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_SpecialGrass extends Entity {

	public Obj_SpecialGrass(GamePanel gp) {
		super(gp);
		name = "Special Grass";
		collision = true;
		type = type_consumable;
		solidArea = new Rectangle(1, 4, 47, 44);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/specialGrass.png"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
