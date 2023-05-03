package objects;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Weapon_RedSword extends Entity {

	public Obj_Weapon_RedSword(GamePanel gp) {
		super(gp);
		type = type_sword;
		name = "Red Sword";
		description = "[ " + name + " ]" + "\nThis is advance sword.\nIt has 7 attack point and \n 40% cirtacal chance.";
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/redSword.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		attackValue = 7;
		attackArea.width = 36;
		attackArea.height = 36;
		coin = 800;
		knockBackPower = 5;
		criticalChance=40;
	}

}
