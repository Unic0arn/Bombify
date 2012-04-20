package entities;

import game.PlayState;
import map.FloorTile;
import map.Block;
import map.Square;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class Player implements Renderable {
	Ellipse player;
	FloorTile tile;
	FloorTile goal;
	Vector2f pos;
	Vector2f velo = new Vector2f(0, 0);
	Vector2f direction = new Vector2f(0, 0);
	boolean hitting = false;
	boolean moving = true;
	

	public Player(FloorTile tile) {
		this.tile = tile;
		pos = tile.getMiddle();
		goal = tile;
	}

	public boolean update(GameContainer c, int delta, PlayState p) {
		if(!moving) {
			if(!direction.equals(new Vector2f(0,0))){
				int destinationx = (int) (tile.getGridx() +  direction.x);
				int destinationy = (int) (tile.getGridy() + direction.y); 
				Square s = p.getTiles()[destinationx][destinationy];
				
				
				if(isFloor(s)){
					goal = (FloorTile)s;
					moving = true;
				}
				if(hitting){
					if(isBlock(s)){
						Block b = (Block)s;	
						b.destroy(p);
					}
					hitting = false;
				}
			}
			
		}else{
			if(goal.getMiddle().copy().sub(pos).length()<0.1){
				moving  = false;
				tile = goal;
			}
			else{
				velo = (goal.getMiddle().copy().sub(pos).scale(0.3f));
				pos.add(velo);
			}
		}
		return true;
	}
	
	private boolean isFloor(Square s){
		return s instanceof FloorTile; 
	}
	
	private boolean isBlock(Square s){
		return s instanceof Block; 
	}

	@Override
	public void render(GameContainer c, Graphics g) {
		g.setColor(Color.green);
		g.fill(new Circle(pos.x, pos.y, 10));
	}

	public void setDirection(Vector2f a) {
		direction = a;
	}
	
	public Vector2f getPos() {
		return pos;
	}

	public Ellipse getEllipse() {
		return player;
	}

	public boolean intersects(Shape s) {
		/*if (player.intersects(s)) {
			return true;
		}*/
		return false;
	}

	public void hit() {
		hitting=true;
	}

}
