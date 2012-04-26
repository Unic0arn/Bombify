package entities;
import game.PlayState;
import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
//import org.newdawn.slick.state.StateBasedGame;

/**
 * Different Items changes state of players. 
 * @author Fredrik & Victor
 * @version 2012-04-25
 *
 */
public class Item implements Renderable {
	Player p;
	FloorTile tile;
	Image img;
	double timeTil;
	int posx,posy,sizex,sizey;

	
	public Item(GameContainer gc, Image image,FloorTile ft,int tiles){		
		img = image;
		tile = ft;
		sizex = (gc.getWidth()/tiles);
		sizey = (gc.getHeight()/tiles);

		posx = (gc.getWidth()/tiles * ft.getGridx());
		posy = (gc.getHeight()/tiles *ft.getGridy());
	}
	
	@Override
	public void render(GameContainer c, Graphics g) {
			
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
	public void update(GameContainer c, PlayState game, int delta){
		//TODO
	}
	
	public void removeItem(PlayState p){
		//TODO
	}

	public FloorTile getTile(){
		return tile;
	}
}

