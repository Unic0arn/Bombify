package settings;

import game.Constants;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

/**
 * This is going to be a settings panel!
 * @author Fredrik
 *
 */
public class SettingsMenu extends BasicGameState{
	private Image menu = null; 
	
	public int getID() {
		return Constants.SETTINGMENU;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		menu = new Image("res/menu.png");
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		menu.draw(0,0);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		// TODO Auto-generated method stub
	}
}
