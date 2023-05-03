package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;

public class StoryCharacter_GrandFatherPlayer extends Entity {
	BufferedImage tempStay, die;
	ArrayList<String> pathMove = new ArrayList<>();
	int preCol, preRow;

	public StoryCharacter_GrandFatherPlayer(GamePanel gp, int col, int row) {
		super(gp);
		type = 1;
		direction = "right";
		canMove = true;
		speed = 2;
		onPath = true;
		getNPCImage();
		setDialog();
		solidArea = new Rectangle(0, 16, 38, 30);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		preCol = col;
		preRow = row;
		pathMove.add("up");
		pathMove.add("up");
		pathMove.add("right");
		pathMove.add("right");
		pathMove.add("right");
		pathMove.add("right");
		name = "GrandFather Player";
	}

	private void getNPCImage() {
		try {
			tempStay = ImageIO.read(new FileInputStream("res/npc/gfPlayer_stay.png"));
			die = ImageIO.read(new FileInputStream("res/npc/gfPlayer_die.png"));
			up1 = ImageIO.read(new FileInputStream("res/npc/StoryCharacter_GrandFatherPlayer_behind_rf.png"));
			up3 = ImageIO.read(new FileInputStream("res/npc/StoryCharacter_GrandFatherPlayer_behind_lf.png"));
			right1 = ImageIO.read(new FileInputStream("res/npc/StoryCharacter_GrandFatherPlayer_right_rf.png"));
			right3 = ImageIO.read(new FileInputStream("res/npc/StoryCharacter_GrandFatherPlayer_right_lf.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setAction() {
		if (onPath) {

			int goalCol = 39, goalRow = 43;
			if ((worldX + solidArea.x) == 39 * gp.TitleSize && (worldY + solidArea.y)== 44*gp.TitleSize) {
				gp.handleStory.npcUpdate = false;
				gp.gameState = gp.storyState;
				speed = 4;
				return;
			}
			if (preCol != (worldX + solidArea.x) / gp.TitleSize || preRow != (worldY + solidArea.y) / gp.TitleSize) {
				preCol = (worldX + solidArea.x) / gp.TitleSize;
				preRow = (worldY + solidArea.y) / gp.TitleSize;
				stotyMove();
			}
			if ((worldX + solidArea.x) == goalCol * gp.TitleSize && (worldY + solidArea.y) <= goalRow*gp.TitleSize)  {
				onPath = false;
				canMove = false;
				direction = "right";
				down1 = tempStay;
				down3 = tempStay;
				gp.handleStory.npcUpdate = false;
				gp.gameState=gp.storyState;
				gp.appearTransSub=false;
			}
		}
	}
	private void stotyMove() {
		int i = pathMove.size();
		if (i > 0) {
			direction = pathMove.get(i - 1);
			pathMove.remove(i - 1);
		}
	}

	public void setDialog() {
		dialoges = new String[1];
		dialoges[0] = "Go to the North and Finding a Special Grass ";
	}

	public void changeDieImage() {
		down1 = die;
	}
}
