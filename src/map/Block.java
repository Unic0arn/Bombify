package map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import entities.Player;



/**
 * This class will create x and y coordinates for
 * one wall with frame 800 x 600 in future: 
 * - n x m if user choose to resize window. 
 * 
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-16
 */
public class Block implements Square{
	boolean immovable = false;
	private Image img;
	
	public Block() {
		
	
	
	}
	public void render(GameContainer container, Graphics g,
			int x, int y, int tiles)
			throws SlickException {
			int sizex=container.getWidth()/tiles;
			int sizey=container.getHeight()/tiles;
			int posx = container.getWidth()/tiles * x;
			int posy = container.getHeight()/tiles * y;
			
			
			if(img!=null){
				g.drawImage(img,
	                      posx,
	                      posy,
	                      posx+sizex,
	                      posy+sizey,
	                      0,
	                      0,
	                      img.getWidth(),
	                      img.getHeight());
			}
			else{
				g.setColor(Color.darkGray);
				g.fill(new Rectangle(posx,posy,sizex,sizey));
			}
	}
	public boolean collides(Player p) {
		return false;
	}

}
