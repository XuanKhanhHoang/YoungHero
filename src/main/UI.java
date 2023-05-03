package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Entity;

public class UI {
	GamePanel gp;
	Graphics2D g2;
	public BufferedImage  backgroundTitleScreen;
	private ArrayList<String> message = new ArrayList<>();
	private ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinish = false;
	public String currentDialog = "";
	public int commandNum = 0, subCommandNum = 0;
	public int titleScreenState = 0;
	public int playerSlotCol = 0, playerSlotRow = 0;
	public int npcSlotCol = 0, npcSlotRow = 0;
	private Font arial_40, MisterPixel16, MaruMonica;
	public int subOptionSubNum = 0, subOptionNum = 0;
	public int counter = 0;
	public Entity npc;
	public BufferedImage avatarPlayer;

	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		// GET FONT
		try {
			FileInputStream fip = new FileInputStream("res/fonts/MisterPixel16.ttf");
			try {
				MisterPixel16 = Font.createFont(Font.TRUETYPE_FONT, fip);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fip = new FileInputStream("res/fonts/x12y16pxMaruMonica.ttf");
			try {
				MaruMonica = Font.createFont(Font.TRUETYPE_FONT, fip);
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			backgroundTitleScreen = ImageIO.read(new FileInputStream("res/maps/youngheroBGr.png"));
			avatarPlayer= ImageIO.read(new FileInputStream("res/player/avartarPlayer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addMessage(String msg) {
		message.add(msg);
		messageCounter.add(0);
	}

	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(arial_40);
		g2.setColor(Color.WHITE);
		if (gp.gameState == gp.playState) {
			if(!gp.handleStory.npcUpdate)drawPlayerLife();
			drawMessage();
		} else if (gp.gameState == gp.dialogState) {
			drawDialogScreen();
		} else if (gp.gameState == gp.titleState) {
			drawTitleScreen();
		} else if (gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory(gp.player, true, false, false);
		} else if (gp.gameState == gp.optionState) {
			drawOptionScreen();
		} else if (gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
		}  else if (gp.gameState == gp.tradeState) {
			drawTradeScreen();
		} else if (gp.gameState == gp.storyState) {
			drawDialogStory();
		}

	}

	private void drawDialogStory() {
		currentDialog = gp.handleStory.dialogStory[gp.handleStory.turn][gp.handleStory.numDialog];
		g2.setColor(new Color(147,213,22));
		drawDialogScreen();

	}

	private void drawTradeScreen() {
		g2.setFont(MisterPixel16.deriveFont(20f));
		g2.setColor(new Color(147,213,22));
		switch (subCommandNum) {
		case 0:
			tradeSelect();
			break;
		case 1:
			tradeBuy();
			break;
		case 2:
			tradeSell();
			break;
		}
	}

	private void tradeSell() {
		drawInventory(npc, false, true, true);
		drawInventory(gp.player, true, true, true);
		drawSubWindowRound5((int) (gp.TitleSize * 1.5), gp.screenHeghit - 48 * 2, gp.TitleSize * 3, gp.TitleSize);
		drawSubWindowRound5(gp.TitleSize * 12, gp.screenHeghit - 48 * 2, gp.TitleSize * 3, gp.TitleSize);
		g2.drawString("[ESC] Back", gp.TitleSize * 2, (int) (gp.screenHeghit - 48 * 1.4 + 3));
		g2.drawString("Your coin : " + gp.player.coin, (int) (gp.TitleSize * 12.2),
				(int) (gp.screenHeghit - 48 * 1.5 + 5));
	}

	private void tradeBuy() {
		drawInventory(gp.player, false, true, false);
		drawInventory(npc, true, true, false);
		drawSubWindowRound5((int) (gp.TitleSize * 1.5), gp.screenHeghit - 48 * 2, gp.TitleSize * 3, gp.TitleSize);
		g2.drawString("[ESC] Back", gp.TitleSize * 2, (int) (gp.screenHeghit - 48 * 1.4 + 3));
		drawSubWindowRound5(gp.TitleSize * 12, gp.screenHeghit - 48 * 2, gp.TitleSize * 3, gp.TitleSize);
		g2.drawString("Your coin : " + gp.player.coin, (int) (gp.TitleSize * 12.2),
				(int) (gp.screenHeghit - 48 * 1.5 + 5));
	}

	private void tradeSelect() {
		drawDialogScreen();
		int frameX = gp.TitleSize * 11;
		int x = 48, y = 48;
		int btnX;
		Color btnBlue = new Color(7,147,12);
		Color white = Color.white;
		int btnHeight=36,btnWidth=102,roundBtn=2;
		int btnLeftSpace=38,btnTopSpace=28;
		y += 29;
		String text="Buy";
		x = getCenteredTextInFrameX(text, frameX, frameX+btnWidth);
		btnX=x;
		x=getCenteredTextInFrameX(text, btnX-btnLeftSpace, btnX-btnLeftSpace+btnWidth);
		g2.setColor(white);
		g2.drawRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
		if (commandNum == 0) {
			g2.setColor(btnBlue);
			g2.fillRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
			g2.setColor(white);
			g2.drawRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
			g2.setColor(white);
			g2.drawString(text, x, y);
			if (gp.keyHandler.enterPressed) {
				gp.keyHandler.enterPressed = false;
				subCommandNum = 1;
				return;
			}
		}
		else {
			g2.drawString(text, x, y);
		}
		y += 50;
		text="Sell";
		x=getCenteredTextInFrameX(text, btnX-btnLeftSpace, btnX-btnLeftSpace+btnWidth);
		g2.setColor(white);
		g2.drawRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
		if (commandNum == 1) {
			g2.setColor(btnBlue);
			g2.fillRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
			g2.setColor(white);
			g2.drawRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
			g2.setColor(white);
			g2.drawString(text, x, y);
			if (gp.keyHandler.enterPressed) {
				subCommandNum = 2;
				gp.keyHandler.enterPressed = false;
				return;
			}
		}else {
			g2.drawString(text, x, y);
		}
		y += 50;
		text="Back";
		x=getCenteredTextInFrameX(text, btnX-btnLeftSpace, btnX-btnLeftSpace+btnWidth);
		g2.setColor(white);
		g2.drawRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
		if (commandNum == 2) {
			g2.setColor(btnBlue);
			g2.fillRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
			g2.setColor(white);
			g2.drawRoundRect(btnX-btnLeftSpace, y-btnTopSpace, btnWidth,btnHeight ,roundBtn,roundBtn);
			g2.setColor(white);
			g2.drawString(text, x, y);
			if (gp.keyHandler.enterPressed) {
				commandNum = 0;
				currentDialog = "I hope to see you again";
				gp.gameState = gp.dialogState;
				subCommandNum = 0;
				gp.keyHandler.enterPressed = false;
				return;
			}
		}else {
			g2.drawString(text, x, y);
		}

	}


	private void drawGameOverScreen() {
		g2.setColor(new Color(0, 0, 0, 0.3f));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeghit);

		int x, y;
		String text = "Game Over";
		g2.setFont(MaruMonica.deriveFont(Font.BOLD, 70f));

		g2.setColor(Color.black);
		x = getCenteredTextX(text);
		y = gp.TitleSize * 4;
		g2.drawString(text, x, y);

		g2.setColor(Color.white);
		g2.drawString(text, x - 3, y - 3);

		text = "Retry";
		g2.setFont(MaruMonica.deriveFont(Font.BOLD, 40f));
		x = getCenteredTextX(text);
		y += gp.TitleSize * 4;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - 25, y);
			if (gp.keyHandler.enterPressed) {
				gp.retry();
				gp.player.invincible = false;
				gp.player.direction = "down";
				gp.gameState = gp.playState;
				gp.keyHandler.enterPressed = false;
				gp.player.attacking = false;
				return;
			}
		}

		text = "Quit";
		g2.setFont(MaruMonica.deriveFont(Font.BOLD, 40f));
		x = getCenteredTextX(text);
		y += (int) gp.TitleSize * 1.1;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - 25, y);
			if (gp.keyHandler.enterPressed) {
				gp.gameState = gp.titleState;
				commandNum = 0;
				gp.music.stop();
				gp.keyHandler.enterPressed = false;
				return;
			}
		}
	}

	private void drawOptionScreen() {

		g2.setFont(this.MaruMonica.deriveFont(31f));

		// DRAW INVENTORY WINDOW
		int frameX = gp.TitleSize * 4;
		int frameY = gp.TitleSize;
		int frameWidth = gp.TitleSize * 8;
		int frameHeight = gp.TitleSize * 10;
		drawSubWindow(frameX, frameY, frameWidth, frameHeight,true);

		g2.setColor(new Color(147,213,22));
		switch (subOptionNum) {
		case 0:
			optionTop(frameX, frameY);
			break;
		case 1:
			optionControl(frameX, frameY);
			break;
		case 2:
			optionExit(frameX, frameY);
			break;
		}

		gp.keyHandler.enterPressed = false;

	}

	private void optionExit(int frameX, int frameY) {
		int textX = frameX + gp.TitleSize;
		int textY = frameY + gp.TitleSize * 3;
		currentDialog = "Are you sure \nback to main menu!";
		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, textX, textY);
			textY += 40;
		}

		String text = "Yes";
		textX = getCenteredTextX(text);
		textY += gp.TitleSize * 3;
		g2.drawString(text, textX, textY);
		if (subOptionSubNum == 0) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyHandler.enterPressed) {
				subOptionNum = 0;
				gp.gameState = gp.titleState;
				gp.ui.commandNum = 0;
				gp.ui.titleScreenState = 0;
				gp.music.stop();
				return;
			}
		}

		text = "No";
		textX = getCenteredTextX(text);
		textY += gp.TitleSize;
		g2.drawString(text, textX, textY);
		if (subOptionSubNum == 1) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyHandler.enterPressed) {
				subOptionNum = 0;
				return;
			}
		}
	}

	private void optionControl(int frameX, int frameY) {
		int textX, textY;
		String text = "Control";
		textX = getCenteredTextX(text);
		textY = frameY + gp.TitleSize;
		g2.drawString(text, textX, textY);

		textX = frameX + gp.TitleSize;
		textY += gp.TitleSize;
		g2.drawString("Move", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("Confirm / Attack", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("Cast Special Skill", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("Character Screen", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("Option", textX, textY);
		textY += gp.TitleSize;

		textX = frameX + gp.TitleSize * 6;
		textY = frameY + gp.TitleSize * 2;
		g2.drawString("WASD", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("Enter", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("1", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("C", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("P", textX, textY);
		textY += gp.TitleSize;
		g2.drawString("ESC", textX, textY);
		textY += gp.TitleSize;

		textY += gp.TitleSize;
		textX = frameX + gp.TitleSize;
		g2.drawString("Back", textX, textY);
		g2.drawString(">", textX - 25, textY);
		if (gp.keyHandler.enterPressed) {
			subOptionNum = 0;
			return;
		}
	}

	private void optionTop(int frameX, int frameY) {
		int textX, textY;
		String text = "Options";
		textX = this.getCenteredTextX(text);
		textY = frameY + gp.TitleSize;// TitleSize*2
		g2.drawString(text, textX, textY);

		textX = frameX + gp.TitleSize;// TitleSize*5
		textY += gp.TitleSize * 2;// TitleSize*3+frameY

		// DRAW PROPERTY
		g2.drawString("Music", textX, textY);
		if (this.subOptionSubNum == 0) {
			g2.drawString(">", textX - 25, textY);
		}

		textY += gp.TitleSize;
		g2.drawString("Sound", textX, textY);
		if (this.subOptionSubNum == 1) {
			g2.drawString(">", textX - 25, textY);
		}

		textY += gp.TitleSize;
		g2.drawString("Control", textX, textY);
		if (this.subOptionSubNum == 2) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyHandler.enterPressed) {
				subOptionNum = 1;
				return;
			}
		}

		textY += gp.TitleSize;
		g2.drawString("Save", textX, textY);
		if (this.subOptionSubNum == 3) {
			g2.drawString(">", textX - 25, textY);
		}

		textY += gp.TitleSize;
		g2.drawString("Exit", textX, textY);
		if (this.subOptionSubNum == 4) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyHandler.enterPressed) {
				subOptionNum = 2;
				subOptionSubNum = 0;
				return;
			}
		}
		// BACK BUTTON
		textY += gp.TitleSize * 2;
		g2.drawString("Back", textX, textY);
		if (this.subOptionSubNum == 5) {
			g2.drawString(">", textX - 25, textY);
			if (gp.keyHandler.enterPressed) {
				gp.gameState = gp.playState;
				return;
			}
		}
		// DRAW PROPERTY'S VALUE
		textX = gp.TitleSize * 5 + frameX;
		textY = (int) (frameY + gp.TitleSize * 2.55);

		g2.setColor(new Color(255,255,255));
		g2.drawRect(textX, textY, 120+2, 24);// 120/5=24
		int volumeWidth = 24 * gp.music.volumeScale;
		g2.setColor(new Color(20,27,236));
		g2.fillRect(textX+1, textY+1, volumeWidth, 22);

		textX = gp.TitleSize * 5 + frameX;
		textY = (int) (frameY + gp.TitleSize * 3.55);
		
		g2.setColor(new Color(255,255,255));
		g2.drawRect(textX, textY, 120+2, 24);
		volumeWidth = 24 * gp.singleSound.volumeScale;
		g2.setColor(new Color(20,27,236));
		g2.fillRect(textX+1, textY+1, volumeWidth, 22);
	}

	private void drawInventory(Entity entity, boolean cursor, boolean trading, boolean selling) {
		int frameX = 0;
		int frameY = 0;
		int frameWidth = 0;
		int frameHeight = 0;
		int slotRow = 0;
		int slotCol = 0;

		// DRAW INVENTORY WINDOW
		if (entity == gp.player) {
			frameX = gp.TitleSize * 9;
			frameY = gp.TitleSize;
			frameWidth = gp.TitleSize * 6;
			frameHeight = gp.TitleSize * 5;
			slotCol = playerSlotCol;
			slotRow = playerSlotRow;
		} else {
			frameX = (int) (gp.TitleSize * 1.5);
			frameY = gp.TitleSize;
			frameWidth = gp.TitleSize * 6;
			frameHeight = gp.TitleSize * 5;
			slotCol = npcSlotCol;
			slotRow = npcSlotRow;
		}
		drawSubWindow(frameX, frameY, frameWidth, frameHeight,true);

		g2.setColor(new Color(255,255,255));
		
		// DRAW SLOT AND ITEM
		final int slotStartX = frameX + 20;
		final int slotStartY = frameY + 20;
		int slotX = slotStartX;
		int slotY = slotStartY;
		final int slotSize = gp.TitleSize + 3;

		for (int i = 0; i < entity.inventory.size(); i++) {
			if (entity.inventory.get(i) == entity.currentWeapon || entity.inventory.get(i) == entity.currentArmor
					|| entity.inventory.get(i) == entity.currentShield) {
				g2.setColor(Color.green);
				g2.fillRoundRect(slotX, slotY + 2, gp.TitleSize + 2, gp.TitleSize + 3, 0, 0);
			}
			g2.drawImage(entity.inventory.get(i).down1, slotX, slotY + 4, gp.TitleSize, gp.TitleSize, null);
			if (entity.inventory.get(i).amount > 1 && entity == gp.player) {
				g2.setFont(g2.getFont().deriveFont(20f));
				String s = "" + entity.inventory.get(i).amount;
				int amoutX = getAlignRightTextX(s, slotX + 43);
				int amoutY = slotY + gp.TitleSize;
				g2.setColor(Color.white);
				g2.drawString(s, amoutX, amoutY);
			}
			slotX += slotSize;
			if (i == 4 || i == 9 || i == 14) {
				slotX = slotStartX;
				slotY += gp.TitleSize + 2;
			}
		}
		// DRAW CURSOR
		if (cursor) {
			int cursorX = slotStartX + slotSize * slotCol;
			int cursorY = slotStartY + slotSize * slotRow;
			int cursorWidth = slotSize;
			int cursorHeight = slotSize;
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(3));
			g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 0, 0);

			// DRAW DESCRIPTION WINDOW
			int itemIndex = getItemIndex(slotCol, slotRow);
			if (itemIndex < entity.inventory.size()) {
				int dFrameX = frameX;
				int dFrameY = frameY + frameHeight + 20;
				int dFrameWidth = frameWidth;
				int dFrameHeight = gp.TitleSize * 3;
				int textX = dFrameX + 20;
				int textY = dFrameY + gp.TitleSize;
				g2.setFont(MisterPixel16.deriveFont(20f));
				drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight,true);
				g2.setColor(new Color(193,241,196));
				for (String line : entity.inventory.get(itemIndex).description.split("\n")) {
					g2.drawString(line, textX, textY);
					textY += 28;
				}
				if (trading) {
					drawSubWindowRound5(frameX + frameWidth - 96, frameY + frameHeight - 16, 96, gp.TitleSize);
					g2.drawString("Price: " + entity.inventory.get(itemIndex).coin, frameX + frameWidth - 96 + 5,
							frameY + frameHeight + 16);
					if (gp.keyHandler.enterPressed) {
						processTrading(selling, cursorX, cursorY, cursorWidth, cursorHeight, itemIndex, entity);
						gp.keyHandler.enterPressed = false;
					}
				}
			}
		}

	}

	private void processTrading(boolean selling, int cursorX, int cursorY, int cursorWidth, int cursorHeight,
			int itemIndex, Entity entity) {
		if (selling) {
			if (entity.inventory.get(itemIndex) == gp.player.currentArmor
					|| entity.inventory.get(itemIndex) == gp.player.currentWeapon
					|| entity.inventory.get(itemIndex) == gp.player.currentShield) {
				gp.gameState = gp.dialogState;
				currentDialog = "You can't sell equipment item";
				drawDialogScreen();
			} else {
				if (entity.inventory.get(itemIndex).amount > 1) {
					entity.inventory.get(itemIndex).amount--;
					entity.coin += entity.inventory.get(itemIndex).coin;
				} else {
					entity.coin += entity.inventory.get(itemIndex).coin;
					entity.inventory.remove(itemIndex);
				}

			}
		} else {
			if (gp.player.coin < entity.inventory.get(itemIndex).coin) {
				gp.gameState = gp.dialogState;
				currentDialog = "You need more coin !";
				drawDialogScreen();
			} else {
				if (gp.player.canObtainItem(entity.inventory.get(itemIndex))) {
					gp.player.coin -= entity.inventory.get(itemIndex).coin;
				} else {

					gp.gameState = gp.dialogState;
					currentDialog = "You can't carry more !";
					drawDialogScreen();
				}

			}
		}
	}

	public int getItemIndex(int slotCol, int slotRow) {
		return slotCol + (slotRow * 5);
	}

	private void drawMessage() {
		int messageX = gp.TitleSize;
		int messageY = gp.TitleSize * 4;
		g2.setFont(MaruMonica);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
		for (int i = 0; i < message.size(); i++) {
			if (message.get(i) != null) {
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);

				int counter = messageCounter.get(i) + 1;
				messageCounter.set(i, counter);
				messageY += 50;

				if (messageCounter.get(i) > 150) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}

	}

	private void drawCharacterScreen() {
		int frameX = gp.TitleSize;
		int frameY = 20;
		int frameWidth = gp.TitleSize * 5;
		int frameheight = gp.TitleSize * 11;
		drawSubWindow(frameX, frameY, frameWidth, frameheight,new Color(0,0,0));
		
		g2.setColor(new Color(245, 229, 186));
		g2.setFont(g2.getFont().deriveFont(25f));
		g2.drawString("Character Status", getCenteredTextInFrameX("Character Status", frameX, frameX+frameWidth),frameY+23);
		g2.setColor(new Color(4,64,118));
		g2.drawLine(frameX, frameY+28, frameX+frameWidth, frameY+28);

		g2.setColor(new Color(147,213,22));
		g2.setFont(this.arial_40);
		g2.setFont(g2.getFont().deriveFont(22f));

		int textX = frameX + 20;
		int textY = frameY + gp.TitleSize+3;
		int lineHeight = 32;
		Color lineColor=new Color(245, 229, 186,80);
		Color textColor= new Color(147,213,22);
		g2.drawString("Level", textX, textY);
		g2.setColor(lineColor);
		g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);
		g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("HP", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Mana", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Strength", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Dexterity", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Attack", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Defense", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Exp", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Next Level Exp", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Coin", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+12, frameX+frameWidth-2, textY+12);g2.setColor(textColor);
		textY += lineHeight + 9;
		g2.drawString("Weapon", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+4, frameX+frameWidth-2, textY+4);g2.setColor(textColor);
		textY += lineHeight;
		g2.drawString("Armor", textX, textY);
		g2.setColor(lineColor);g2.drawLine(frameX+2, textY+5, frameX+frameWidth-2, textY+5);g2.setColor(textColor);;
		textY += lineHeight + 5;
		g2.drawString("Now Quest: ", textX, textY);

		int tailX = frameX + frameWidth - 30;
		textY = frameY + gp.TitleSize+3; // RESET TEXT_Y
		String value;

		value = String.valueOf(gp.player.level);
		textX = getAlignRightTextX(value, tailX);
		
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.currentLife + " / " + gp.player.maxLife);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.mana + " / " + gp.player.maxMana);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.strength);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.dexterity);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.attack);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.defense);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.exp);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.nextLevelExp);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;

		value = String.valueOf(gp.player.coin);
		textX = getAlignRightTextX(value, tailX);
		g2.drawString(value, textX, textY);
		textY += lineHeight;
		if (gp.player.currentWeapon != null)
			g2.drawImage(gp.player.currentWeapon.down1, textX, textY - 15, 25, 25, null);
		textY += lineHeight + 3;
		if (gp.player.currentArmor != null)
			g2.drawImage(gp.player.currentArmor.down1, textX, textY - 19, 25, 25, null);
		textY += lineHeight;
		String quest = gp.player.nowQuest;
		textY += 14;
		g2.setColor(new Color(204,198,125));
		if (!gp.player.nowQuest.isBlank()) {
			for (String line : quest.split("\n")) {
				g2.setFont(MisterPixel16.deriveFont(17f));
				g2.drawString(line, frameX + 20, textY);
				textY += 20;
			}
		}

	}

	private void drawPlayerLife() {
		int x = 85;
		int y = gp.screenHeghit-84;
		g2.drawImage(avatarPlayer,0,y-2,85,85, null);

		
		int maxHpBar =200;
		int currentHpBar= (gp.player.currentLife*maxHpBar/gp.player.maxLife);
		g2.setColor(new Color(253,228,174));
		g2.fillRect(x, y-2, maxHpBar+2, 16);
		g2.setColor(Color.red);
		g2.fillRect(85+1,y-1, currentHpBar, 14);
		
		int maxManaBar =140;
		int currentManaBar=0;
		if(gp.player.maxMana!=0)currentManaBar= (int) gp.player.mana*maxManaBar/gp.player.maxMana;
		g2.setColor(new Color(116,207,225));
		g2.fillRect(85, y-2+15, maxManaBar+2, 16);
		g2.setColor(Color.blue);
		g2.fillRect(85+1,y-1+15,currentManaBar, 14);
		
		g2.setColor(Color.white);
		g2.setFont(arial_40.deriveFont(24f));
		g2.drawString("LV "+gp.player.level, 90, y+15+46);

	}

	private void drawTitleScreen() {

		g2.drawImage(backgroundTitleScreen, 0, 0, gp.screenWidth, gp.screenHeghit, null);
		if (titleScreenState == 0) {
			g2.setFont(MisterPixel16);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
			String text = "Young Hero";
			int x = getCenteredTextX(text);
			int y = gp.TitleSize * 3;
			g2.setColor(Color.gray);
			g2.drawString(text, x + 5, y + 5);

			Color c = new Color(216, 254, 236);
			g2.setColor(c);
			g2.drawString(text, x, y);

			x = gp.screenWidth / 2 - gp.TitleSize;
			y += gp.TitleSize;

			// MENU
			g2.setFont(g2.getFont().deriveFont(25f));
			g2.setColor(Color.white);
			
			
			y += gp.TitleSize * 2;
			
			g2.setStroke(new BasicStroke(4));
			int leftX = getCenteredTextX("NEW GAME")+19;
			int r=10;
			Color h = new Color(88,168,85);
			Color f = new Color(200, 240, 200);
			Color w = new Color(247, 238, 75);
			Color textColor = new Color(255,255,255);
			g2.setColor(f);
			g2.fillRoundRect(leftX-48+1, y+20+1, 162,38, r, r);
			g2.setColor(h);
			g2.drawRoundRect(leftX-48, y+20, 165, 40,r,r);
			text = "NEW GAME";
			x = getCenteredTextX(text);
			y += gp.TitleSize;
			g2.setColor(textColor); g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.setColor(w);
				g2.drawRoundRect(leftX-48, y+20-gp.TitleSize, 165, 40,r,r);
			}

			g2.setColor(f);
			g2.fillRoundRect(leftX-48+1, y+20+1, 162,38, r, r);
			g2.setColor(h);
			g2.drawRoundRect(leftX-48, y+20, 165, 40,r,r);
			text = "CONTINUE";
			x = getCenteredTextX(text);
			y += gp.TitleSize;
			g2.setColor(textColor); g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.setColor(w);
				g2.drawRoundRect(leftX-48, y+20-gp.TitleSize, 165, 40,r,r);
			}
			
			g2.setColor(f);
			g2.fillRoundRect(leftX-48+1, y+20+1, 162,38, r, r);
			g2.setColor(h);
			g2.drawRoundRect(leftX-48, y+20, 165, 40,r,r);
			text = "ABOUT";
			x = getCenteredTextX(text);
			y += gp.TitleSize;
			g2.setColor(textColor); g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.setColor(w);
				g2.drawRoundRect(leftX-48, y+20-gp.TitleSize, 165, 40,r,r);
			}
			
			g2.setColor(f);
			g2.fillRoundRect(leftX-48+1, y+20+1, 162,38, r, r);
			g2.setColor(h);
			g2.drawRoundRect(leftX-48, y+20, 165, 40,r,r);
			text = "EXIT";
			x = getCenteredTextX(text);
			y += gp.TitleSize;
			g2.setColor(textColor); g2.drawString(text, x, y);
			if (commandNum == 3) {
				g2.setColor(w);
				g2.drawRoundRect(leftX-48, y+20-gp.TitleSize, 165, 40,r,r);
			}
		}
		else if (titleScreenState == 1) {
			g2.setColor(new Color(0, 0, 0, 60));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeghit);
			g2.setFont(arial_40);
			;
			Color c = new Color(216, 254, 236);
			g2.setColor(c);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
			String text = "";
			int y = 30;
			try {
				String line = "";
				FileReader is = new FileReader("res/about/about.txt");
				BufferedReader map = new BufferedReader(is);
				while ((line = map.readLine()) != null) {
					text = line;
					int x = getCenteredTextX(text);
					y += 28;
					g2.drawString(text, x, y);
				}
				map.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		else if (titleScreenState == 2) {
			g2.setColor(new Color(0, 0, 0, 255));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeghit);
			g2.setFont(arial_40);
			;
			Color c = new Color(216, 254, 236);
			g2.setColor(c);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20f));
			String text = "";
			int y = 30;
			try {
				String line = "";
				FileReader is = new FileReader("res/about/about.txt");
				BufferedReader map = new BufferedReader(is);
				while ((line = map.readLine()) != null) {
					text = line;
					int x = getCenteredTextX(text);
					y += 28;
					g2.drawString(text, x, y);
				}
				map.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
	}

	public void drawDialogScreen() {
		g2.setFont(MaruMonica);
		int x = gp.TitleSize * 2;
		int y = gp.TitleSize / 2;
		int width = gp.screenWidth - gp.TitleSize * 4;
		int height = gp.TitleSize * 4;
		drawSubWindow(x, y, width, height,false);
		g2.setColor(new Color(147,213,22));
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30f));
		x += gp.TitleSize / 2;
		y += gp.TitleSize;
		for (String line : currentDialog.split("\n")) {
			g2.drawString(line, x, y);
			y += 35;
		}
	}

	public void drawSubWindow(int x, int y, int width, int height,boolean NoRound) {
		if(NoRound) {
			Color c = new Color(3,3,3, 190);
			g2.setColor(c);
			g2.fillRoundRect(x, y, width, height, 0, 0);

			c = new Color(4,64,118);
			g2.setColor(c);
			g2.setStroke(new BasicStroke(2));
			g2.drawRoundRect(x, y, width, height, 0, 0);
		}
		else {
			Color c = new Color(3,3,3, 190);
			g2.setColor(c);
			g2.fillRoundRect(x, y, width, height, 35, 35);

			c = new Color(4,64,118);
			g2.setColor(c);
			g2.setStroke(new BasicStroke(2));
			g2.drawRoundRect(x, y, width, height, 35, 35);
		}
	}
	public void drawSubWindow(int x, int y, int width, int height,Color c) {
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 0, 0);

		c = new Color(4,64,118);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(x, y, width, height, 0, 0);
	}

	public int getCenteredTextX(String text) {
		int x;
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = gp.screenWidth / 2 - length / 2;
		return x;
	}

	public int getCenteredTextInFrameX(String text, int xStartFrame, int xEndFrame) {
		int x;
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = (xEndFrame + xStartFrame) / 2 - length / 2;
		return x;
	}

	public int getAlignRightTextX(String text, int tail) {
		int x;
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		x = tail - length;
		return x;
	}

	public void drawSubWindowRound5(int x, int y, int width, int height) {
		Color c = new Color(0, 0, 0, 210);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 5, 5);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(2));
		g2.drawRoundRect(x, y, width, height, 5, 5);
	}
}
