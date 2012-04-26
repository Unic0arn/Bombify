package entities;

import game.PlayState;
import map.FloorTile;
import map.Square;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.Sound;
import org.newdawn.slick.Image;

public class Player implements Renderable {

	int placeableBombs = 5, speed = 3, bombTime = 1, bombs = 0, bombDelay = 1000, sinceLastBomb = 1000,
			hitDelay = 1000, sinceLastHit = 1000,bombSize = 1, animationspeed = 500, lives = 3;

	FloorTile currentTile, goalTile;
	Vector2f pos, velo = new Vector2f(0, 0),direction = new Vector2f(0, 0);
	boolean hitting = false,moving = false;
	Color playerColor;
	Image hearts;
	int posx,posy,sizex,sizey;
	SpriteSheet ss;
	Animation players; 
	Sound life; 

	public Player(FloorTile tile,Color c){
		this.currentTile = tile;
		pos = tile.getMiddle();
		goalTile = tile;
		tile.setPlayer(this);
		playerColor = c;
		try {
			ss = new SpriteSheet("/res/players.png", 20, 30);
			players = new Animation(ss,0,0,0,0,true,animationspeed,true);
			life = new Sound("res/life.wav");
			hearts = new Image("res/heartSmall.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public boolean update(GameContainer c, int delta, PlayState p) throws SlickException {		
		sizex = (c.getWidth()/p.getTiles().length);
		sizey = (c.getHeight()/p.getTiles().length);

		if(sinceLastBomb<=bombDelay){
			sinceLastBomb = sinceLastBomb + delta;
		}
		if(sinceLastHit<=hitDelay){
			sinceLastHit = sinceLastHit+ delta;
		}

		if(!moving) {
			if(!(direction.x == 0 && direction.y == 0)){
				int destinationx = (int) (currentTile.getGridx() +  direction.x);
				int destinationy = (int) (currentTile.getGridy() + direction.y); 
				Square[][] tileGrid = p.getTiles();
				Square s = tileGrid[destinationx][destinationy];
				
				if(isFloor(s)){
					FloorTile tempTile = (FloorTile)s;
					
					if(tempTile.hasPlayer() && hitting && isHitAllowed() ){
						tempTile.getPlayer().hurt();
						sinceLastHit = 0;
					
					}else if(!tempTile.hasPlayer() && !tempTile.hasBomb()){
						goalTile = tempTile;				
						moving = true;
						currentTile.setPlayer(null);
						goalTile.setPlayer(this);
						if(goalTile.isBurning()){
							hurt();
						}
					}
					if(tempTile.hasItem()){
						System.out.println("LOL");
						getItem(p,tempTile.getItem());
					}
				}
			}else if(hitting){
				if(!currentTile.hasBomb()){
					p.createBomb(this,currentTile);
					bombs++;
					sinceLastBomb = 0;
					System.out.println("Bomb in 'player' created.");
					System.out.println("Antal bomber: "+bombs);
				}
				hitting = false;
			}
		}else{

			//Right
			if(direction.equals(new Vector2f(1,0))){
				players = new Animation(ss,9,0,9,0,true, animationspeed,true);
			}

			//Left
			else if(direction.equals(new Vector2f(-1,0))){
				players = new Animation(ss,4,0,7,0,true,animationspeed,true);
			}

			//Down
			else if(direction.equals(new Vector2f(0,1))){
				players = new Animation(ss,0,0,1,0,true,animationspeed,true);
			}

			//Up
			else if(direction.equals(new Vector2f(0,-1))){
				players = new Animation(ss,3,0,4,0,true,animationspeed,true);
			}

			if(goalTile.getMiddle().copy().sub(pos).length()<0.1){
				moving  = false;
				currentTile = goalTile;
			}
			else{
				velo = (goalTile.getMiddle().copy().sub(pos).scale(0.1f*speed));
				pos.add(velo);
			}
		}
		return true;
	}

	private void getItem(PlayState p, Item item) {
		switch (item.getID()){
		case 1: 
			bombSize++;
			System.out.println("Bombsize is now " + bombSize);
			break;
		case 2: 
			life.play();
			lives++;
			System.out.println("Lives: " + lives);
			break;
		case 3: 
			speed--;
			System.out.println("Speed is now " + speed);
			break;
		case 4: 
			speed++;
			System.out.println("Speed is now " + speed);
			break;
		}
		p.removeItem(item);
		goalTile.setItem(null);
	}

	private boolean isFloor(Square s){
		return s instanceof FloorTile; 
	}
	@Override
	public void render(GameContainer c, Graphics g) {
		g.drawAnimation(players, pos.x-(sizex/6f), pos.y-(sizey/6f),playerColor);
	}

	public void setDirection(Vector2f a) {
		direction = a;
	}
	public boolean isAlive(){
		return lives > 0;
	}
	public Vector2f getPos() {
		return pos;
	}

	public void hit() {
		if(!moving) {
			if(isBombAllowed()) {
				hitting=true;
			}			
		}
	}
	
	public int getLifes(){
		return lives;
	}

	public int getBombSize(){
		return bombSize;
	}

	public double getBombTime() {
		return bombTime;
	}
	public void decreaseBombCount(){
		bombs--;
	}
	public boolean isBombAllowed(){
		if (bombs<placeableBombs && sinceLastBomb>=bombDelay){
			return true;
		}
		return false;
	}
	public boolean isHitAllowed(){
		if (sinceLastHit>=hitDelay){
			return true;
		}
		return false;
	}

	public void hurt() {
		lives--;
		System.out.println("Lives: " + lives);
		if(lives <= 0){
			System.out.println("I died");
		}
	}
}
