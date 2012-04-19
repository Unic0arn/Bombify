package map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class OuterWall implements Square {
	private Image img;
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
	public void setImg(Image i) {
		img = i;
	}

}