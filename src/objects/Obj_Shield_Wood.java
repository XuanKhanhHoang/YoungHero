package objects;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Shield_Wood extends Entity {

	public Obj_Shield_Wood(GamePanel gp) {
		super(gp);

		type = type_shield;
		name = "Wood Shield";
		description = "[ " + name + " ]" + "\nThis is a wood shield.\nIt can help you\n block damage <=10.";
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/shield_wood.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		defenseValue = 10;
		coin = 400;
	}

}
