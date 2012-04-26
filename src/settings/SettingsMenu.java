package settings;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import guiParts.Button;
import game.BombifyGame;
import game.Constants;

import org.newdawn.slick.AppGameContainer;
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
	private Image menu, main;
	Button[] b;
	Button returnButton;
	String[] settingList;
	AppGameContainer app;
	SettingsContainer sc;
	BombifyGame bg;
	public SettingsMenu(SettingsContainer gameSettings, BombifyGame bombifyGame) {
		sc = gameSettings;
		bg = bombifyGame;

	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		List<String> mapKeys = new ArrayList<String>(sc.keySet());
		TreeSet<String> sortedKeys = new TreeSet<String>(mapKeys);
		menu = new Image("res/menu.png");
		main = new  Image("res/main.png");
		b = new Button[sortedKeys.size()];
		settingList = new String[sortedKeys.size()];
		Iterator<String> it = sortedKeys.iterator();
		String temp;
		for(int i = 0; i< b.length;i++){
			if(it.hasNext()){
				temp = it.next();
				settingList[i] = temp;
				b[i] = new Button(200,20*i+50,50,20,temp);
				b[i].setImage(main);
			}
		}
		returnButton = new Button(300,50,50,50,main);
		returnButton.setShortCut(Constants.LOBBY);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		menu.draw(0, 0);
		for (int i = 0; i < b.length; i++) {
			b[i].render(container, game, g);
			g.drawString(settingList[i] + ": " + sc.get(settingList[i]), 20,20*i+50);
		}
		returnButton.render(container, game, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input in = container.getInput();		

		if (in.isMousePressed(0)) {
			for(int i= 0; i < b.length;i++){
				Vector2f mousePos = new Vector2f(in.getAbsoluteMouseX(),
						in.getAbsoluteMouseY());

				if (b[i].isClicked(mousePos)) {
					String tempString = b[i].getString();
					String tempValue = JOptionPane.showInputDialog(null);
					sc.remove(tempString);
					sc.put(tempString, tempValue);
				}
				if(returnButton.isClicked(mousePos)){
					game.enterState(returnButton.getShortCut());
				}
			}
		}
	}

	@Override
	public int getID() {
		return Constants.SETTINGMENU;
	}
}
