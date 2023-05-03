package tile_interactive;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class InterractiveTilr_Tee_01_cutted extends Tile_interactive {

	public InterractiveTilr_Tee_01_cutted(GamePanel gp, int worldX, int worldY) {
		super(gp);
		destructible = true;
		try {
			down1 = ImageIO.read(new FileInputStream("res/tile_interactive/tree_02_cutted.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.worldX = worldX;
		this.worldY = worldY;
		solidArea = new Rectangle(0, 0, 0, 0);
	}

}
