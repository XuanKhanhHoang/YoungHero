package objects;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Potion_red extends Entity {
	public Obj_Potion_red(GamePanel gp) {
		super(gp);

		value = 4;
		type = type_consumable;
		name = "Red Potion";
		description = "[ " + name + " ]" + "\nThis is a small potion.\nIt heal for you 4 HP point.";
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/potion_red.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		coin = 250;
		stackble = true;
	}

	public boolean use(Entity entity) {
		gp.gameState = gp.dialogState;
		gp.ui.currentDialog = "You drink a " + name + "\nIt healed for you " + value + " HP point";
		gp.player.currentLife += value;
		if (gp.player.currentLife > gp.player.maxLife) {
			gp.player.currentLife = gp.player.maxLife;
		}
		return true;
	}

}