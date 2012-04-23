package map;

import org.newdawn.slick.Animation;
//import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
//import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
//import org.newdawn.slick.geom.Rectangle;

import game.PlayState;
import entities.Player;

/**
 * A class that contains information about a block in the map.
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-22
 */
public class Block implements Square{
	boolean immovable =true;
	//private Image img;
	int gridx,gridy,posx,posy,sizex,sizey;
	private Animation anime;
	
	
	public int getGridx() {
		return gridx;
	}
	public int getGridy() {
		return gridy;
	}
	public Block(int x, int y, GameContainer container, int tiles) {

		gridx = x;
		gridy = y;
		sizex=container.getWidth()/tiles;
		sizey=container.getHeight()/tiles;
		posx = container.getWidth()/tiles * x;
		posy = container.getHeight()/tiles * y;
	}
	
	/**
	 * Checks for immovable walls or noth. 
	 * @return true/false. 
	 */
	public boolean isImmovable() {
		return immovable;
	}
	
	//Block that can't move. 
	public Block setImmovable(boolean b){
		immovable = b;
		return this;
	}
	public boolean destroy(PlayState gs){
		if(immovable){
			return false;
		}
		else {
			gs.removeWall(this);
			return true;
		}
	}
	public void render(GameContainer container, Graphics g,
			int x, int y, int tiles)
					throws SlickException {
		
		if(anime!=null){
			g.drawAnimation(anime, posx-(sizex/6f), posy-(sizey/6f));
//			g.drawImage(img,
//					posx,
//					posy,
//					posx+sizex,
//					posy+sizey,
//					0,
//					0,
//					img.getWidth(),
//					img.getHeight());
		}
//		else{
//			g.setColor(Color.darkGray);
//			g.fill(new Rectangle(posx,posy,sizex,sizey));
//		}
	}
//	@Override
//	public Block setImg(Image i) {
//		img = i;
//		return this;
//	}
	@Override
	public Square setAnimation(Animation a) {
		anime = a;
		return this;
	}

}
