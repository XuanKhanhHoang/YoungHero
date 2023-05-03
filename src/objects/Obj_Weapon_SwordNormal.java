package objects;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Weapon_SwordNormal extends Entity {

	public Obj_Weapon_SwordNormal(GamePanel gp) {
		super(gp);
		type = type_sword;
		name = "Normal Sword";
		description = "[ " + name + " ]" + "\nThis is basic sword.\nIt has 4 attack point \n5% cirtacal chance.";
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/sword_normal.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		attackValue = 4;
		attackArea.width = 36;
		attackArea.height = 36;
		coin = 100;
		knockBackPower = 0;
		criticalChance=5;
	}

}
