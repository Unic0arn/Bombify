package settings;
import guiParts.Button;
import game.Constants;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

/**
 *  This SettingsMenu will change state of the screen
 *  like resolution etc.   
 * 
 * @author Fredrik & Victor
 * @version 2012-04-22
 * 
 */
public class SettingsMenu extends BasicGameState {
	private Image menu = null, main=null;
	Button[] b;

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		menu = new Image("res/menu.png");
		main = new  Image("res/main.png");

		b = new Button[1];
		b[0] = new Button(50, 250, 200, 100, main);
		b[0].setShortCut(Constants.LOBBY);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		menu.draw(0, 0);
		for (int i = 0; i < b.length; i++) {
			b[i].render(container, game, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input in = container.getInput();

		if (in.isMousePressed(0)) {
			Vector2f mousePos = new Vector2f(in.getAbsoluteMouseX(),
					in.getAbsoluteMouseY());
			for (int i = 0; i < b.length; i++) {
				if (b[i].isClicked(mousePos)) {
					game.enterState(b[i].getShortCut());
				}
			}
		}
	}

	@Override
	public int getID() {
		return Constants.SETTINGMENU;
	}
}
