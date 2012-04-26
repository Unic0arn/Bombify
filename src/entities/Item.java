package entities;

import game.PlayState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
//import org.newdawn.slick.state.StateBasedGame;

/**
 * Different Items changes state of players. 
 * @author Fredrik & Victor
 * @version 2012-04-25
 *
 */
public interface Item{
	public void render(GameContainer c, Graphics g);
	public void update(GameContainer c, PlayState game, int delta);
	public int getID();
}

