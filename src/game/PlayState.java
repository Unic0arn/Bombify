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
import org.newdawn.slick.Image; 
import settings.SettingsContainer;

import entities.Player;
import map.Block; 
import map.Square;
import map.Tile;
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
	Square[][] tiles;
	Block wall; 
	int players;
	int hitCounter = 0;
	private Image outerBrick, concrete = null;
	int nrtiles = 10;

	public PlayState(SettingsContainer gs) {
		super();
		gameSettings = gs;
	}
	private void parseSettings() {
		players = Integer.parseInt(gameSettings.get("PLAYERS"));
		playerControls = new HashMap<String,Integer>();
		for(int i = 0; i < players;i++){
			playerControls.put("P"+(i+1)+"N",Integer.parseInt(gameSettings.get("P"+(i+1)+"N")));
			playerControls.put("P"+(i+1)+"S",Integer.parseInt(gameSettings.get("P"+(i+1)+"S")));
			playerControls.put("P"+(i+1)+"W",Integer.parseInt(gameSettings.get("P"+(i+1)+"W")));
			playerControls.put("P"+(i+1)+"E",Integer.parseInt(gameSettings.get("P"+(i+1)+"E")));
		}
	}


	@Override
	public int getID() {
		return Constants.GAME;
	}
	
	/**
	 * Starting position for field and players. 
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		parseSettings();
		player = new Player[players];
		player[0] = new Player(new Vector2f(70, 70));
		player[1] = new Player(new Vector2f(730, 530));
		tiles = new Square[nrtiles][nrtiles];
		for(int i = 1;i<nrtiles;i+=2){
			for(int j = 1;j<nrtiles;j+=2){
				tiles[i][j] = new Block();
			}
		}
		for(int i = 0;i<nrtiles;i+=2){
			for(int j = 0;j<nrtiles;j+=2){
				tiles[i][j] = new Tile();
			}
		}
		for(int i = 0;i<nrtiles;i+=2){
			for(int j = 1;j<nrtiles;j+=2){
				tiles[i][j] = new Tile();
			}
		}
		for(int i = 1;i<nrtiles;i+=2){
			for(int j = 0;j<nrtiles;j+=2){
				tiles[i][j] = new Tile();
			}
		}
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
			/*if(player[i].intersects(field)){
				player[i].setAccel(accel.normalise().scale(50f));
				player[i].update(c, delta);
			}*/
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
	 * @throws SlickException 
	 */
	private void renderWalls(GameContainer gc, Graphics g) throws SlickException {
		g.setColor(Color.white);
		
		
		/*
		// Concrete walls
		for (int i = 100; i < 720; i+= 110) {
			concrete.draw(i, 100);
			concrete.draw(i, 212);
			concrete.draw(i, 332);
			concrete.draw(i, 448);
		}
		
		// y-wall right/left side. 
		for (int i = 30; i < 570; i += 30) {
			g.fillRect(0, i, 30, 30);
			g.fillRect(770, i, 30, 30);
		}*/
		
		for(int i = 1;i<nrtiles;i++){
			for(int j = 1;j<nrtiles;j++){
				tiles[i][j].render(gc,g, i, j, nrtiles-1);
			}
		}
		
		/*
		// Fixed middle thingy. 
		for (int i = 110; i < 770; i+=110) {
			g.fillRect(i, 100, 50, 50);
			g.fillRect(i, 210, 50, 50);
			g.fillRect(i, 320, 50, 50);
			g.fillRect(i, 430, 50, 50);
		}*/
	}
	
	
	private void renderItems(GameContainer c, Graphics g) {
		// TODO Auto-generated method stub
	}
	
	
	







	




}
