package entities;

import map.FloorTile;
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
 * @version 2012-04-24
 *
 */
public class Slow extends Item implements Renderable {
	Player p;
	FloorTile tile;
	Image img;
	double timeTil;
	int posx,posy,sizex,sizey;

	public Slow(GameContainer gc, Image image, FloorTile ft, int tiles) {
		super(gc, image, ft, tiles);
	}
	
	public FloorTile getTile(){
		return tile;
	}
	
	public Player getPlayer(){
		return p;
	}

}
