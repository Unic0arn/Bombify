package map;

import org.newdawn.slick.Animation;
//import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
//import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import entities.Bomb;
import entities.Item;
import entities.Player;
/**
 * A class that contains information about a tile on the floor.
 * @author Fredrik & Victor
 * @version 2012-04-25
 *
 */
public class FloorTile implements Square {
	private Image img;
	private Item item;
	private Player player;
	int posx,posy,sizex,sizey,gridx,gridy;
	private Bomb bomb;
	
	
	public FloorTile(int x, int y, GameContainer container, int tiles){
		gridx = x;
		gridy = y;
		sizex = (container.getWidth()/tiles);
		sizey = (container.getHeight()/tiles);
		posx = (container.getWidth()/tiles * x);
		posy = (container.getHeight()/tiles * y);
	}
	@Override
	public void render(GameContainer container, Graphics g, int x, int y,
			int tiles) throws SlickException {
		if(img!=null){			
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
	}
	@Override
	public FloorTile setImg(Image i){
		img = i;
		return this;
	}
	public Vector2f getMiddle(){
		return new Vector2f(posx+(sizex/2f), posy+(sizey/2f));
	}

	public Vector2f getCorner(){
		return new Vector2f(posx, posy);
	}
	//----------------------------------
	public boolean hasBomb(){
		return bomb != null;
	}
	public Bomb getBomb(){
		return bomb;
	}
	public void setBomb(Bomb bomb) {
		this.bomb = bomb;
	}
	//---------------------------------
	public boolean hasItem(){
		return item != null;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	//---------------------------------
	public boolean hasPlayer(){
		return player != null;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	//---------------------------------
	public int getGridx() {
		return gridx;
	}
	public int getGridy() {
		return gridy;
	}
	public String toString(){
		return "[" + gridx + "," + gridy + "]";
	}
}

