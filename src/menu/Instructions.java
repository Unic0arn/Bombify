package menu;

import game.Constants;
import game.PlayState;
import guiParts.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import settings.SettingsContainer;

public class Instructions extends BasicGameState {
	Image menu;
	Button[] button; 	
	SettingsContainer sc;
	
	
	public Instructions(SettingsContainer sc){
		this.sc = sc;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		//TODO
		//menu = new Image("res/menu.png");
//		button = new Button[1];
//		button[0]= new Button(50, 450, 200, 100, quit);
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		//TODO
		//g.drawImage(menu,0,0);		
	}

	@Override
	public void update(GameContainer gameCont, StateBasedGame game, int delta)
			throws SlickException {
		//TODO
		//game.addState(new PlayState(sc));		
	}

	@Override
	public int getID() {
		return Constants.INSTRUCTIONS;
	}

}
