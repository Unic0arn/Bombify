package powerups;

import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Item;

public class Life extends Item {
	Image life;

	public Life(GameContainer gc,FloorTile ft, int tiles) {
		super(gc, ft, tiles);
		try {
			life = new Image("res/life.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
