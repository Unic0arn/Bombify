package entities;

import game.PlayState;
import map.FloorTile;
import map.Square;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class Player implements Renderable {
	int placeableBombs = 1;
	int speed = 3;
	int bombTime = 1; //in seconds
	int bombSize = 1;
	int lives = 1;
	FloorTile tile, goal;
	Vector2f pos, velo = new Vector2f(0, 0),direction = new Vector2f(0, 0);
	boolean hitting = false,
			moving = true;

	Image bomberman;
	int posx,posy,sizex,sizey;
	SpriteSheet ss;
	Animation anime; 

	public Player(FloorTile tile){
		this.tile = tile;
		pos = tile.getMiddle();
		goal = tile;
		
		try {
			ss = new SpriteSheet("/res/b.png", 20, 30);
			anime = new Animation(ss,0,0,0,0,true,300,true);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public boolean update(GameContainer c, int delta, PlayState p) throws SlickException {		
		if(!moving) {
			if(!direction.equals(new Vector2f(0,0))){
				int destinationx = (int) (tile.getGridx() +  direction.x);
				int destinationy = (int) (tile.getGridy() + direction.y); 
				Square s = p.getTiles()[destinationx][destinationy];


				if(isFloor(s)){					
					goal = (FloorTile)s;					
					moving = true;
				}
//				if(hitting){
//					
//					if(isBlock(s)){
//						Block b = (Block)s;	
//						b.destroy(p);
//					}
//					hitting = false;
//				}
			}else if(hitting){
				p.createBomb(this,tile);
				hitting = false;
			}
		}
		else{
			//Right
			if(direction.equals(new Vector2f(1,0))){
				anime = new Animation(ss,9,0,9,0,true, 300,true);
			}
			
			//Left
			else if(direction.equals(new Vector2f(-1,0))){
				anime = new Animation(ss,6,0,8,0,true,300,true);
			}

			//Down
			else if(direction.equals(new Vector2f(0,1))){
				anime = new Animation(ss,0,0,1,0,true,300,true);

			}
			
			//Up
			else if(direction.equals(new Vector2f(0,-1))){
				anime = new Animation(ss,3,0,4,0,true,300,true);

			}
			
			if(goal.getMiddle().copy().sub(pos).length()<0.1){
				moving  = false;
				tile = goal;
			}
			else{
				velo = (goal.getMiddle().copy().sub(pos).scale(0.1f * speed));
				pos.add(velo);
			}
		}
		return true;
	}

	private boolean isFloor(Square s){
		return s instanceof FloorTile; 
	}

//	private boolean isBlock(Square s){
//		return s instanceof Block; 
//	}

	@Override
	public void render(GameContainer c, Graphics g) {
		g.drawAnimation(anime, pos.x, pos.y);
		//g.setColor(Color.green);
		//g.fill(new Circle(pos.x, pos.y, 10));
	}

	public void setDirection(Vector2f a) {
		direction = a;
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

}
