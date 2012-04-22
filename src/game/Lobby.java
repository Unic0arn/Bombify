package game;

import guiParts.Button;
//import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
/**
 * A lobby to welcome the player when first starting the application.
 * Contains buttons with shortcuts to the game or settings.
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-22
 *
 */
public class Lobby extends BasicGameState {
	Button[] b; // An array of buttons.
	private Image menu, start, settings, quit; 
	//menu = null, start=null, main=null, settings=null; 

	public Lobby(){

	}
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		menu = new Image("res/menu.png");
		start = new Image("res/start.png");
		settings = new Image("res/settings.png");
		quit = new Image("res/quit.png");
		
		b = new Button[3];
		b[0] = new Button(50, 250, 200, 100, start);
		b[1] = new Button(50,350,200,100,settings);	
		b[2] = new Button(50, 450, 200, 100, quit);
		b[0].setShortCut(Constants.GAME);
		b[1].setShortCut(Constants.SETTINGMENU);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {		
		menu.draw(0,0);
		for(int i= 0; i < b.length;i++){
			b[i].render(container, game, g);
		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input in = container.getInput();
		
		if(in.isMousePressed(0)){
			Vector2f mousePos = new Vector2f( in.getAbsoluteMouseX(), in.getAbsoluteMouseY());
			for(int i= 0; i < b.length;i++){
				
				if(b[i].isClicked(mousePos)){
					game.enterState(b[i].getShortCut());
				}
				
				if(b[2].isClicked(mousePos)){
					b[2].quit(container);
				}
			}
		}
	}
	@Override
	public int getID() {
		return Constants.LOBBY;
	}
}
