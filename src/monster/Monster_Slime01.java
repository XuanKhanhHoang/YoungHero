package monster;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import objects.Obj_Coin;
import objects.Obj_Potion_Mana;
import objects.Obj_Potion_red;
import objects.Skill_FireBall;

public class Monster_Slime01 extends Entity {
	Random random;

	public Monster_Slime01(GamePanel gp) {
		super(gp);
		name = "Green Slime";
		type = type_monster;
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxLife = 12;
		currentLife = maxLife;
		attack = 3;
		defense = 0;
		exp = 3;
		onPath = false;
		knockBackPower = 1;
		projectile = new Skill_FireBall(gp);

		solidArea = new Rectangle(3, 15, 42, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		getNPCImage();
	}

	public void update() {

		xDistance = Math.abs(worldX - gp.player.worldX);
		yDistance = Math.abs(worldY - gp.player.worldY);
		tileDistance = xDistance / gp.TitleSize + yDistance / gp.TitleSize;

		if (!onPath) {
			hpBarOn = false;
			if (tileDistance <= 3)
				onPath = true;
		} else if (onPath) {
			hpBarOn = true;
			hpBarCouter = 0;
			if (tileDistance >= 6) {
				onPath = false;
				hpBarOn = false;
			}
		}
		super.update();

	}

	private void getNPCImage() {
		try {
			down1 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_1.png"));
			down3 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_2.png"));
			up1 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_1.png"));
			up3 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_2.png"));
			left1 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_1.png"));
			left3 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_2.png"));
			right1 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_1.png"));
			right3 = ImageIO.read(new FileInputStream("res/monster/greenslime_down_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAction() {
		if (onPath) {

			int goalCol = (gp.player.worldX + gp.player.solidArea.x) / gp.TitleSize,
					goalRow = (gp.player.worldY + gp.player.solidArea.y) / gp.TitleSize;
			searchPath(goalCol, goalRow);
		} else {
			lockCouter++;
			if (lockCouter == 120) {
				Random random = new Random();
				int i = random.nextInt(100) + 1;
				if (i < 25) {
					direction = "up";
				} else if (i < 50) {
					direction = "down";
				} else if (i < 75) {
					direction = "left";
				} else {
					direction = "right";
				}

				lockCouter = 0;
			}
		}

	}

	public void damageReaction() {
		lockCouter = 0;
		onPath = true;
	}

	public void checkDrop() {
		random = new Random();
		int i = random.nextInt(100) + 1;
		if (i <= 50) {
			dropItem(new Obj_Coin(gp));
		} else if (i < 75) {
			dropItem(new Obj_Potion_red(gp));
		} else {
			dropItem(new Obj_Potion_Mana(gp));
		}
	}
}
