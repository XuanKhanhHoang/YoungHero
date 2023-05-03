package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import objects.Obj_Armor_Basic;
import objects.Obj_Shield_Wood;
import objects.Obj_Weapon_RedSword;
import objects.Obj_Weapon_SwordNormal;
import objects.Skill_ManaBall;

public class Player extends Entity {
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	private int couter = 0;
	public boolean attackCanceled = false, canAttack = false, defencing = false;
	public BufferedImage invisibleImage, defaultDown1, defaultDown2;
	public boolean handleMove = true;
	public int respawnCol, respawnRow;
	public String nowQuest;

	public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		projectile = new Skill_ManaBall(gp);
		screenX = (gp.screenWidth - gp.TitleSize) / 2;
		screenY = (gp.screenHeghit - gp.TitleSize) / 2;
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 31;
		getPlayerImage();
		getPlayerAttackImage();
		getPlayerDefenceImage();

		down1 = invisibleImage;
		down2 = invisibleImage;
		handleMove = false;
		respawnCol = 39;
		respawnRow = 44;

		worldX = 39 * gp.TitleSize;
		worldY = 41 * gp.TitleSize;// 45

		defaultSpeed = 4;
		speed = defaultSpeed;
		direction = "down";
		maxLife = 6;
		currentLife = maxLife;
		level = 0;
		strength = 1;
		dexterity = 0;
		maxMana = 0;
		mana = maxMana;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		nowQuest = "";
	}

	public void getPlayerDefenceImage() {
		if (currentShield != null) {
			if (currentShield.name.equals(new Obj_Shield_Wood(gp).name)) {
				try {
					defenceDown1 = ImageIO
							.read(new FileInputStream("res/player/player_defencing_woodShield_front.png"));
					defenceLeft1 = ImageIO.read(new FileInputStream("res/player/player_defencing_woodShield_left.png"));
					defenceRight1 = ImageIO
							.read(new FileInputStream("res/player/player_defencing_woodShield_right.png"));
					defenceUp1 = ImageIO.read(new FileInputStream("res/player/player_defencing_woodShield_up.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void setDefaultValue() {
		currentArmor = null;
		currentWeapon = null;
		inventory.clear();
	}

	public void setPlayerValue() {
		currentArmor = new Obj_Armor_Basic(gp);
		defense = getDefense(false);
		currentWeapon = new Obj_Weapon_SwordNormal(gp);
		attack = getAttack(false);
		coin = 500;
		inventory.add(currentArmor);
		inventory.add(currentWeapon);

		currentShield = new Obj_Shield_Wood(gp);
		inventory.add(currentShield);

	}

	public void setRespawnPosition() {
		worldX = respawnCol * gp.TitleSize;
		worldY = respawnRow * gp.TitleSize;

	}

	public void restoreLifeAndMana() {
		currentLife = maxLife;
		mana = maxMana;
	}

	public int getDefense(boolean return0) {
		if (return0)
			return 0;
		return dexterity + currentArmor.defenseValue;
	}

	public int getAttack(boolean return0) {
		if (return0)
			return 0;
		this.attackArea = currentWeapon.attackArea;
		return strength + currentWeapon.attackValue;
	}

	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(new FileInputStream("res/player/player_running_behind_rf.png"));
			up2 = ImageIO.read(new FileInputStream("res/player/player_running_behind_e.png"));
			up3 = ImageIO.read(new FileInputStream("res/player/player_running_behind_lf.png"));
			down1 = ImageIO.read(new FileInputStream("res/player/player_running_front_rf.png"));
			down2 = ImageIO.read(new FileInputStream("res/player/player_running_front_e.png"));
			down3 = ImageIO.read(new FileInputStream("res/player/player_running_front_lf.png"));
			left1 = ImageIO.read(new FileInputStream("res/player/player_running_left_rf.png"));
			left2 = ImageIO.read(new FileInputStream("res/player/player_running_left_e.png"));
			left3 = ImageIO.read(new FileInputStream("res/player/player_running_left_lf.png"));
			right1 = ImageIO.read(new FileInputStream("res/player/player_running_right_rf.png"));
			right2 = ImageIO.read(new FileInputStream("res/player/player_running_right_e .png"));
			right3 = ImageIO.read(new FileInputStream("res/player/player_running_right_lf.png"));
			invisibleImage = ImageIO.read(new FileInputStream("res/player/invisiblePlayer.png"));
			defaultDown1 = ImageIO.read(new FileInputStream("res/player/player_running_front_rf.png"));
			defaultDown2 = ImageIO.read(new FileInputStream("res/player/player_running_front_lf.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getPlayerAttackImage() {
		if (currentWeapon == null) {
			try {
				attackUp1 = ImageIO.read(new FileInputStream("res/player/player_attack_up_1.png"));
				attackUp2 = ImageIO.read(new FileInputStream("res/player/player_attack_up_2.png"));
				attackDown1 = ImageIO.read(new FileInputStream("res/player/player_attack_down_1.png"));
				attackDown2 = ImageIO.read(new FileInputStream("res/player/player_attack_down_2.png"));
				attackLeft1 = ImageIO.read(new FileInputStream("res/player/player_attack_left_1.png"));
				attackLeft2 = ImageIO.read(new FileInputStream("res/player/player_attack_left_2.png"));
				attackRight1 = ImageIO.read(new FileInputStream("res/player/player_attack_right_1.png"));
				attackRight2 = ImageIO.read(new FileInputStream("res/player/player_attack_right_2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (currentWeapon.name.equals(new Obj_Weapon_SwordNormal(gp).name)) {
			try {
				attackUp1 = ImageIO.read(new FileInputStream("res/player/player_attack_up_1.png"));
				attackUp2 = ImageIO.read(new FileInputStream("res/player/player_attack_up_2.png"));
				attackDown1 = ImageIO.read(new FileInputStream("res/player/player_attack_down_1.png"));
				attackDown2 = ImageIO.read(new FileInputStream("res/player/player_attack_down_2.png"));
				attackLeft1 = ImageIO.read(new FileInputStream("res/player/player_attack_left_1.png"));
				attackLeft2 = ImageIO.read(new FileInputStream("res/player/player_attack_left_2.png"));
				attackRight1 = ImageIO.read(new FileInputStream("res/player/player_attack_right_1.png"));
				attackRight2 = ImageIO.read(new FileInputStream("res/player/player_attack_right_2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (currentWeapon.name.equals(new Obj_Weapon_RedSword(gp).name)) {
			try {
				attackUp1 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_up_1.png"));
				attackUp2 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_up_2.png"));
				attackDown1 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_down_1.png"));
				attackDown2 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_down_2.png"));
				attackLeft1 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_left_1.png"));
				attackLeft2 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_left_2.png"));
				attackRight1 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_right_1.png"));
				attackRight2 = ImageIO.read(new FileInputStream("res/player/player_attack_redSword_right_2.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void getObj(int k) {
		String msg = "";
		if (gp.object[gp.currentMap][k].type == type_pickUpOnly) {
			gp.object[gp.currentMap][k].get(this);
			gp.object[gp.currentMap][k] = null;
		} else if (gp.object[gp.currentMap][k].type == type_obstacle) {
			
		} else {
			if (canObtainItem(gp.object[gp.currentMap][k])) {
				gp.playSingleSound(1);
				if (gp.object[gp.currentMap][k].name == "Special Grass") {
					int i = 0;
					while (i < gp.player.inventory.size())
						i++;
					if (i < gp.player.inventory.size()) {
						gp.player.inventory.remove(i);
					}
					gp.gameState = gp.storyState;
				} else if (gp.object[gp.currentMap][k].name == "Special Herb") {
					gp.gameState = gp.transitionSubState;
					gp.exeption = 9;
					gp.counter1 = 50;
					gp.counter = 0;
				}

				gp.object[gp.currentMap][k] = null;

			} else {
				msg = "You can't carry more!";
			}
			gp.ui.addMessage(msg);
		}
	}

	public void update() {
		if (handleMove) {
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
				int npcIndex = 999;
				if (attacking && canAttack && !defencing) {
					attacking();
					if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
						if (keyH.upPressed) {
							direction = "up";
						} else if (keyH.downPressed) {
							direction = "down";
						} else if (keyH.leftPressed) {
							direction = "left";
						} else if (keyH.rightPressed) {
							direction = "right";
						}
					}
				} else if (defencing && currentShield != null) {
					defense = getDefense(false) + currentShield.defenseValue;
				} else {
					if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
						if (keyH.upPressed) {
							direction = "up";
						} else if (keyH.downPressed) {
							direction = "down";
						} else if (keyH.leftPressed) {
							direction = "left";
						} else if (keyH.rightPressed) {
							direction = "right";
						}
						spriteCounter++;
						if (spriteCounter > 12) {
							if (spriteNum == 2)
								spriteNum = 1;
							else if (spriteNum == 1)
								spriteNum = 3;
							else if (spriteNum == 3)
								spriteNum = 1;
							spriteCounter = 0;
						}
					} else if (couter > 30) {
						spriteNum = 2;
						couter = 0;
					} else {
						couter++;
					}
				}

				collisionOn = false;
				gp.cChecker.checkTile(this);
				int objIndex = gp.cChecker.checkObject(this, true);
				if (objIndex != 999)
					gp.player.getObj(objIndex);
				npcIndex = gp.cChecker.checkEntity(this, gp.npc);
				gp.cChecker.checkEntity(this, gp.monster);
				gp.eventHandler.checkEvent();
				gp.cChecker.checkEntity(this, gp.tileInteractive);
				if (!collisionOn && !defencing) {
					if (keyH.upPressed) {
						worldY -= speed;
					} else if (keyH.downPressed) {
						worldY += speed;
					} else if (keyH.leftPressed) {
						worldX -= speed;
					} else if (keyH.rightPressed) {
						worldX += speed;
					}
				}
				if (gp.keyHandler.enterPressed && !attackCanceled) {
					attacking = true;
					spriteCounter = 0;
				}
				attackCanceled = false;
				if (invincible) {
					invincibleCout++;
					if (invincibleCout > 60) {
						invincible = false;
						invincibleCout = 0;
					}
				}
				if (projectileAvailbleCounter < 30) {
					projectileAvailbleCounter++;
				}
				if (keyH.shotKeyPressed && !projectile.alive && projectileAvailbleCounter == 30
						&& projectile.haveResource(this)) {
					projectile.set(worldX, worldY, direction, true, this);
					projectile.subtractResource(this);
					for (int i = 0; i < gp.projectileList[1].length; i++) {
						if (gp.projectileList[gp.currentMap][i] == null) {
							gp.projectileList[gp.currentMap][i] = projectile;
							break;
						}
					}
					gp.playSingleSound(3);
					projectileAvailbleCounter = 0;
				}
				if (npcIndex != 999) {
					attackCanceled = true;
					interractNPC(npcIndex);
				}
				if (currentLife <= 0) {
					gp.gameState = gp.gameOverState;
					gp.ui.commandNum = -1;
				}
				gp.keyHandler.enterPressed = false;
			}

		}

	}

	public void selectItem() {
		int itemIndex = gp.ui.getItemIndex(gp.ui.playerSlotCol, gp.ui.playerSlotRow);
		if (itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			if (selectedItem.type == type_sword ) {
				this.currentWeapon = selectedItem;
				this.attack = getAttack(false);
				getPlayerAttackImage();
			} else if (selectedItem.type == type_armor) {
				this.currentArmor = selectedItem;
				this.defense = getDefense(false);
			}
			if (selectedItem.type == type_consumable) {
				if (selectedItem.use(this)) {
					if (selectedItem.amount > 1) {
						selectedItem.amount--;
					} else
						inventory.remove(itemIndex);
				}
			}
		}
	}

	public int seachItemInventory(String itemName) {
		int itemIndex = 999;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i).name.equals(itemName)) {
				itemIndex = i;
				return itemIndex;
			}
		}
		return itemIndex;
	}

	public boolean canObtainItem(Entity item) {
		boolean canObtain = false;
		if (item.stackble) {
			int index = seachItemInventory(item.name);
			if (index != 999) {
				inventory.get(index).amount++;
				canObtain = true;
			} else {
				if (inventory.size() < maxInventorySize) {
					inventory.add(item);
					canObtain = true;
				}
			}
		} else {
			if (inventory.size() < maxInventorySize) {
				inventory.add(item);
				canObtain = true;
			}
		}
		return canObtain;
	}

	private void attacking() {
		spriteCounter++;
		if (spriteCounter <= 5) {
			if (spriteCounter == 3)
				gp.playSingleSound(2);
			spriteNum = 1;
		} else if (spriteCounter <= 25) {

			spriteNum = 2;
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

			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			if (monsterIndex != 999) {
				damageMonster(monsterIndex, attack, currentWeapon.knockBackPower,true);
			}
			int itTileIndex = gp.cChecker.checkEntity(this, gp.tileInteractive);
			if (itTileIndex != 999) {
				damageInteractiveTile(itTileIndex);
			}
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			worldX = currentWorldX;
			worldY = currentWorldY;

		} else {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
			gp.stopSingleSound(2);
		}

	}

	public void damageInteractiveTile(int itTileIndex) {
		if (gp.tileInteractive[gp.currentMap][itTileIndex].destructible
				&& gp.tileInteractive[gp.currentMap][itTileIndex].isCorrectItem(currentWeapon)
				&& !gp.tileInteractive[gp.currentMap][itTileIndex].invincible) {
			gp.tileInteractive[gp.currentMap][itTileIndex].currentLife--;
			gp.tileInteractive[gp.currentMap][itTileIndex].invincible = true;

			if (gp.tileInteractive[gp.currentMap][itTileIndex].currentLife <= 0) {
				gp.tileInteractive[gp.currentMap][itTileIndex] = gp.tileInteractive[gp.currentMap][itTileIndex]
						.getDestoyedForm();
			}
		}
	}

	public void damageMonster(int monsterIndex, int attack, int knockBackPower,boolean canCritical) {
		if (!gp.monster[gp.currentMap][monsterIndex].invincible && !gp.monster[gp.currentMap][monsterIndex].dying) {

			knockBack(gp.monster[gp.currentMap][monsterIndex], knockBackPower);
			int tempAtk=attack;
			if(canCritical) {
				Random random =new Random();
				int j = random.nextInt(100) + 1;
				if(j<=this.currentWeapon.criticalChance ) {
					tempAtk*=2;
				}
			}
			int damage = tempAtk - gp.monster[gp.currentMap][monsterIndex].defense;
			if (damage < 0) {
				damage = 0;
			}

			gp.monster[gp.currentMap][monsterIndex].currentLife -= damage;
			gp.ui.addMessage(damage + " damage !");

			gp.monster[gp.currentMap][monsterIndex].invincible = true;
			if (gp.monster[gp.currentMap][monsterIndex].currentLife <= 0) {
				gp.monster[gp.currentMap][monsterIndex].dying = true;
				exp += gp.monster[gp.currentMap][monsterIndex].exp;
				gp.ui.addMessage("Killed Monster " + gp.monster[gp.currentMap][monsterIndex].name + " !");
				gp.ui.addMessage("Exp + " + gp.monster[gp.currentMap][monsterIndex].exp + "!");
				checkLevelUp();
			} else
				gp.monster[gp.currentMap][monsterIndex].damageReaction();
		}
	}

	private void checkLevelUp() {
		if (exp >= this.nextLevelExp) {
			level++;
			exp -= nextLevelExp;
			nextLevelExp *= 2;
			maxLife *= 1.2;
			maxMana = (int) (level + 3) / 2;
			strength += (int) ((level * 0.3) + 0.8);
			dexterity += (int) ((level * 0.3) + 0.8);
			attack = getAttack(false);
			defense = getDefense(false);
			currentLife = maxLife;
			mana = maxMana;
			gp.gameState = gp.dialogState;
			gp.ui.currentDialog = "You are level " + level + " now";
		}
	}

	private void interractNPC(int npcIndex) {
		if (gp.keyHandler.enterPressed) {
			gp.gameState = gp.dialogState;
			gp.npc[gp.currentMap][npcIndex].speak();
			gp.keyHandler.enterPressed = false;
		}
	}

	public void draw(Graphics2D g2) {
		if (!canAttack)
			attacking = false;
		BufferedImage img = null;
		int x = this.screenX, y = this.screenY;
		// STOP MOVING CAMERA

		if (screenX > worldX) {
			x = worldX;
		}
		if (screenY > worldY) {
			y = worldY;
		}

		int rightOffset = gp.screenWidth - gp.player.screenX;
		int bottomOffset = gp.screenHeghit - gp.player.screenY;
		if (rightOffset > gp.worldWidth - gp.player.worldX) {
			x = gp.screenWidth - (gp.worldWidth - worldX);
		}
		if (bottomOffset > gp.worldWidth - gp.player.worldY) {
			y = gp.screenHeghit - (gp.worldHeight - worldY);
		}
		if (knockBack) {
			switch (tempDirection) {
			case "up":
				if (defencing) {
					img = defenceUp1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = up1;
					else if (spriteNum == 2)
						img = up2;
					else if (spriteNum == 3)
						img = up3;
				} else {
					if (spriteNum == 1)
						img = attackUp1;
					else if (spriteNum == 2)
						img = attackUp2;
					y -= gp.TitleSize;
				}
				break;
			case "down":
				if (defencing) {
					img = defenceDown1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = down1;
					else if (spriteNum == 2)
						img = down2;
					else if (spriteNum == 3)
						img = down3;
				} else {
					if (spriteNum == 1)
						img = attackDown1;
					else if (spriteNum == 2)
						img = attackDown2;
				}
				break;
			case "left":
				if (defencing) {
					img = defenceLeft1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = left1;
					else if (spriteNum == 2)
						img = left2;
					else if (spriteNum == 3)
						img = left3;
				} else {
					if (spriteNum == 1)
						img = attackLeft1;
					else if (spriteNum == 2)
						img = attackLeft2;
					x -= gp.TitleSize;
				}
				break;
			case "right":
				if (defencing) {
					img = defenceRight1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = right1;
					else if (spriteNum == 2)
						img = right2;
					else if (spriteNum == 3)
						img = right3;
				} else {
					if (spriteNum == 1)
						img = attackRight1;
					else if (spriteNum == 2)
						img = attackRight2;
				}
				break;
			}
		} else {
			switch (direction) {
			case "up":
				if (defencing) {
					img = defenceUp1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = up1;
					else if (spriteNum == 2)
						img = up2;
					else if (spriteNum == 3)
						img = up3;
				} else {
					if (spriteNum == 1)
						img = attackUp1;
					else if (spriteNum == 2)
						img = attackUp2;
					y -= gp.TitleSize;
				}
				break;
			case "down":
				if (defencing) {
					img = defenceDown1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = down1;
					else if (spriteNum == 2)
						img = down2;
					else if (spriteNum == 3)
						img = down3;
				} else {
					if (spriteNum == 1)
						img = attackDown1;
					else if (spriteNum == 2)
						img = attackDown2;
				}
				break;
			case "left":
				if (defencing) {
					img = defenceLeft1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = left1;
					else if (spriteNum == 2)
						img = left2;
					else if (spriteNum == 3)
						img = left3;
				} else {
					if (spriteNum == 1)
						img = attackLeft1;
					else if (spriteNum == 2)
						img = attackLeft2;
					x -= gp.TitleSize;
				}
				break;
			case "right":
				if (defencing) {
					img = defenceRight1;
				} else if (!attacking) {
					if (spriteNum == 1)
						img = right1;
					else if (spriteNum == 2)
						img = right2;
					else if (spriteNum == 3)
						img = right3;
				} else {
					if (spriteNum == 1)
						img = attackRight1;
					else if (spriteNum == 2)
						img = attackRight2;
				}
				break;
			}
		}

		if (invincible && !defencing) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(img, null, x, y);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
}
