package tile_interactive;

import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile_Tree01 extends Tile_interactive {

	public InteractiveTile_Tree01(GamePanel gp) {
		super(gp);
		currentLife = 2;
		destructible = true;
		try {
			down1 = ImageIO.read(new FileInputStream("res/tile_interactive/tree_02.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean isCorrectItem(Entity item) {
		return item.type == gp.player.type_sword;
	}

	public void playSound() {
	}

	public Tile_interactive getDestoyedForm() {
		Tile_interactive tile = new InterractiveTilr_Tee_01_cutted(gp, worldX, worldY);
		return tile;
	}
}
