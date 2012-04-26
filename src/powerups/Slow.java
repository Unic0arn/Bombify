
package powerups;

import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Item;

public class Slow extends Item{
	Image slow;
	
	public Slow(GameContainer gc, FloorTile ft, int tiles) {
		super(gc,ft, tiles);
		try {
			slow = new Image("res/slow.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
