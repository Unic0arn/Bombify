package menu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import game.BombifyGame;
import game.Constants;
import guiParts.Button;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image;

import settings.SettingsContainer;

/**
 *  This SettingsMenu will change state of the screen
 *  like resolution etc.   
 * 
 * @author Fredrik & Victor
 * @version 2012-05-08
 * 
 */
public class SettingsMenu extends BasicGameState {
	private Image menu, main, quit, change;
	Button[] b;
	Button returnButton, quitButton;
	String[] settingList;
	AppGameContainer app;
	SettingsContainer sc;
	BombifyGame bg;
	LinkedList<String> settings;
	
	public SettingsMenu(SettingsContainer gameSettings, BombifyGame bombifyGame) {
		sc = gameSettings;
		bg = bombifyGame;
	}

	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		
		/* Menu images */
		menu = new Image("res/menu.png");
		main = new  Image("res/main.png");
		quit = new Image("res/quit.png");
		change = new Image("res/change.png");
		
		List<String> mapKeys = new ArrayList<String>(sc.keySet());
		TreeSet<String> sortedKeys = new TreeSet<String>(mapKeys);
		settings = new LinkedList<String>(); 
		Iterator<String> it = sortedKeys.iterator();
		
		
		/* Get settings and store in a list */
		for(int i = 0; i< sortedKeys.size();i++){
			String temp;
			if(it.hasNext()){
				temp = it.next();
				if(!temp.matches("P[1-4][N|S|W|E|B]")) {
					settings.add(temp);
				}
			}
		}
		
		/* Setting buttons */
		b = new  Button[settings.size()];
		for(int i=0; i<settings.size();i++){
			b[i] = new Button(250,50*i+175,50,50,change);
		}
		/* Return buttons */
		returnButton = new Button(200,450,100,75,main);
		returnButton.setShortCut(Constants.LOBBY);
		
		quitButton = new Button(50, 450, 100, 75, quit);	
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		menu.draw(0, 0);
		returnButton.render(container, game, g);
		quitButton.render(container, game, g);
		
		for (int i = 0; i < b.length; i++) {
			b[i].render(container, game, g);
			g.drawString(settings.get(i) + ": " + sc.get(settings.get(i)), 50,56*i+175);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		Input in = container.getInput();		

		if (in.isMousePressed(0)) {
			for(int i= 0; i < b.length;i++){
				Vector2f mousePos = new Vector2f(in.getAbsoluteMouseX(),
						in.getAbsoluteMouseY());

				/* Will replace new String in 'Bombify.cfg if player changed 
				  anything and new settings will work after restart */
				if (b[i].isClicked(mousePos)) {
					String tempString = b[i].getString();
					String tempValue = JOptionPane.showInputDialog(null);
					sc.remove(tempString);
					sc.put(tempString, tempValue);
					System.out.println(tempString);
					System.out.println(tempValue);
					//sc.writeToFile();

				}
				/* Return to main menu */
				if(returnButton.isClicked(mousePos)){
					game.enterState(returnButton.getShortCut());
				}
				
				/* Quit game */
				if(quitButton.isClicked(mousePos)){
					container.exit();
				}
			}
		}
	}

	@Override
	public int getID() {
		return Constants.SETTINGMENU;
	}
}
