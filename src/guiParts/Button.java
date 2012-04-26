package guiParts;


import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.StateBasedGame;
/**
 * This is a helper class for Lobby and SettingsMenu
 * to navigate the user for the right option. 
 * @author Fredrik & Victor
 * @version 2012-04-22
 *
 */
public class Button {
	private  int posx,posy,sizex,sizey; // Self explanatory.
	private  String buttonString; //What should be written on the button.
	private int shortcut; //Where this button leads, the constants are in the Constants class.
	private Image img; // The image on the button.
	private Color buttonColor = Color.blue,
			stringColor = Color.white; // Colors in case an image is missing.
	/**
	 * Creates a button at the specified location with the specified size.
	 * @param x - The x-Position of the button relative to the game-window
	 * @param y - The y-Position of the button relative to the game-window
	 * @param xx - The width of the button
	 * @param yy - The height of the button
	 */
	public Button(int x, int y, int xx, int yy){
		posx=x;
		posy=y;
		sizex=xx;
		sizey=yy;

	}
	/**
	 * Creates a button at the specified location with the specified size.
	 * Also adds an image to the button.
	 * @param x - The x-Position of the button relative to the game-window
	 * @param y - The y-Position of the button relative to the game-window
	 * @param xx - The width of the button
	 * @param yy - The height of the button
	 * @param im - An image to be added to the button.
	 */
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
	 * Renders the button.
	 * @param container
	 * @param game
	 * @param g
	 * @throws SlickException
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {


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
			g.setColor(buttonColor);	
			g.fill(new Rectangle(posx,posy,sizex,sizey));
			g.setColor(stringColor);

			g.drawString(buttonString, posx, posy);
		}
	}
	/**
	 * Set the shortcut to where this button leads.
	 * The shortcut is in the form of an int which is specified
	 * in the Constants class.
	 * @param gameState - An int which points to another gameState (see constants)
	 */
	public void setShortCut(int gameState){
		shortcut = gameState;
	}
	/**
	 * Returns the shortcut this button holds.
	 * @return - An int which points to another gameState (see constants)
	 */
	public int getShortCut() {
		return shortcut;
	}
	/**
	 * Checks weather the mouse is inside the frame of this button.
	 * @param mouse - A vector containing the x and y coordinates of the mouse pointer.
	 * @return true - if mouse is inside, false - if mouse is not inside.
	 */
	public boolean isClicked(Vector2f mouse) {
		if(mouse.getX() > posx && mouse.getX() < posx+sizex && mouse.getY() > posy && mouse.getY() < posy+sizey){
			return true;
		}
		return false;
	}
	/**
	 * Sets the image of this button.
	 * @param im - The image.
	 */
	public void setImage(Image im) {
		img = im;
	}
	public void setButtonColor(Color c){
		buttonColor = c;
	}
	public void setStringColor(Color c){
		stringColor = c;
	}
	public String getString(){
		return buttonString;
	}
	/**
	 * Brute force quitting of the program. 
	 * @param c
	 */
	public void quit(GameContainer c) {
		c.exit();		
	}
}
