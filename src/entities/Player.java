package entities;

import game.PlayState;
import map.FloorTile;
import map.Square;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Player implements Renderable {
	int placeableBombs = 1;
	int speed = 3;
	int bombTime = 2; //in seconds
	int bombSize = 1;
	int lives = 1;
	int playerNumber = 1;
	FloorTile tile, goal;
	Vector2f pos, velo = new Vector2f(0, 0),direction = new Vector2f(0, 0);
	boolean hitting = false,
			moving = true;

	Image bomberman;
	int posx,posy,sizex,sizey;
	ResourceManager test;
	SpriteSheet ss;
	Animation anime; 

	public Player(FloorTile tile) {//throws SlickException {		
		this.tile = tile;
		pos = tile.getMiddle();
		goal = tile;
		try {
			ss = new SpriteSheet("/res/lol.png",32,32);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		anime = new Animation(ss,0,0,0,0,true,200,true);
	}

	public boolean update(GameContainer c, int delta, PlayState p) throws SlickException {
//		sizex = (c.getWidth()/tiles);
//		sizey = (c.getHeight()/tiles);
//		posx = (c.getWidth()/tiles * tile.getGridx());
//		posy = (c.getHeight()/tiles *tile.getGridy());

		//tileSheet = new SpriteSheet("res/Bomberman.png", tile.getGridx(), tile.getGridy(), 10);
		//anime = new Animation(tileSheet, 2, 2, 5, 4, false, 10, false);
		
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
			anime = new Animation(ss,0,0,0,0,true,120,true);
		}else{
			if(direction.equals(new Vector2f(1,0))){
				anime = new Animation(ss,
						0+(playerNumber -1)*3,
						2+(playerNumber -1)*4 ,
						0+2+(playerNumber -1)*3,
						2+4+(playerNumber -1)*4 ,
						true,120,true);
			}
			else if(direction.equals(new Vector2f(-1,0))){
				anime = new Animation(ss,
						0+(playerNumber -1)*3,
						1+(playerNumber -1)*4 ,
						0+2+(playerNumber -1)*3,
						1+4+(playerNumber -1)*4 ,
						true,120,true);
			}
			else if(direction.equals(new Vector2f(0,1))){
				anime = new Animation(ss,
						0+(playerNumber -1)*3,
						0+(playerNumber -1)*4 ,
						0+2+(playerNumber -1)*3,
						0+4+(playerNumber -1)*4 ,
						true,120,true);
			}
			else if(direction.equals(new Vector2f(0,-1))){
				anime = new Animation(ss,
						0+(playerNumber -1)*3,
						3+(playerNumber -1)*4 ,
						0+2+(playerNumber -1)*3,
						3+4+(playerNumber -1)*4 ,
						true,120,true);
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
//		g.drawImage(img,
//				posx,
//				posy,
//				posx+sizex,
//				posy+sizey,
//				0,
//				0,
//				img.getWidth(),
//				img.getHeight());
			
		//anime = test.getAnimation("1");
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
