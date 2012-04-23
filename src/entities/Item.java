package entities;
import game.PlayState2;
import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
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
	
	//Temporary until we figure out Animations. 
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
	public void update(GameContainer c, PlayState2 game, int delta){}


	public FloorTile getTile(){
		return tile;
	}
	
	public Player getPlayer(){
		return p;
	}
}

