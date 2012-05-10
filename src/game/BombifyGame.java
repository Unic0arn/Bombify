package game;

import java.text.ParseException;
import settings.*;
import menu.*;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;



/**
 * The main game container! Distributes all the effort across different
 * "gameStates" which basically are different "windows".
 * 
 * @author Fredrik & Victor
 * @version 2012-04-26
 */
public class BombifyGame extends StateBasedGame{
	static SettingsContainer gameSettings; // All the settings of the game stored in a HashMap
	public BombifyGame() {
		super("Bombify");
		try{			
			gameSettings = new SettingsContainer("res/config/Bombify.cfg");
		} catch(ParseException e){
			System.out.println("Error in config file on line " + e.getErrorOffset());
			System.exit(0);
		}		
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new Lobby(gameSettings));		
		addState(new SettingsMenu(gameSettings,this));
		addState(new Instructions(gameSettings));
		addState(new PlayState(gameSettings));
	}
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new BombifyGame());		

		// Application properties
		app.setDisplayMode(
				Integer.parseInt(gameSettings.get("GAME_SIZE_X")),
				Integer.parseInt(gameSettings.get("GAME_SIZE_Y")), 
				Integer.parseInt(gameSettings.get("FULLSCREEN"))==1);
		app.setSmoothDeltas(true);
		app.setTargetFrameRate(Integer.parseInt(gameSettings.get("MAX_FPS")));
		app.setTargetFrameRate(60);
		app.setShowFPS(false);
		app.setVSync(true);
		app.start();		
	}
}
