package map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import entities.Player;
/**
 * A class that contains information about a tile on the floor.
 * @author Fredrik
 *
 */
public class FloorTile implements Square {
	private Image img;
	int posx,posy,sizex,sizey;
	int gridx,gridy;
	public int getGridx() {
		return gridx;
	}
	public int getGridy() {
		return gridy;
	}
	public FloorTile(int x, int y, GameContainer container, int tiles){
		gridx = x;
		gridy = y;
		sizex = (container.getWidth()/tiles);
		sizey = (container.getHeight()/tiles);
		posx = (container.getWidth()/tiles * x);
		posy = (container.getHeight()/tiles * y);
	}
	@Override
	public void render(GameContainer container, Graphics g, int x, int y,
			int tiles) throws SlickException {
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
			g.setColor(Color.black);
			g.fill(new Rectangle(posx,posy,sizex,sizey));
		}
	}
	@Override
	public void setImg(Image i){
		img = i;
	}
	public Vector2f getMiddle(){
		return new Vector2f(posx+(sizex/2f), posy+(sizey/2f));
	}
	public String toString(){
		return "[" + gridx + "," + gridy + "]";
	}
}

