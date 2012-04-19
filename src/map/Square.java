package map;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public interface Square {
	public void render(GameContainer container, Graphics g,
			int x, int y, int tiles) throws SlickException ;
	public void setImg(Image i);
}