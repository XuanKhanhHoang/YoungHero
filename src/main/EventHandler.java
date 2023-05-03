package main;

import java.awt.Rectangle;

public class EventHandler {
	class EventRectangle extends Rectangle {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int EventRectangleDefaultX, EventRectangleDefaultY;
		boolean eventDone = false;
	}

	GamePanel gp;
	public EventRectangle eventRectangle[][][];
	public int previousEventX, previousEventY;
	private boolean canHandle = true;

	public EventHandler(GamePanel gp) {
		this.gp = gp;
		eventRectangle = new EventRectangle[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		previousEventX = -48;
		previousEventY = -48;
		// SET EVENT'S RECTANGLE
		int col = 0, row = 0, map = 0;
		while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRectangle[map][col][row] = new EventRectangle();
			eventRectangle[map][col][row].x = 20;
			eventRectangle[map][col][row].y = 20;
			eventRectangle[map][col][row].width = 15;
			eventRectangle[map][col][row].height = 15;
			eventRectangle[map][col][row].EventRectangleDefaultX = eventRectangle[map][col][row].x;
			eventRectangle[map][col][row].EventRectangleDefaultY = eventRectangle[map][col][row].y;

			col++;
			if (col == gp.maxWorldCol) {
				col = 0;
				row++;
				if (row == gp.maxWorldRow) {
					map++;
					row = 0;
				}
			}
		}
		eventRectangle[0][21][5].x = 0;
		eventRectangle[0][21][5].y = 0;
		eventRectangle[0][21][5].height = 47;
		eventRectangle[0][21][5].width = 47;
		eventRectangle[0][21][4].x = 0;
		eventRectangle[0][21][4].y = 0;
		eventRectangle[0][21][4].height = 47;
		eventRectangle[0][21][4].width = 47;
		eventRectangle[0][22][7].x = 0;
		eventRectangle[0][22][7].y = 0;
		eventRectangle[0][22][7].height = 47;
		eventRectangle[0][22][7].width = 47;
	}

	public void checkEvent() {
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if (distance > gp.TitleSize) {
			canHandle = true;
		}
		if (canHandle) {
			if (hit(0, 21, 5, "any") && (gp.handleStory.turn == 7 && gp.handleStory.numDialog > 0)) {
				dialogHandle("I'll kill The Fire Devel General !", 4);
			} else if (hit(0, 22, 7, "any")) {
				dialogHandle("Me: I saw a iron amor \nI'll get it !", 0);
				eventRectangle[0][22][7].eventDone = true;
			} else if (hit(0, 21, 4, "any") && (gp.handleStory.turn == 7 && gp.handleStory.numDialog > 0)) {
				dialogHandle("I'll kill The Fire Devel General !", 4);
			} else if (hit(0, 22, 22, "down")) {
				dialogHandle("Attack tree to cut it !\n(Press enter)", 0);
				eventRectangle[0][22][22].eventDone = true;
			} else if (hit(0, 22, 36, "down")) {
				dialogHandle("Me:That's a long time ...\n     I can saw any people ,\n      I think I'll talk to them",
						0);
				eventRectangle[0][22][36].eventDone = true;
			} else if (hit(0, 22, 35, "up")) {
				dialogHandle("Me: Maybe I should do some work!", 1);
			} else if (hit(0, 39, 44, "any")) {
				eventRectangle[0][22][35].eventDone = true;
				eventRectangle[0][39][44].eventDone = true;
				gp.gameState = gp.storyState;
			} else if (hit(0, 49, 13, "any")) {
				dialogHandle("Me: Maybe I this is wrong way!", 2);
			} else if (hit(0, 0, 10, "any")) {
				dialogHandle("Me: Maybe I this is wrong way!", 3);
			} else if (hit(0, 49, 35, "any")) {
				dialogHandle("Me: Maybe I this is wrong way!", 2);
			} else if ((hit(0, 23, 12, "any")) && (gp.handleStory.turn == 7)) {
				dialogHandle("Me: Maybe I need to cut this trees!", 0);
				eventRectangle[0][23][12].eventDone = true;
			}
		}
	}

	private void dialogHandle(String string, int exeption) {
		gp.gameState = gp.dialogState;
		gp.ui.currentDialog = string;
		if (exeption == 0) {
		} else if (exeption == 1) {
			gp.player.direction = "down";
			gp.player.worldY -= 10;
		} else if (exeption == 2) {
			gp.player.direction = "left";
			gp.player.worldX -= 10;
		} else if (exeption == 3) {
			gp.player.direction = "right";
			gp.player.worldX += 10;
		} else if (exeption == 4) {
			gp.player.direction = "left";
			gp.player.worldX -= 48;
			int i=0;
			while (gp.monster[0][i].name!="FireDevil General" && i<15) {
				i++;
			}
			if(i!=15) {
				gp.monster[0][i].worldX-=48;
			}
		}
	}

	public boolean hit(int map, int col, int row, String requireDirection) {
		boolean hit = false;
		if (map == gp.currentMap) {
			gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			eventRectangle[map][col][row].x = col * gp.TitleSize + eventRectangle[map][col][row].x;
			eventRectangle[map][col][row].y = row * gp.TitleSize + eventRectangle[map][col][row].y;

			if (gp.player.solidArea.intersects(eventRectangle[map][col][row])
					&& !eventRectangle[map][col][row].eventDone) {
				if (gp.player.direction.contentEquals(requireDirection) || requireDirection.contentEquals("any")) {
					hit = true;
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}

			gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			eventRectangle[map][col][row].x = eventRectangle[map][col][row].EventRectangleDefaultX;
			eventRectangle[map][col][row].y = eventRectangle[map][col][row].EventRectangleDefaultY;
		}

		return hit;
	}

}
