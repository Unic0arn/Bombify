package guiParts;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;

public class Button {
	private final int posx,posy,sizex,sizey;
	private  String buttonString; //What should be written on the button.
	private int shortcut; //Where this button leads, the constants are in the Constants class.
	private Image img;
	public Button(int x, int y, int xx, int yy){
		posx=x;
		posy=y;
		sizex=xx;
		sizey=yy;
	}
	public Button(int x, int y, int xx, int yy,Image im){
		posx=x;
		posy=y;
		sizex=xx;
		sizey=yy;
		img=im;
	}
	public Button(int x, int y, int xx, int yy,String string){
		this(x,y,xx,yy);
		buttonString = string;
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
	
	/**
	 * Renders the button with blue color and black font.
	 * @param container
	 * @param game
	 * @param g
	 * @throws SlickException
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
			g.setColor(Color.blue);	
			g.fill(new Rectangle(posx,posy,sizex,sizey));
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
			else{
				g.setColor(Color.black);
				g.drawString(buttonString, posx, posy);
			}
	}
	/**
	 * Set the shortcut to where this button leads.
	 * @param gameState
	 */
	public void setShortCut(int gameState){
		shortcut = gameState;
	}
	/**
	 * Checks weather the mouse is inside the frame of this button.
	 * @param mouse
	 * @return true - if mouse is inside, false - if mouse is not inside.
	 */
	public boolean isClicked(Vector2f mouse) {
		if(mouse.getX() > posx && mouse.getX() < posx+sizex && mouse.getY() > posy && mouse.getY() < posy+sizey){
			return true;
		}
		return false;
	}
	public int getShortCut() {
		return shortcut;
	}
	public void setImage(Image im) {
		img = im;
	}
}
