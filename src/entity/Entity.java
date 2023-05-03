package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import main.GamePanel;

public class Entity {

	protected GamePanel gp;

	public int worldX, worldY;
	public int speed, defaultSpeed;
	public Rectangle solidArea = new Rectangle(0, 0, 47, 47);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public BufferedImage up1, up2, up3, down1, down2, down3, left1, left2, left3, right1, right2, right3;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
			attackRight2;
	public BufferedImage defenceUp1, defenceDown1, defenceLeft1, defenceRight1;
	public String direction = "down";

	public boolean collisionOn;
	protected int lockCouter = 0;
	public String dialoges[];
	public int dialogIndex = 0;

	public BufferedImage image, image1, image2;
	public String name;
	public boolean collision = false;
	public int spriteCounter = 0;
	public int spriteNum = 1;

	public int type;
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_armor = 5;
	public final int type_consumable = 6;
	public final int type_pickUpOnly = 7;
	public final int type_obstacle = 8;
	public final int type_shield = 9;

	public boolean stackble = false;
	public int amount = 1;

	public int maxLife;
	public int currentLife;
	public int mana;
	public int maxMana;
	public boolean invincible = false;
	public int invincibleCout = 0;
	public boolean attacking = false;

	public boolean dying = false;
	public boolean alive = true;
	private int dyingCouter = 0;
	public boolean hpBarOn = false;
	public int hpBarCouter = 0;
	public boolean canMove = true;
	public boolean onPath = false;
	public boolean knockBack = false;
	public int knockBackCouter = 0;

	public int level = 1;
	public int strength;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public Entity currentWeapon;
	public Entity currentArmor;
	public Entity currentShield;

	public int attackValue;
	public int defenseValue;
	public int value;
	public int knockBackPower;

	public String description = "";

	public Skill projectile;
	public int useCost;
	public int projectileAvailbleCounter = 0;

	public String tempDirection;
	public int turnInterract = 1;
	public int attackCounter = 0;

	public ArrayList<Entity> inventory = new ArrayList<>();
	public final int maxInventorySize = 20;

	public int xDistance;
	public int yDistance;
	public int tileDistance;

	public boolean haveAttackAnimation = false;
	public int skillCastRate = 0;
	public int criticalChance =0;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void interract() {
	}

	public void setAction() {
	}

	public void speak() {
		if (dialogIndex >= dialoges.length)
			dialogIndex = 0;
		gp.ui.currentDialog = dialoges[dialogIndex];
		dialogIndex++;
		switch (gp.player.direction) {
		case "down":
			this.direction = "up";
			break;
		case "up":
			this.direction = "down";
			break;
		case "left":
			this.direction = "right";
			break;
		case "right":
			this.direction = "left";
			break;
		}
	}

	public int getTopY() {
		return worldY + solidArea.y;
	}

	public int getBottomY() {
		return worldY + solidArea.y + solidArea.height;
	}

	public int getLeftX() {
		return worldX + solidArea.x;
	}

	public int getRightX() {
		return worldX + solidArea.x + solidArea.width;
	}

	public int getCol() {
		return worldX / gp.TitleSize;
	}

	public int getRow() {
		return worldY / gp.TitleSize;
	}

	public void update() {

		if (canMove) {
			if (knockBack) {
				checkCollistion();
				if (collisionOn) {
					knockBackCouter = 0;
					knockBack = false;
					speed = defaultSpeed;
					direction = tempDirection;
				} else {
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
				}
				knockBackCouter++;
				if (knockBackCouter >= 10) {
					knockBackCouter = 0;
					knockBack = false;
					speed = defaultSpeed;
					direction = tempDirection;
				}
			} else {
				setAction();
				checkCollistion();
				if (!collisionOn) {
					if (direction == "up") {
						worldY -= speed;
					} else if (direction == "down") {
						worldY += speed;
					} else if (direction == "left") {
						worldX -= speed;
					} else if (direction == "right") {
						worldX += speed;
					}
				}
				if (onPath && type == type_monster && haveAttackAnimation) {
					attackingToPlayer();
				}
			}
			if (!attacking) {
				spriteCounter++;
				if (spriteCounter > 12) {
					if (spriteNum == 1)
						spriteNum = 3;
					else if (spriteNum == 3)
						spriteNum = 1;
					spriteCounter = 0;
				}
			}
			if (invincible) {
				invincibleCout++;
				if (invincibleCout > 40) {
					invincible = false;
					invincibleCout = 0;
				}
			}
			if (projectileAvailbleCounter < 30) {
				projectileAvailbleCounter++;
			}
		}

	}

	public void knockBack(Entity entity, int knockBackPower) {

		entity.tempDirection = entity.direction;
		entity.direction = direction;
		entity.speed += knockBackPower;
		entity.knockBack = true;
	}

	public void damageToPlayer(int attack) {
		knockBack(gp.player, knockBackPower);
		int damage = attack - gp.player.defense;
		if (damage < 0) {
			damage = 0;
		}
		gp.player.currentLife -= damage;
		gp.player.invincible = true;
	}

	public void draw(Graphics2D g2) {
		BufferedImage img = down1;
		// CACULATE ENTITY'S PLACE IN SCREEN
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		// DRAW IF ENTITY'S PLACE IN SCREEN
		if (gp.player.worldX < gp.player.screenX) {
			screenX = worldX;
		}
		if (gp.player.worldY < gp.player.screenY) {
			screenY = worldY;
		}
		int rightOffset = gp.screenWidth - gp.player.screenX;
		int bottomOffset = gp.screenHeghit - gp.player.screenY;
		if (rightOffset > gp.worldWidth - gp.player.worldX) {
			screenX = gp.screenWidth - (gp.worldWidth - worldX);
		}
		if (bottomOffset > gp.worldWidth - gp.player.worldY) {
			screenY = gp.screenHeghit - (gp.worldHeight - worldY);
		}
		int tempX = screenX, tempY = screenY;
		if (worldX > gp.player.worldX - gp.player.screenX - gp.TitleSize
				&& worldX < gp.player.worldX + gp.player.screenX + gp.TitleSize
				&& worldY > gp.player.worldY - gp.player.screenY - gp.TitleSize
				&& worldY < gp.player.worldY + gp.player.screenY + gp.TitleSize) {
			if (knockBack) {
				switch (tempDirection) {
				case "up":
					if (!attacking) {
						if (spriteNum == 1)
							img = up1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = up3;
						break;
					} else if (attacking) {
						if (spriteNum == 1)
							img = attackUp1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackUp2;
						screenY -= gp.TitleSize;
						break;
					}
				case "down":
					if (!attacking) {
						if (spriteNum == 1)
							img = down1;
						else if (spriteNum == 2)
							img = down2;
						else if (spriteNum == 3)
							img = down3;
						break;
					} else if (attacking) {
						if (spriteNum == 1)
							img = attackDown1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackDown2;
						break;
					}
				case "left":
					if (!attacking) {
						if (spriteNum == 1)
							img = left1;
						else if (spriteNum == 2)
							img = left2;
						else if (spriteNum == 3)
							img = left3;
						break;
					} else if (this.attacking) {
						if (spriteNum == 1)
							img = attackLeft1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackLeft2;
						screenX -= gp.TitleSize;
						break;
					}
				case "right":
					if (!attacking) {
						if (spriteNum == 1)
							img = right1;
						else if (spriteNum == 2)
							img = right2;
						else if (spriteNum == 3)
							img = right3;
						break;
					} else if (this.attacking) {
						if (spriteNum == 1)
							img = attackRight1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackRight2;
						break;
					}
				}
			} else
				switch (direction) {
				case "up":
					if (!attacking) {
						if (spriteNum == 1)
							img = up1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = up3;
						break;
					} else if (this.attacking) {
						if (spriteNum == 1)
							img = attackUp1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackUp2;
						screenY -= gp.TitleSize;
						break;
					}
				case "down":
					if (!attacking) {
						if (spriteNum == 1)
							img = down1;
						else if (spriteNum == 2)
							img = down2;
						else if (spriteNum == 3)
							img = down3;
						break;
					} else if (this.attacking) {

						if (spriteNum == 1)
							img = attackDown1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackDown2;
						break;
					}
				case "left":
					if (!attacking) {
						if (spriteNum == 1)
							img = left1;
						else if (spriteNum == 2)
							img = left2;
						else if (spriteNum == 3)
							img = left3;
						break;
					} else if (this.attacking) {
						if (spriteNum == 1)
							img = attackLeft1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackLeft2;
						screenX -= gp.TitleSize;
						break;
					}
				case "right":
					if (!attacking) {
						if (spriteNum == 1)
							img = right1;
						else if (spriteNum == 2)
							img = right2;
						else if (spriteNum == 3)
							img = right3;
						break;
					} else if (this.attacking) {
						if (spriteNum == 1)
							img = attackRight1;
						else if (spriteNum == 2)
							img = up2;
						else if (spriteNum == 3)
							img = attackRight2;
						break;
					}
				}
			// MONSTER HP BAR
			if (type == 2 && hpBarOn) {
				double hpBarValue = (double) gp.TitleSize * currentLife / maxLife;
				g2.setColor(Color.black);
				g2.fillRect(tempX - 1, tempY - 16, gp.TitleSize, 7);
				g2.setColor(Color.red);
				g2.fillRect(tempX, tempY - 15, (int) hpBarValue, 5);
				hpBarCouter++;
				if (hpBarCouter > 480) {
					hpBarCouter = 0;
					hpBarOn = false;
				}
			}

			// SET OPACITY WHEN INVINCIBLE
			if (invincible) {
				hpBarOn = true;
				changeOpacity(g2, 0.4f);
				hpBarCouter = 0;
			}
			// ANIMATION WHEN ENTITY DIE
			if (dying) {
				dyingAnimation(g2);
			}
			// DRAW ENTITY IMAGE
			g2.drawImage(img, screenX, screenY, null);
			// RESET OPACITY
			changeOpacity(g2, 1f);
		} else if (gp.player.worldX < gp.player.screenX || gp.player.worldY < gp.player.screenY
				|| rightOffset > gp.worldWidth - gp.player.worldX || bottomOffset > gp.worldHeight - gp.player.worldY) {
			g2.drawImage(img, screenX, screenY, null);
		}

	}

	public void attackingToPlayer() {

		if (attacking) {
			boolean contactPlayer = false;
			spriteCounter++;
			if (spriteCounter <= 5) {
				if (spriteCounter == 3) {
					gp.playSingleSound(2);
					Random random = new Random();
					int j = random.nextInt(100) + 1;
					if (this.projectile != null)
						if (j < skillCastRate && !projectile.alive && projectileAvailbleCounter == 30) {
							projectile.set(worldX, worldY, direction, true, this);
							for (int i = 0; i < gp.projectileList[1].length; i++) {
								if (gp.projectileList[gp.currentMap][i] == null) {
									gp.projectileList[gp.currentMap][i] = projectile;
									break;
								}
							}
							projectileAvailbleCounter = 0;
						}
				}
				spriteNum = 1;
			} else if (spriteCounter <= 25) {
				spriteNum = 3;

				int currentWorldX = worldX;
				int currentWorldY = worldY;
				int solidAreaWidth = solidArea.width;
				int solidAreaHeight = solidArea.height;

				switch (direction) {
				case "up":
					worldY -= attackArea.height;
					break;
				case "down":
					worldY += attackArea.height;
					break;
				case "left":
					worldX -= attackArea.width;
					break;
				case "right":
					worldX += attackArea.width;
					break;
				}
				solidArea.width = attackArea.width;
				solidArea.height = attackArea.height;

				contactPlayer = gp.cChecker.checkPlayer(this);

				solidArea.width = solidAreaWidth;
				solidArea.height = solidAreaHeight;
				worldX = currentWorldX;
				worldY = currentWorldY;
			} else {
				spriteNum = 1;
				spriteCounter = 0;
				gp.stopSingleSound(2);
			}

			if (type == type_monster && contactPlayer) {
				if (!gp.player.invincible) {
					damageToPlayer(attack);
				}
			}
			attacking = true;
		}
	}

	public void dyingAnimation(Graphics2D g2) {
		dyingCouter++;
		if (dyingCouter <= 5)
			changeOpacity(g2, 0f);
		else if (dyingCouter <= 10)
			changeOpacity(g2, 1f);
		else if (dyingCouter <= 15)
			changeOpacity(g2, 0f);
		else if (dyingCouter <= 20)
			changeOpacity(g2, 1f);
		else if (dyingCouter <= 25)
			changeOpacity(g2, 0f);
		else if (dyingCouter <= 30)
			changeOpacity(g2, 1f);
		else if (dyingCouter <= 35)
			changeOpacity(g2, 0f);
		else if (dyingCouter <= 40)
			changeOpacity(g2, 1f);
		else {
			alive = false;
		}
	}

	public boolean use(Entity entity) {
		return false;
	}

	public void get(Entity entity) {
	}

	public void changeOpacity(Graphics2D g2, float opacity) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
	}

	public void damageReaction() {
	}

	public void dropItem(Entity entity) {
		for (int i = 0; i < gp.object.length; i++) {
			if (gp.object[gp.currentMap][i] == null) {
				gp.object[gp.currentMap][i] = entity;
				gp.object[gp.currentMap][i].worldX = worldX;
				gp.object[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}

	public void checkDrop() {
	}

	public void searchPath(int goalCol, int goalRow) {
		int startCol = (worldX + solidArea.x) / gp.TitleSize;
		int startRow = (worldY + solidArea.y) / gp.TitleSize;
		gp.pFinder.setNodes(startCol, startRow, goalCol, goalRow);
		if (gp.pFinder.seach()) {
			if (gp.pFinder.pathList.size() == 0)return;
			int nextX = gp.pFinder.pathList.get(0).col * gp.TitleSize;
			int nextY = gp.pFinder.pathList.get(0).row * gp.TitleSize;

			int enSlAreaLeftX = worldX + solidArea.x;
			int enSlAreaRightX = worldX + solidArea.x + solidArea.width;
			int enSlAreaTopY = worldY + solidArea.y;
			int enSlAreaBottomY = worldY + solidArea.y + solidArea.height;

			if (enSlAreaTopY > nextY && enSlAreaLeftX >= nextX 
					&& enSlAreaRightX < nextX + gp.TitleSize) {
				direction = "up";
				checkCollistion();
				if (collisionOn) {
					direction = "right";
					collisionOn = false;
					checkCollistion();
					if (collisionOn) {
						direction = "left";
					}
				}
			} else if (enSlAreaTopY < nextY && enSlAreaLeftX >= nextX && enSlAreaRightX < nextX + gp.TitleSize) {
				direction = "down";
				checkCollistion();
				if (collisionOn) {
					direction = "right";
					collisionOn = false;
					checkCollistion();
					if (collisionOn) {
						direction = "left";
					}
				}
			} else if (enSlAreaTopY >= nextY && enSlAreaBottomY < nextY + gp.TitleSize) {
				if (enSlAreaLeftX > nextX) {
					direction = "left";
				}
				if (enSlAreaLeftX < nextX) {
					direction = "right";
				}
			}
		}
	}

	public void checkCollistion() {
		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.tileInteractive);
		boolean contactPlayer = false;
		if (this != gp.player) {
			contactPlayer = gp.cChecker.checkPlayer(this);
		}

		// IF ENTITY IS MONSTER AND CONTACT PLAYER
		if (type == type_monster && contactPlayer && level != 0) {
			if (!gp.player.invincible) {
				damageToPlayer(attack);
			}
		}
	}
}