package entities;

import game.PlayState;
import map.FloorTile;
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
	Vector2f accel = new Vector2f(0, 0);
	boolean moving = true;

	public Player(FloorTile tile) {
		this.tile = tile;
		pos = tile.getMiddle();
		goal = tile;
		System.out.println(tile.getGridx());
	}

	public boolean update(GameContainer c, int delta, PlayState p) {
		if(!moving) {
			if(!accel.equals(new Vector2f(0,0))){
				if(p.getTiles()[(int) (tile.getGridx() + accel.x)][(int) (tile.getGridy() + accel.y)] instanceof FloorTile){
					goal = (FloorTile) p.getTiles()[(int) (tile.getGridx() + accel.x)][(int) (tile.getGridy() + accel.y)];
					moving = true;
					System.out.println(goal);
				}
			}
		}else{
			System.out.println(pos);
			System.out.println(goal.getMiddle());
			if(goal.getMiddle().copy().sub(pos).scale(0.1f).length()<0.1){
				moving  = false;
				tile = goal;
				System.out.println("lol");
			}
			else{
				velo = (goal.getMiddle().copy().sub(pos).scale(0.5f));
				pos.add(velo);
			}
		}
		
		/*velo.add(accel.copy().scale(delta / 1000f));
		accel.set(0, 0);
		*/
		
		
		//player.setLocation(pos);
		return true;
	}

	@Override
	public void render(GameContainer c, Graphics g) {
		g.setColor(Color.green);

		g.fill(new Circle(pos.x, pos.y, 10));
	}

	public void setAccel(Vector2f a) {
		accel = a;
	}
	public void setSpeed(Vector2f a){
		velo = a;
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

}
