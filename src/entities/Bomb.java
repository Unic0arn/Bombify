package entities;

/**
 * Bomb counts down and by zero it has disappeared. 
 * 
 * @author Fredrik
 * @version 2012-04-23
 */
import game.PlayState;
import map.FloorTile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class Bomb implements Renderable {
	Player p;
	FloorTile tile;
	Image img;
	double timeTil;
	int posx,posy,sizex,sizey;
	Animation animeBomb;

	public Bomb(GameContainer gc, Player player, Animation b,FloorTile ft,int tiles){
		try {
			animeBomb = new Animation(new SpriteSheet("res/bomb.png",50 , 50),0,0,1,0,true,500,true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		g.drawAnimation(animeBomb, posx-(sizex/10), posy-(sizey/10f));
	}

	public void update(GameContainer c, PlayState game, int delta){
		timeTil = timeTil - delta;
		if(timeTil <= 0){
			explodeBomb(game);
		}
	}
	public void explodeBomb(PlayState game){
		game.removeBomb(this);
	}
	public FloorTile getTile(){
		return tile;
	}

	public Player getPlayer(){
		return p;
	}
}
