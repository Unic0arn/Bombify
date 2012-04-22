package entities;

import game.PlayState;
import map.FloorTile;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.state.StateBasedGame;
public class Bomb implements Renderable {
	Player p;
	FloorTile tile;
	Image img;
	double timeTil;
	int posx,posy,sizex,sizey;
	
	public Bomb(GameContainer gc, Player player, Image image,FloorTile ft,int tiles){
		img = image;
		p = player;
		tile = ft;
		timeTil = player.getBombTime() * 1000;
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
		timeTil = timeTil - delta;
		//System.out.println(timeTil);
		if(timeTil <= 0){
			game.removeBomb(this);
			//explode();
		}
	}
	
	public FloorTile getTile(){
		return tile;
	}
	
	public Player getPlayer(){
		return p;
	}
//	private void explode() {
//		System.out.println("BOOOM");
//		
//	}
}
