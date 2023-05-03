package monster;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Monster_Skeleton01 extends Entity {
	Random random;

	public Monster_Skeleton01(GamePanel gp) {
		super(gp);
		name = "Skeleton";
		type = type_monster;
		defaultSpeed = 2;
		speed = defaultSpeed;
		maxLife = 30;
		currentLife = maxLife;
		attack = 5;
		defense = 3;
		exp = 5;
		direction = "left";
		knockBackPower = 2;
		solidArea = new Rectangle(5, 3, 42, 36);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea = new Rectangle(6, 4, 38, 38);
		attacking = false;
		getNPCImage();
		level = 0;
		haveAttackAnimation = true;
	}

	public void update() {

		xDistance = Math.abs(worldX - gp.player.worldX);
		yDistance = Math.abs(worldY - gp.player.worldY);
		tileDistance = xDistance / gp.TitleSize + yDistance / gp.TitleSize;

		if (!onPath) {
			hpBarOn = false;
			if (tileDistance <= 4) {
				onPath = true;
			}
		} else if (onPath) {
			hpBarOn = true;
			hpBarCouter = 0;
			if (tileDistance >= 7) {
				onPath = false;
				hpBarOn = false;
				attacking = false;
			}
			if (tileDistance <= 3) {
				attacking = true;
			}
		}
		super.update();
	}

	private void getNPCImage() {
		try {
			down1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_down_1.png"));
			down3 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_down_2.png"));
			up1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_up_1.png"));
			up3 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_up_2.png"));
			left1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_left_1.png"));
			left3 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_left_2.png"));
			right1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_right_1.png"));
			right3 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			attackUp1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_up_1.png"));
			attackUp2 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_up_2.png"));
			attackDown1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_down_1.png"));
			attackDown2 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_down_2.png"));
			attackLeft1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_left_1.png"));
			attackLeft2 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_left_2.png"));
			attackRight1 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_right_1.png"));
			attackRight2 = ImageIO.read(new FileInputStream("res/monster/skeletonlord_attack_right_2.png"));
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
//		random = new Random();
//		int i = random.nextInt(100)+1;
//		dropItem(new Obj_Armor_IronAmor(gp));
	}
}
