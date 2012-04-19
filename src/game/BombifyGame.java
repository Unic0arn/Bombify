package game;

import java.text.ParseException;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import settings.SettingsContainer;
/**
 * The main game container! Distributes all the effort across different
 * "gameStates" which basically are different "windows".
 */
public class BombifyGame extends StateBasedGame {
	static SettingsContainer gameSettings; // All the settings of the game stored in a HashMap
	public BombifyGame() {
		super("Bombify");
		try{
			gameSettings = new SettingsContainer("res/Bombify.cfg");
		} catch(ParseException e){
			System.out.println("Error in config file on line " + e.getErrorOffset());
			System.exit(0);
		}
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		addState(new Lobby());
		PlayState state = new PlayState(gameSettings);
		addState(state);
	}
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new BombifyGame());

		// Application properties
		app.setDisplayMode(Integer.parseInt(gameSettings.get("GAME_SIZE_X")),
				Integer.parseInt(gameSettings.get("GAME_SIZE_Y")), false);
		//app.setSmoothDeltas(true);
		app.setTargetFrameRate(Integer.parseInt(gameSettings.get("MAX_FPS")));
		app.setTargetFrameRate(60);
		app.setVSync(true);
		app.start();
	}
}
