package menu;

import game.Constants;
import guiParts.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import settings.SettingsContainer;

/**
 * A lobby to welcome the player when first starting the application.
 * Contains buttons with shortcuts to the game or settings.
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-26
 *
 */
public class Lobby extends BasicGameState {	
	Button[] b; // An array of buttons.
	private Image menu, start, settings, quit;
	Sound fx;	

	SettingsContainer sc;
	public Lobby(SettingsContainer sc){
		this.sc = sc;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {		
		
		menu = new Image("res/menu.png");
		start = new Image("res/start.png");
		settings = new Image("res/settings.png");
		quit = new Image("res/quit.png");

		b = new Button[4];
		b[0] = new Button(50, 450, 200, 100, quit);
		b[1] = new Button(50, 250, 200, 100, start);
		b[2] = new Button(50,350,200,100, settings);
		b[3] = new Button(0,0,0,0,"");

		b[1].setShortCut(Constants.GAME);
		b[2].setShortCut(Constants.SETTINGMENU);
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
			}

			if(b[0].isClicked(mousePos)){
				container.exit();
			}
		}
	}

	@Override
	public int getID() {
		return Constants.LOBBY;
	}
}
