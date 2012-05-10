
package entities.powerups;

import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Item;
import game.PlayState;

public class Slow implements Item{
	Image slow;
	FloorTile tile;
	int posx,posy,sizex,sizey;
	
	public Slow(GameContainer gc, FloorTile ft, int tiles) {
		
		try {
			slow = new Image("res/items/slow.png");
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sizex = (gc.getWidth()/tiles);
		sizey = (gc.getHeight()/tiles);

		posx = (gc.getWidth()/tiles * ft.getGridx());
		posy = (gc.getHeight()/tiles *ft.getGridy());
	}

	@Override
	public void render(GameContainer c, Graphics g) {
		g.drawImage(slow,
				posx,
				posy,
				posx+sizex,
				posy+sizey,
				0,
				0,
				slow.getWidth(),
				slow.getHeight());		
	}

	@Override
	public void update(GameContainer c, PlayState game, int delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getID() {
		return 3;
	}	
}
