package game;

import java.util.HashMap;
import java.util.Random;

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
	GameContainer gamecont;
	HashMap<String,Integer> playerControls; //Store the player controls in a hashmap.
	Player[] player; //A vector of the players.
	Square[][] tiles; //A grid of all the "tiles" in the game.
	int players; // The amount of players 1-4
	int hitCounter = 0;

	private Image outerBrick = null, concrete = null,floorTile = null, bomb = null;
	int nrtiles = 15; // must be odd otherwise map is weird.

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
			playerControls.put("P"+(i+1)+"B",Integer.parseInt(gameSettings.get("P"+(i+1)+"B")));
			

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
		gamecont = gc;
		parseSettings(); // Start by parsing all the settings.

		//Assign the images.
		outerBrick = new Image("res/brick.png");
		floorTile = new Image("res/metal.png");
		concrete = new Image("res/brick2.png");
		bomb = new Image("/res/bomb.png");


		//Creates the players and gives them positions.
		player = new Player[players];

		//Defines the tiles.
		tiles = new Square[nrtiles][nrtiles];

		/*
		 * Quite complex "algorithm" to decide where walls and tiles 
		 * should be placed.
		 * 
		 */
		for(int x = 0;x<nrtiles;x++){
			for(int y = 0;y<nrtiles;y++){
				if(x == 0||x ==nrtiles-1||y==0||y==nrtiles-1){
					tiles[x][y] = new OuterWall();
					tiles[x][y].setImg(outerBrick);
				}else if(y%2==0 && x%2==0){
					tiles[x][y] = new Block(x,y,gamecont,nrtiles);
					tiles[x][y].setImg(concrete);
				}else{

					tiles[x][y] = new FloorTile(x,y,gc,nrtiles);
					tiles[x][y].setImg(floorTile);

				}
			}
		}
		//Creates the players and gives them positions.
		player = new Player[players];
		System.out.println(tiles[1][1]);
		System.out.println(tiles[1][1]);
		FloorTile ft = (FloorTile) tiles[1][1];

		System.out.println(ft.getGridx());
		player[0] = new Player(ft);
		ft = (FloorTile) tiles[nrtiles - 2][nrtiles-2];
		player[1] = new Player(ft);
		Random r = new Random();

		for(int i = 0; i< nrtiles;i++){
			for(int j = 0; j< nrtiles;j++){
				if(tiles[i][j] instanceof FloorTile){
					if (r.nextInt(10) < 6){

						tiles[i][j] = new Block(i,j,gamecont,nrtiles).setImmovable(false).setImg(bomb);
					}
				}
			}
		}
	}



	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g)
			throws SlickException {
		renderBackground(c,g);
		renderItems(c,g);
		renderMap(c,g);
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
		updatePlayers(c,delta,in);
		updateBombs(c,delta,in);
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
			else if (in.isKeyDown(playerControls.get("P"+(i+1)+"S"))) {
				accel.add(new Vector2f(0, 1));
			}
			else if (in.isKeyDown(playerControls.get("P"+(i+1)+"W"))) {
				accel.add(new Vector2f(-1, 0));
			}
			else if (in.isKeyDown(playerControls.get("P"+(i+1)+"E"))) {
				accel.add(new Vector2f(1, 0));
			}
			if (in.isKeyDown(playerControls.get("P"+(i+1)+"B"))) {
				player[i].hit();
			}
			player[i].setAccel(accel);

			player[i].update(c, delta, this);
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
		//TODO

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

	private void renderMap(GameContainer gc, Graphics g) throws SlickException {

		for(int i =0;i<nrtiles;i++){
			for(int j = 0;j<nrtiles;j++){
				tiles[i][j].render(gc,g, i, j, nrtiles);
			}
		}
	}
	private void renderItems(GameContainer c, Graphics g) {
		// TODO Auto-generated method stub
	}
	public Square[][] getTiles(){
		return tiles;
	}
	public void removeWall(Block b) {
		tiles[b.getGridx()][b.getGridy()] = new FloorTile(b.getGridx(),b.getGridy(),gamecont,nrtiles).setImg(floorTile);
	}
}
