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
/**
 * A lobby to welcome the player when first starting the application.
 * Contains buttons with shortcuts to the game or settings.
 * @author Fredrik
 *
 */
public class Lobby extends BasicGameState {
	Button[] b; // An array of buttons.

	public Lobby(){

	}
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		b = new Button[2];
		b[0] = new Button(320,200,100,50,"Start");
		b[1] = new Button(320,250,100,50,"Menu");
		b[0].setShortCut(Constants.GAME);
		b[1].setShortCut(Constants.SETTINGMENU);
		b[1].setButtonColor(Color.red);
	}
	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
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
		}
	}
	@Override
	public int getID() {
		return Constants.LOBBY;
	}
}
