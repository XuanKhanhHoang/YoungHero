package objects;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Potion_Mana extends Entity {
	public Obj_Potion_Mana(GamePanel gp) {
		super(gp);

		value = 3;
		type = type_consumable;
		name = "Mana Potion";
		description = "[ " + name + " ]" + "\nThis is a small potion.\nIt heal for you " + value + " Mana point.";
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/potion_mana.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		coin = 450;
		stackble = true;
	}

	public boolean use(Entity entity) {
		gp.gameState = gp.dialogState;
		gp.ui.currentDialog = "You drink a " + name + "\nIt healed for you " + value + " Mana point";
		gp.player.mana += value;
		if (gp.player.mana > gp.player.maxMana) {
			gp.player.mana = gp.player.maxMana;
		}
		return true;
	}

}