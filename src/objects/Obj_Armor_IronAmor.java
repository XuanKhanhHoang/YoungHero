package objects;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Armor_IronAmor extends Entity {

	public Obj_Armor_IronAmor(GamePanel gp) {
		super(gp);

		type = type_armor;
		;
		name = "Iron Armor";
		description = "[ " + name + " ]" + "\nThis is a Iron Armor.\nIt has 2 deffense point.";
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/iron_armor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		defenseValue = 2;
		coin = 800;
	}

}
