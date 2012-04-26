package settings;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JOptionPane;

import guiParts.Button;
import game.BombifyGame;
import game.Constants;
import game.Lobby;
import game.PlayState;

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
	private int xRes = 800, yRes = 600;
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
		/*b[0] = new Button(50, 250, 200, 100, main);
		b[0] = new Button(50, 150, 50, 50, "main");
		b[1] = new Button(50, 250, 50, 50, "640 x 480");
		b[2] = new Button(50, 350, 50, 50, "start");
		b[0].setShortCut(Constants.LOBBY);
		b[2].setShortCut(Constants.GAME);	*/
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
				//game.enterState(b[i].getShortCut());
			}
			if(returnButton.isClicked(mousePos)){
				//sc.writeToFile();
				game.enterState(returnButton.getShortCut());
			}
//			
//			if (b[2].isClicked(mousePos)) {
//				game.enterState(b[2].getShortCut());
//			}
			
			/*if (b[1].isClicked(mousePos)) {
				AppGameContainer app = new AppGameContainer(new BombifyGame());
				app.setDisplayMode(640, 480, false);
				game.enterState(b[0].getShortCut());
				//setResoulution(640, 480);
			}*/
			}
		}
	}
	
	private void setResoulution(int x, int y){
		this.xRes = x;
		this.yRes = y;
	}
	
	public int getYres() {
		return yRes;
	}
	
	public int getXres() {
		return xRes;
	}

	@Override
	public int getID() {
		return Constants.SETTINGMENU;
	}
}
