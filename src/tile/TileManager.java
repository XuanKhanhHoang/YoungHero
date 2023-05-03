package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	ArrayList<String> fileName = new ArrayList<>();
	ArrayList<String> collistionStatus = new ArrayList<>();

	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[100];
		mapTileNum = new int[gp.maxMap][gp.maxWorldRow][gp.maxWorldCol];
		FileReader is;
		try {
			is = new FileReader("res/maps/tileData.txt");
			BufferedReader br = new BufferedReader(is);

			String line;
			try {
				while ((line = br.readLine()) != null) {
					fileName.add(line);
					collistionStatus.add(br.readLine());
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		FileReader iss;
		try {
			iss = new FileReader("res/maps/map_01.txt");
			BufferedReader br = new BufferedReader(iss);

			String line;
			try {
				line = br.readLine();
				String[] nums = line.split(" ");
				gp.maxWorldCol = nums.length;
				gp.maxWorldRow = nums.length;
				gp.worldWidth = gp.maxWorldCol * gp.TitleSize;
				gp.worldHeight = gp.maxWorldRow * gp.TitleSize;
				mapTileNum = new int[gp.maxMap][gp.maxWorldRow][gp.maxWorldCol];
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		getTileImage();
		loadMaps("res/maps/map_01.txt", 0);
		loadMaps("res/maps/map_01.txt", 1);
	}

	private void loadMaps(String filePath, int mapNumber) {
		try {
			FileReader is = new FileReader(filePath);
			BufferedReader map = new BufferedReader(is);
			int row = 0, col = 0;

			while (row < gp.maxWorldRow) {

				String line = map.readLine();
				String[] nums = line.split(" ");
				while (col < gp.maxWorldCol) {
					int number = Integer.parseInt(nums[col]);
					mapTileNum[mapNumber][col][row] = number;
					col++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			map.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void getTileImage() {
		for (int i = 0; i < fileName.size(); i++) {
			String fileName1;
			boolean collistion;
			fileName1 = this.fileName.get(i);
			if (collistionStatus.get(i).equals("true")) {
				collistion = true;
			} else {
				collistion = false;
			}
			tile[i] = new Tile();
			try {
				tile[i].image = ImageIO.read(new FileInputStream("res/tiles/" + fileName1));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			tile[i].collision = collistion;
		}
	}

	public void draw(Graphics2D g2) {
		int worldRow = 0, worldCol = 0;
		int tileNum;
		while (worldRow < gp.maxWorldRow && worldCol < gp.maxWorldCol) {
			tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			// CACULATE TILE'S PLACE IN SCREEN
			int worldX = worldCol * gp.TitleSize;
			int worldY = worldRow * gp.TitleSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;

			// STOP MOVING CAMERA
			int rightOffset = gp.screenWidth - gp.player.screenX;
			int bottomOffset = gp.screenHeghit - gp.player.screenY;
			if (rightOffset > gp.worldWidth - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			if (bottomOffset > gp.worldWidth - gp.player.worldY) {
				screenY = gp.screenHeghit - (gp.worldHeight - worldY);
			}
			if (gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}
			if (gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}

			// DRAW IF TILE'S PLACE IN SCREEN
			if (worldX > gp.player.worldX - gp.player.screenX - gp.TitleSize
					&& worldX < gp.player.worldX + gp.player.screenX + gp.TitleSize
					&& worldY > gp.player.worldY - gp.player.screenY - gp.TitleSize
					&& worldY < gp.player.worldY + gp.player.screenY + gp.TitleSize) {
				g2.drawImage(tile[tileNum].image, null, screenX, screenY);
			} else if (gp.player.worldX < gp.player.screenX || gp.player.worldY < gp.player.screenY
					|| rightOffset > gp.worldWidth - gp.player.worldX
					|| bottomOffset > gp.worldHeight - gp.player.worldY) {
				g2.drawImage(tile[tileNum].image, null, screenX, screenY);
			}
			worldCol++;
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
