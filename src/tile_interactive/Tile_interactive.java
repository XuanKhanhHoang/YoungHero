package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class Tile_interactive extends Entity {
	public boolean destructible = false;

	public Tile_interactive(GamePanel gp) {
		super(gp);

	}

	public boolean isCorrectItem(Entity item) {
		return item.type == gp.player.type_sword;
	}

	public void update() {
		if (invincible) {
			invincibleCout++;
			if (invincibleCout >= 25) {
				invincibleCout = 0;
				invincible = false;
			}
		}
	}

	public void playSound() {
	}

	public Tile_interactive getDestoyedForm() {
		Tile_interactive tile = null;
		return tile;
	}
}
