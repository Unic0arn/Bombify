package map;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import entities.Player;
/**
 * A class that contains information about an outer wall.
 * @author Fredrik & Victor
 *
 */
public class OuterWall implements Square {
	private Image img;
	int gridx,gridy;
	public OuterWall(int x, int y, GameContainer container, int tiles){
		
	}
	@Override
	public void render(GameContainer container, Graphics g, int x, int y,
			int tiles) throws SlickException {
		int sizex = (container.getWidth()/tiles);
		int sizey = (container.getHeight()/tiles);
		int posx = (container.getWidth()/tiles * x);
		int posy = (container.getHeight()/tiles * y);

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
	public OuterWall setImg(Image i) {
		img = i;
		return this;
	}

	@Override
	public int getGridx() {
		return gridx;
	}

	@Override
	public int getGridy() {
		return gridy;
	}
}
