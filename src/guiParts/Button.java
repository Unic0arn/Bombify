package guiParts;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
/**
 * A button that can either have an image or a string
 * @author Fredrik
 *
 */
public class Button {
	private  int posx,posy,sizex,sizey;
	private  String buttonString; //What should be written on the button.
	private int shortcut; //Where this button leads, the constants are in the Constants class.
	private Image img;
	private Color buttonColor = Color.blue,
			stringColor = Color.black;
	public Button(int x, int y, int xx, int yy){
		posx=x;
		posy=y;
		sizex=xx;
		sizey=yy;
	}
	public Button(int x, int y, int xx, int yy,Image im){
		this(x,y,xx,yy);
		img=im;
	}
	/**
	 * Create the button with a String
	 * @param x - The x-Position of the button relative to the game-window
	 * @param y - The y-Position of the button relative to the game-window
	 * @param xx - The width of the button
	 * @param yy - The height of the button
	 * @param string - The String to be written on the button.
	 */
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
			g.setColor(buttonColor);	
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
				g.setColor(stringColor);
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
	public void setButtonColor(Color c){
		buttonColor = c;
	}
	public void setStringColor(Color c){
		stringColor = c;
	}
}
