package entities;

import game.PlayState;
import map.FloorTile;
import map.Square;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class Player implements Renderable {
	int placeableBombs = 1;
	static int SPEED = 3;
	int bombTime = 3; //in seconds
	int bombSize = 1;
	int animationspeed = 500; 
	int lives = 1;
	FloorTile tile, goal;
	Vector2f pos, velo = new Vector2f(0, 0),direction = new Vector2f(0, 0);
	boolean hitting = false,
			//moving = true,
			moving = false;

	//Image bomberman;
	int posx,posy,sizex,sizey;
	SpriteSheet ss;
	Animation anime; 

	public Player(FloorTile tile){
		this.tile = tile;
		pos = tile.getMiddle();
		goal = tile;
		tile.setPlayer(this);
		try {
			ss = new SpriteSheet("/res/b.png", 20, 30);
			anime = new Animation(ss,0,0,0,0,true,animationspeed,true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public boolean update(GameContainer c, int delta, PlayState p) throws SlickException {		
		sizex = (c.getWidth()/p.getTiles().length);
		sizey = (c.getHeight()/p.getTiles().length);

		if(!moving) {
			if(!direction.equals(new Vector2f(0,0))){
				int destinationx = (int) (tile.getGridx() +  direction.x);
				int destinationy = (int) (tile.getGridy() + direction.y); 
				Square s = p.getTiles()[destinationx][destinationy];
				if(isFloor(s)){
					FloorTile temp = (FloorTile)s;
					if(!temp.hasPlayer()){
						goal = (FloorTile)s;					
						moving = true;
						tile.setPlayer(null);
						goal.setPlayer(this);
					}}
			}else if(hitting){
				p.createBomb(this,tile);
				hitting = false;
			}

		}
		else{
			//Right
			if(direction.equals(new Vector2f(1,0))){
				anime = new Animation(ss,9,0,9,0,true, animationspeed,true);
			}

			//Left
			else if(direction.equals(new Vector2f(-1,0))){
				anime = new Animation(ss,4,0,7,0,true,animationspeed,true);
			}

			//Down
			else if(direction.equals(new Vector2f(0,1))){
				anime = new Animation(ss,0,0,1,0,true,animationspeed,true);
			}
			
			//Up
			else if(direction.equals(new Vector2f(0,-1))){
				anime = new Animation(ss,3,0,4,0,true,animationspeed,true);
			}

			if(goal.getMiddle().copy().sub(pos).length()<0.1){
				moving  = false;
				tile = goal;
			}
			else{
				//velo = (goal.getMiddle().copy().sub(pos).scale(0.4f));
				velo = (goal.getMiddle().copy().sub(pos).scale(0.1f*SPEED));
				pos.add(velo);
			}
		}
		return true;
	}

	private boolean isFloor(Square s){
		return s instanceof FloorTile; 
	}
	@Override
	public void render(GameContainer c, Graphics g) {
		g.drawAnimation(anime, pos.x-(sizex/6f), pos.y-(sizey/6f));
		//g.drawAnimation(anime, pos.x, pos.y);
	}

//	public Vector2f getMiddle(){
//		return new Vector2f(posx+(sizex/6f), posy+(sizey/6f));
//	}

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
		if(!moving)
			hitting=true;
	}
	public int getBombSize(){
		return bombSize;
	}
	public double getBombTime() {

		return bombTime;
	}

	public void hurt(PlayState p) {
		lives =- 1;
		if(lives <= 0){
			System.out.println("I died");
		}
	}
	
	public int decreaseSpeed(int s) {
		return SPEED -= s;
	}
	
	public int increaseSpeed(int s) {
		return SPEED -= s;
	}

}
