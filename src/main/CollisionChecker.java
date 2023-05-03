package main;

import entity.Entity;

public class CollisionChecker {
	GamePanel gp;

	CollisionChecker(GamePanel gp) {
		this.gp = gp;
	}

	public void checkTile(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

		int entityLeftCol = entityLeftWorldX / gp.TitleSize;
		int entityRightCol = entityRightWorldX / gp.TitleSize;
		int entityTopRow = entityTopWorldY / gp.TitleSize;
		int entityBottomRow = entityBottomWorldY / gp.TitleSize;

		int tileNum1 = -1, tileNum2 = -1;

		switch (entity.direction) {

		case "up":
			entityTopRow = (entityTopWorldY - entity.speed) / gp.TitleSize;
			if (entityTopRow < 0) {
				entity.collisionOn = true;
				return;
			}
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed) / gp.TitleSize;
			if (entityBottomRow >= gp.maxWorldRow) {
				entity.collisionOn = true;
				return;
			}
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];

			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed) / gp.TitleSize;
			if (entityLeftCol < 0) {
				entity.collisionOn = true;
				return;
			}
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];

			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed) / gp.TitleSize;
			if (entityRightCol >= gp.maxWorldCol) {
				entity.collisionOn = true;
				return;
			}
			tileNum1 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileManager.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			break;

		}
		if (gp.tileManager.tile[tileNum1].collision || gp.tileManager.tile[tileNum2].collision) {
			entity.collisionOn = true;
		}
	}

	public int checkObject(Entity entity, boolean isPlayer) {
		int defaultInt = 999;
		for (int i = 0; i < gp.object[1].length; i++) {
			if (gp.object[gp.currentMap][i] != null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				gp.object[gp.currentMap][i].solidArea.x = gp.object[gp.currentMap][i].worldX
						+ gp.object[gp.currentMap][i].solidArea.x;
				gp.object[gp.currentMap][i].solidArea.y = gp.object[gp.currentMap][i].worldY
						+ gp.object[gp.currentMap][i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}

				if (entity.solidArea.intersects(gp.object[gp.currentMap][i].solidArea)) {
					if (gp.object[gp.currentMap][i].collision) {
						entity.collisionOn = true;
					}
					if (isPlayer) {
						defaultInt = i;
					}
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.object[gp.currentMap][i].solidArea.x = gp.object[gp.currentMap][i].solidAreaDefaultX;
				gp.object[gp.currentMap][i].solidArea.y = gp.object[gp.currentMap][i].solidAreaDefaultY;
			}
		}
		return defaultInt;
	}

	public int checkEntity(Entity entity, Entity target[][]) {
		int defaultInt = 999;
		for (int i = 0; i < target[1].length; i++) {
			if (target[gp.currentMap][i] != null) {
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].worldX
						+ target[gp.currentMap][i].solidArea.x;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].worldY
						+ target[gp.currentMap][i].solidArea.y;

				switch (entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					break;
				case "left":
					entity.solidArea.x -= entity.speed;
					break;
				case "right":
					entity.solidArea.x += entity.speed;
					break;
				}

				if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
					if (target[gp.currentMap][i] != entity) {
						entity.collisionOn = true;
						defaultInt = i;
					}
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				if (entity.solidArea.intersects(target[gp.currentMap][i].solidArea)) {
					if (target[gp.currentMap][i] != entity) {
						entity.collisionOn = true;
						defaultInt = i;
					}
				}
				target[gp.currentMap][i].solidArea.x = target[gp.currentMap][i].solidAreaDefaultX;
				target[gp.currentMap][i].solidArea.y = target[gp.currentMap][i].solidAreaDefaultY;
			}
		}
		return defaultInt;
	}

	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

		switch (entity.direction) {
		case "up":
			entity.solidArea.y -= entity.speed;
			break;
		case "down":
			entity.solidArea.y += entity.speed;
			break;
		case "left":
			entity.solidArea.x -= entity.speed;
			break;
		case "right":
			entity.solidArea.x += entity.speed;
			break;
		}

		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}

		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		if (entity.solidArea.intersects(gp.player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;

		return contactPlayer;
	}

}
