package game;

import guiParts.Button;

import org.newdawn.slick.Color;
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
 * @version 2012-04-19
 *
 */
public class Lobby extends BasicGameState {
	Button[] b; // An array of buttons.
	private Image menu = null, start=null, settings=null; 

	public Lobby(){

	}
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		menu = new Image("res/menu.png");
		start = new Image("res/start.png");
		settings = new Image("res/settings.png");
		
//		b = new Button[3];
//		b[0] = new Button(200,150,100,50, "MAIN");
//		b[1] = new Button(200,250,100,50,"Game");
//		b[2] = new Button(200,300,100,50,"Settings");		
//		b[1].setShortCut(Constants.GAME);
//		b[2].setShortCut(Constants.SETTINGMENU);
//		b[2].setButtonColor(Color.red);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {		
		menu.draw(0,0);
		start.draw(100, 150);
		settings.draw(100, 250);
//		for(int i= 0; i < b.length;i++){
//			b[i].render(container, game, g);
//		}
	}
	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		
		Input in = container.getInput();

//		if(in.isMousePressed(0)){
//			Vector2f mousePos = new Vector2f( in.getAbsoluteMouseX(), in.getAbsoluteMouseY());
//			for(int i= 0; i < b.length;i++){
//				if(b[i].isClicked(mousePos)){
//					game.enterState(b[i].getShortCut());
//				}
//			}
//		}
	}
	@Override
	public int getID() {
		return Constants.LOBBY;
	}
}
