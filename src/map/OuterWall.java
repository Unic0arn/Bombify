package map;


import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * A class that contains information about an outer wall.
 * @author Fredrik & Victor
 *
 */
public class OuterWall implements Square {
	private Image imgOuterWall;
	int gridx,gridy;


	public OuterWall(int x, int y, GameContainer container, int tiles){
		try {
			imgOuterWall = new Image("res/outerWall.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Override
	public void render(GameContainer container, Graphics g, int x, int y,
			int tiles) throws SlickException {
		int sizex = (container.getWidth()/tiles);
		int sizey = (container.getHeight()/tiles);
		int posx = (container.getWidth()/tiles * x);
		int posy = (container.getHeight()/tiles * y);

		g.drawImage(imgOuterWall ,
				posx,
				posy,
				posx+sizex,
				posy+sizey,
				0,
				0,
				imgOuterWall .getWidth(),
				imgOuterWall .getHeight());
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
