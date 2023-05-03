package objects;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import entity.Skill;
import main.GamePanel;

public class Skill_FireBall extends Skill {

	public Skill_FireBall(GamePanel gp) {
		super(gp);
		name = "Fire Ball";
		speed = 12;
		maxLife = 20;
		currentLife = maxLife;
		attack = 8;
		useCost = 1;
		alive = false;
		getImage();
		knockBackPower = 7;
		solidArea = new Rectangle(1, 8, 46, 32);
	}

	private void getImage() {
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/fireball_down_1.png"));
			down3 = ImageIO.read(new FileInputStream("res/objects/fireball_down_2.png"));
			up1 = ImageIO.read(new FileInputStream("res/objects/fireball_up_1.png"));
			up3 = ImageIO.read(new FileInputStream("res/objects/fireball_up_2.png"));
			left1 = ImageIO.read(new FileInputStream("res/objects/fireball_left_1.png"));
			left3 = ImageIO.read(new FileInputStream("res/objects/fireball_left_2.png"));
			right1 = ImageIO.read(new FileInputStream("res/objects/fireball_right_1.png"));
			right3 = ImageIO.read(new FileInputStream("res/objects/fireball_right_2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean haveResource(Entity user) {
		if (user.mana >= useCost) {
			return true;
		} else
			return false;

	}

	public void subtractResource(Entity user) {
		user.mana -= useCost;
	}

}
