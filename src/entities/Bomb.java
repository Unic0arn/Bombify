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
	FloorTile tile;
	Image img;
	double timeTil;
	int bombSize;
	int bombTime;
	int posx,posy,sizex,sizey;
	Animation animeBomb;
	Player player;
	float animationScale;

	public Bomb(GameContainer gc, int bombSize, int bombTime, Animation b,FloorTile ft,int tiles, Player p){
		animationScale = (float) (1.0/(tiles/15.0));
		
		try {
			animeBomb = new Animation(new SpriteSheet(new Image("res/items/bomb.png").getScaledCopy(animationScale),
					(int)(50.0*animationScale), (int)(50.0*animationScale)),
					0,0,1,0,true,500,true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tile = ft;
		timeTil = bombTime * 1000; // bombTime in milliseconds
		sizex = (gc.getWidth()/tiles);
		sizey = (gc.getHeight()/tiles);
		player = p;
		posx = (gc.getWidth()/tiles * ft.getGridx());
		posy = (gc.getHeight()/tiles *ft.getGridy());
		this.bombSize = bombSize;
		this.bombTime = bombTime;

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
		player.decreaseBombs();
	}
	public FloorTile getTile(){
		return tile;
	}

	public int getBombSize() {
		return bombSize;
	}
}
