
package powerups;

import entities.Item;
import entities.Player;
import entities.Renderable;
import game.PlayState;
import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

/**
 * Item slow will slow down player
 * @author Victor
 * @version 2012-04-25
 *
 */
public class Slow extends Item{
	
	public Slow(GameContainer gc, Image image, FloorTile ft, int tiles) {
		super(gc, image, ft, tiles);
	}
	
	public FloorTile getTile(){
		return null;
	}
	
	public Player getPlayer(){
		return null;
	}

}
