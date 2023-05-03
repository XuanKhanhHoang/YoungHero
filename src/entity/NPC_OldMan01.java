package entity;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class NPC_OldMan01 extends Entity {
	public NPC_OldMan01(GamePanel gp) {
		super(gp);
		type = 1;
		direction = "down";
		canMove = false;
		speed = 1;
		getNPCImage();
		setDialog();
		solidArea = new Rectangle(8, 16, 38, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		name = "Nagashita";
	}

	private void getNPCImage() {
		try {
			down1 = ImageIO.read(new FileInputStream("res/npc/npc_oldMan_01_front_e.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setDialog() {
		dialoges = new String[1];
		dialoges[0] = "There is an herb used to treat\n this and it grows in the north of the village";
	}

	public void speak() {
		if (turnInterract > 1)
			super.speak();
		else {
			gp.gameState = gp.storyState;
		}
	}

}
