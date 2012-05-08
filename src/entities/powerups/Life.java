package entities.powerups;

import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import entities.Item;
import game.PlayState;

public class Life implements Item {
	Image life;
	FloorTile tile;
	int posx,posy,sizex,sizey;

	public Life(GameContainer gc,FloorTile ft, int tiles){
	
		try {
			life = new Image("res/life.png");
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
	public void render(GameContainer c, Graphics g){
		g.drawImage(life,
				posx,
				posy,
				posx+sizex,
				posy+sizey,
				0,
				0,
				life.getWidth(),
				life.getHeight());	
	}

	@Override
	public void update(GameContainer c, PlayState game, int delta) {
		// TODO Auto-generated method stub
	}

	@Override
	public int getID() {		
		return 2;
	}
}
