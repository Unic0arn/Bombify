package guiParts;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Button {
	private final int posx,posy,sizex,sizey;
	private  String buttonString;
	private String buttonCommand;
	private int shortcut;
	public Button(int x, int y, int xx, int yy){
		posx=x;
		posy=y;
		sizex=xx;
		sizey=yy;
		buttonCommand = "default";
	}
	public Button(int x, int y, int xx, int yy,String string){
		this(x,y,xx,yy);
		buttonCommand = string;
		buttonString = string;
	}
	public Button(int x, int y, int xx, int yy,String command,String text){
		this(x,y,xx,yy);
		buttonCommand = command;
		buttonString = text;
	}
	public int getPosx() {
		return posx;
	}
	public int getPosy() {
		return posy;
	}
	public int getSizex() {
		return sizex;
	}
	public int getSizey() {
		return sizey;
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
			g.setColor(Color.blue);	
			g.fill(new Rectangle(posx,posy,sizex,sizey));
			g.setColor(Color.black);
			g.drawString(buttonString, posx, posy);
	}
	public void setShortCut(int gameState){
		shortcut = gameState;
	}
	public boolean isClicked(Vector2f mouse) {
		if(mouse.getX() > posx && mouse.getX() < posx+sizex && mouse.getY() > posy && mouse.getY() < posy+sizey){
			return true;
		}
		return false;
	}
	public int getShortCut() {
		return shortcut;
	}
}
