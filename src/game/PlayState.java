package game;

import java.util.HashMap;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Image; 
import settings.SettingsContainer;

import entities.Player;
import map.Block; 
import map.OuterWall;
import map.Square;
import map.FloorTile;
/**
 * This is the playing part of the game.
 * A game that is based on players trying to blow each other up.
 * 
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-16
 *
 */

public class PlayState extends BasicGameState {
	Boolean paused =false; //Maybe for pausing the game not used at the moment!
	SettingsContainer gameSettings; //An object to conatin all settings.

	HashMap<String,Integer> playerControls; //Store the player controls in a hashmap.
	Player[] player; //A vector of the players.
	Square[][] tiles; //A grid of all the "tiles" in the game.
	int players; // The amount of players 1-4
	int hitCounter = 0;
	private Image outerBrick = null, concrete = null,floorTile = null;
	int nrtiles = 11; // must be odd otherwise map is weird.
	/**
	 * Creates a new game with the desired settings.
	 * @param gs - The settings file.
	 * @throws SlickException
	 */
	public PlayState(SettingsContainer gs) throws SlickException {
		super();
		gameSettings = gs;
	}
	/**
	 * Parses the most used settings from the hashmap.
	 * This is to avoid constant parsing and ParseInts which are
	 * time consuming.
	 */
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
		parseSettings(); // Start by parsing all the settings.
		//Assign the images.
		outerBrick = new Image("res/brick.png");
		floorTile = new Image("res/brick_3.png");
		concrete = new Image("res/brick2.png");
		//Creates the players and gives them positions.
		player = new Player[players];
		player[0] = new Player(new Vector2f((gc.getWidth()/nrtiles)+20, gc.getHeight()/nrtiles+20));
		player[1] = new Player(new Vector2f(gc.getWidth()-gc.getWidth()/nrtiles+20, gc.getHeight()-gc.getHeight()/nrtiles+20));
		//Defines the tiles.
		tiles = new Square[nrtiles][nrtiles];

		/*
		 * Quite complex "algorithm" to decide where walls and tiles 
		 * should be placed.
		 * 
		 */
		for(int i = 0;i<nrtiles;i++){
			for(int j = 0;j<nrtiles;j++){
				if(i == 0||i ==nrtiles-1||j==0||j==nrtiles-1){
					tiles[i][j] = new OuterWall();
					tiles[i][j].setImg(outerBrick);
				}else if(j%2==0 && i%2==0){
					tiles[i][j] = new Block();
					tiles[i][j].setImg(concrete);
				}else{

					tiles[i][j] = new FloorTile();
					tiles[i][j].setImg(floorTile);
				}
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
		//Just checking if anyone pressed the escape key to end game.
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
		for(int i =0;i<nrtiles;i++){
			for(int j = 0;j<nrtiles;j++){
				tiles[i][j].render(gc,g, i, j, nrtiles);
			}
		}
	}
	private void renderItems(GameContainer c, Graphics g) {
		// TODO Auto-generated method stub
	}
}
