package entity;

import main.GamePanel;

public class Skill extends Entity {
	Entity user;

	public Skill(GamePanel gp) {
		super(gp);
	}

	public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
		this.user = user;
		this.worldX = worldX;
		this.worldY = worldY;
		this.direction = direction;
		this.alive = alive;
		this.currentLife = maxLife;

	}

	public void update() {
		switch (direction) {
		case "up":
			worldY -= speed;
			break;
		case "down":
			worldY += speed;
			break;
		case "left":
			worldX -= speed;
			break;
		case "right":
			worldX += speed;
			break;
		}
		currentLife--;
		if (currentLife <= 0) {
			alive = false;
		}
		spriteCounter++;
		if (spriteCounter > 12) {
			if (spriteNum == 1)
				spriteNum = 3;
			else if (spriteNum == 3)
				spriteNum = 1;
			spriteCounter = 0;
		}
		if (user == gp.player) {
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if (monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex, (int) ((strength+5)*2.7), knockBackPower,false);
				alive = false;
			}
		} else {
			boolean conntactPlayer = gp.cChecker.checkPlayer(this);
			if (!gp.player.invincible && conntactPlayer) {
				damageToPlayer((int) (attack*1.2));
				alive = false;
			}
			if (gp.player.defencing && conntactPlayer) {
				alive = false;
			}
		}
	}

	public boolean haveResource(Entity user) {
		if (user.mana >= useCost) {
			return true;
		} else
			return false;

	}

	public void subtractResource(Entity user) {
	}

}
