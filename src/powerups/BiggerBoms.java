package powerups;

import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Item;

public class BiggerBoms extends Item {
	Image biggerBomb;

	public BiggerBoms(GameContainer gc, FloorTile ft, int tiles) {
		super(gc, ft, tiles);
		try {
			biggerBomb = new Image("res/dynamite.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}

}
