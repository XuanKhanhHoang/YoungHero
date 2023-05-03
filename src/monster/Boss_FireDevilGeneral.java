package monster;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import objects.Skill_FireBall;

public class Boss_FireDevilGeneral extends Entity {
	Random random;

	public Boss_FireDevilGeneral(GamePanel gp) {
		super(gp);
		name = "FireDevil General";
		type = type_monster;
		defaultSpeed = 2;
		speed = defaultSpeed;
		maxLife = 46;
		currentLife = maxLife;
		attack = 5;
		defense = 3;
		exp = 9;
		direction = "left";
		knockBackPower = 3;
		projectile = new Skill_FireBall(gp);
		solidArea = new Rectangle(5, 3, 42, 36);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea = new Rectangle(6, 4, 40, 40);
		attacking = false;
		getNPCImage();
		level = 0;
		haveAttackAnimation = true;
		onPath = true;
		skillCastRate = 35;
	}

	public void update() {

		xDistance = Math.abs(worldX - gp.player.worldX);
		yDistance = Math.abs(worldY - gp.player.worldY);
		tileDistance = xDistance / gp.TitleSize + yDistance / gp.TitleSize;

		if (onPath) {
			hpBarOn = true;
			hpBarCouter = 0;
			if (tileDistance >= 4) {
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
			down1 = ImageIO.read(new FileInputStream("res/monster/boss01_down1.png"));
			down3 = ImageIO.read(new FileInputStream("res/monster/boss01_down2.png"));
			up1 = ImageIO.read(new FileInputStream("res/monster/boss01_up1.png"));
			up3 = ImageIO.read(new FileInputStream("res/monster/boss01_up2.png"));
			left1 = ImageIO.read(new FileInputStream("res/monster/boss01_left1.png"));
			left3 = ImageIO.read(new FileInputStream("res/monster/boss01_left2.png"));
			right1 = ImageIO.read(new FileInputStream("res/monster/boss01_right1.png"));
			right3 = ImageIO.read(new FileInputStream("res/monster/boss01_right2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			attackUp1 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_up1.png"));
			attackUp2 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_up2.png"));
			attackDown1 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_down1.png"));
			attackDown2 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_down2.png"));
			attackLeft1 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_left1.png"));
			attackLeft2 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_left2.png"));
			attackRight1 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_right1.png"));
			attackRight2 = ImageIO.read(new FileInputStream("res/monster/boss01_attacking_right2.png"));
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
		gp.gameState = gp.storyState;
		gp.player.direction = "down";
		gp.player.attacking = false;
		gp.player.invincible = false;
		gp.player.projectile.alive=false;
	}
}
