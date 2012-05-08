package menu;

import java.util.Date;

import game.Constants;
import guiParts.Button;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import settings.SettingsContainer;

@SuppressWarnings("deprecation")
public class Instructions extends BasicGameState {
	Image menu; //Background
	Button[] button; //Button vector
	SettingsContainer sc; 
	Date startTime;	//Count down for game
	StateBasedGame theGame;
	public Instructions(SettingsContainer sc){
		this.sc = sc;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		startTime = new Date(); 
		menu = new Image("res/new/instructions.png");
		theGame = game;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		g.drawImage(menu,0,0);		
	}

	/* Check if the time is under 3 seconds otherwise continue draw menu */
	@Override
	public void update(GameContainer gameCont, StateBasedGame game, int delta)
			throws SlickException {
		Date newTime = new Date();		
		if(newTime.getSeconds() > startTime.getSeconds() + 4){
			game.enterState(Constants.GAME);
		}
	}
	@Override
	public void keyPressed(int kc, char v) {
	  theGame.enterState(Constants.GAME);
	}  
	@Override
	public int getID() {
		return Constants.INSTRUCTIONS;
	}
}
