package objects;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class Obj_Coin extends Entity {

	public Obj_Coin(GamePanel gp) {
		super(gp);
		name = "Coin";
		type = type_pickUpOnly;
		value = 50;
		try {
			down1 = ImageIO.read(new FileInputStream("res/objects/coin_bronze.png"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void get(Entity entity) {
		gp.ui.addMessage("Coin+ " + value);
		gp.player.coin += value;
	}

}
