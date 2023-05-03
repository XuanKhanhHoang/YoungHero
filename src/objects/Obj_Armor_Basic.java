package objects;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Armor_Basic extends Entity {

	public Obj_Armor_Basic(GamePanel gp) {
		super(gp);

		type = type_armor;
		;
		name = "Basic Cloth Armor";
		description = "[ " + name + " ]" + "\nThis is a basic Cloth Armor.\nIt has 1 deffense point.";
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/basic_cloth_armor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		defenseValue = 1;
		coin = 100;
	}

}
