package entity;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import objects.Obj_Potion_Mana;
import objects.Obj_Potion_red;

public class NPC_Trader01 extends Entity {

	public NPC_Trader01(GamePanel gp) {
		super(gp);
		canMove = false;
		type = 1;
		direction = "down";
		getNPCImage();
		setDialog();
		solidArea = new Rectangle(0, 16, 48, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		setItems();
	}

	private void getNPCImage() {
		try {
			down1 = ImageIO.read(new FileInputStream("res/npc/npc_Man_01_front_e.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDialog() {
		dialoges = new String[1];
		dialoges[0] = "Hello, My name is Hagashi\nI have any Item\nDo you want to trade ?";
	}

	public void speak() {
		super.speak();
		gp.gameState = gp.tradeState;
		gp.ui.npc = this;
	}

	public void setItems() {
		inventory.add(new Obj_Potion_Mana(gp));
		inventory.add(new Obj_Potion_red(gp));
	}

}
