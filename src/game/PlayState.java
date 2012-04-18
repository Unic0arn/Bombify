package game;

import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import settings.SettingsContainer;
//import org.newdawn.slick.Image; 

import entities.Player;
import map.Block; 
/**
 * The main class of the game Bombify. A game that is based on players trying to blow each other up.
 * 
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-16
 *
 */

public class PlayState extends BasicGameState {
	Boolean paused =false;
	SettingsContainer gameSettings;
	HashMap<String,Integer> playerControls;
	Player[] player;
	Rectangle field;
	Block wall; 
	int players;
	int hitCounter = 0;
	

	public PlayState(SettingsContainer gs) {
		super();
		gameSettings = gs;
	}
	private void parseControls() {
		playerControls = new HashMap<String,Integer>();
		for(int i = 0; i < players;i++){
			playerControls.put("P"+(i+1)+"N",Integer.parseInt(gameSettings.get("P"+(i+1)+"N")));
			playerControls.put("P"+(i+1)+"S",Integer.parseInt(gameSettings.get("P"+(i+1)+"S")));
			playerControls.put("P"+(i+1)+"W",Integer.parseInt(gameSettings.get("P"+(i+1)+"W")));
			playerControls.put("P"+(i+1)+"E",Integer.parseInt(gameSettings.get("P"+(i+1)+"E")));
		}
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		paused = false;
	}

	@Override
	public int getID() {
		return 1;
	}
	
	/**
	 * Starting position for field and players. 
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		players = Integer.parseInt(gameSettings.get("PLAYERS"));
		parseControls();
		
		field = new Rectangle(0, 0, 
				gc.getWidth(),
				gc.getHeight());
		player = new Player[players];
		player[0] = new Player(new Vector2f(70, 70));
		player[1] = new Player(new Vector2f(730, 530));
	}



	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g)
			throws SlickException {
		
		renderBackground(c,g);
		renderItems(c,g);
		renderWalls(c,g);
		renderPlayers(c,g);
	}



	@Override
	public void update(GameContainer c, StateBasedGame game, int delta)
			throws SlickException {
		Input in = c.getInput();
		if (in.isKeyDown(Input.KEY_ESCAPE)) {
			c.exit();
		}
		updateBombs(c,delta,in);
		updatePlayers(c,delta,in);
		updateItems(c,delta,in);
		updateWalls(c,delta,in);
	}


	private void updateWalls(GameContainer c, int delta, Input in) {
		// TODO Auto-generated method stub

	}
	private void updateItems(GameContainer c, int delta, Input in) {
		// TODO Auto-generated method stub

	}
	private void updatePlayers(GameContainer c, int delta, Input in) {
		for(int i = 0; i < player.length; i++){
			Vector2f accel = new Vector2f(0, 0);
			if (in.isKeyDown(playerControls.get("P"+(i+1)+"N"))) {
				accel.add(new Vector2f(0, -1));
			}
			if (in.isKeyDown(playerControls.get("P"+(i+1)+"S"))) {
				accel.add(new Vector2f(0, 1));
			}
			if (in.isKeyDown(playerControls.get("P"+(i+1)+"W"))) {
				accel.add(new Vector2f(-1, 0));
			}
			if (in.isKeyDown(playerControls.get("P"+(i+1)+"E"))) {
				accel.add(new Vector2f(1, 0));
			}
			player[i].setAccel(accel.normalise().scale(50f));
			if(player[i].intersects(field)){
				player[i].setAccel(accel.normalise().scale(50f));
				player[i].update(c, delta);
			}
			player[i].update(c, delta);
		}

		for(int i = 0; i < player.length; i++){
			for(int j = 0; j < player.length; j++){
				if(player[0].intersects(player[1].getEllipse())){
					hitCounter++;
					System.out.println("OMG YOU HIT HIM!!! " + hitCounter);
				}
			}
		}
	}
	private void updateBombs(GameContainer c, int delta, Input in) {
		// TODO Auto-generated method stub

	}
	private void renderBackground(GameContainer c, Graphics g) {
		g.setBackground(Color.black);
		g.setAntiAlias(true);		
	}
	private void renderPlayers(GameContainer c, Graphics g) {
		for(int i = 0; i < player.length; i++){
			player[i].render(c, g);
		}
	}
	/**
	 * Fixed walls soon to be pictures.
	 * @param gc
	 * @param g
	 */
	private void renderWalls(GameContainer gc, Graphics g) {
		g.setColor(Color.white);		
		
		// X-wall above and under. 
		for (int i = 20; i < 760; i += 40) {			
			g.fillRect(i, 0, 40, 40);
			g.fillRect(i, 560, 40, 40);
		}
		
		// Y-wall R/L-side. 
		for (int i = 0; i < 560; i += 40) {
			g.fillRect(0, i, 40, 40);
			g.fillRect(760, i, 40, 40);
		}
		
		// Concrete walls
		for (int i = 100; i < 720; i+=110) {
			g.fillRect(i, 100, 50, 50);
			g.fillRect(i, 212, 50, 50);
			g.fillRect(i, 332, 50, 50);
			g.fillRect(i, 448, 50, 50);
		}
	}
	
	
	private void renderItems(GameContainer c, Graphics g) {
		// TODO Auto-generated method stub
	}
	
	
	







	




}
