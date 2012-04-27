package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import settings.SettingsContainer;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Sound;

import powerups.*;

import entities.Bomb;
import entities.Item;
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
 * @version 2012-04-26
 *
 */

public class PlayState extends BasicGameState{
	Boolean paused =false; //Maybe for pausing the game not used at the moment!
	SettingsContainer gameSettings; //An object to contain all settings.
	GameContainer gamecont;
	HashMap<String,Integer> playerControls; //Store the player controls in a hashmap.
	Player[] players; //A vector of the players.
	Square[][] tiles; //A grid of all the "tiles" in the game.
	ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	ArrayList<Item> item = new ArrayList<Item>();
	int nrplayers; // The amount of players 1-4
	int hitCounter = 0;	
	int nrtiles = 15; // Odd number = nice field
	Animation bomb;
	SpriteSheet ss;
	Image hearts;
	Sound soundBomb;

	/**
	 * Creates a new game with the desired settings.
	 * @param gs - The settings file.
	 * @throws SlickException
	 */
	public PlayState(SettingsContainer gs) throws SlickException{
		super();
		gameSettings = gs;
	}
	/**
	 * Starting position for field and players. 
	 */
	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {
		hearts = new Image("res/heartSmall.png");
		soundBomb = new Sound("res/bomb.wav");
		gamecont = gc;
		parseSettings(); // Start by parsing all the settings.


		//Defines the tiles.
		tiles = new Square[nrtiles][nrtiles];	


		/**********************************************
		 * This algorithm decides where walls, concrete
		 * and floor should be in a Tiled Map. 
		 */
		for(int x = 0;x<nrtiles;x++){
			for(int y = 0;y<nrtiles;y++){
				if(x == 0||x ==nrtiles-1||y==0||y==nrtiles-1){
					tiles[x][y] = new OuterWall(x,y,gamecont,nrtiles);
				}else if(y%2==0 && x%2==0){
					tiles[x][y] = new Block(x,y,gamecont,nrtiles);
				}else{

					tiles[x][y] = new FloorTile(x,y,gc,nrtiles);
				}
			}
		}

		/************************************************
		 *  Creates the players and gives them positions.
		 */
		players = new Player[nrplayers];
		FloorTile floorTile = (FloorTile) tiles[1][1];
		players[0] = new Player(floorTile,Color.cyan);


		if(nrplayers > 1){
			floorTile = (FloorTile) tiles[nrtiles-2][nrtiles-2];
			players[1] = new Player(floorTile,Color.red);	
		}
		if(nrplayers > 2){
			floorTile = (FloorTile) tiles[1][nrtiles-2];
			players[2] = new Player(floorTile,Color.green);	
		}
		if(nrplayers > 3){
			floorTile = (FloorTile) tiles[nrtiles-2][1];
			players[3] = new Player(floorTile,Color.magenta);	
		}


		/*******************************************
		 * Adds random walls instead of a FloorTile. 
		 */
		for(int i = 0; i< nrtiles;i++){
			Random dice = new Random();
			for(int j = 0; j< nrtiles;j++){				
				if(tiles[i][j] instanceof FloorTile){					
					if (dice.nextInt(10) < 6){						

						// Avoid corners. 
						if (i==1 && j==1 || i==1 && j==2 || i==2 && j==1 || i==13 && j==2 || i==12 && j==1 || i==13 && j==1 
								|| i==13 && j==12 || i==13 && j==13 || i==12 && j==13 || i==1 && j==12 || i==1 & j==13 || i==2 && j==13){
							//Do nothing. 
						}else {
							tiles[i][j] = new Block(i,j,gamecont,nrtiles).setImmovable(false);
						}
					}
				}
			}
		}
	}

	@Override
	public void render(GameContainer c, StateBasedGame game, Graphics g)
			throws SlickException{
		renderMap(c,g);
		renderBombs(c,g);
		renderItems(c,g);
		renderPlayers(c,g);
	}


	@Override
	public void update(GameContainer c, StateBasedGame game, int delta)
			throws SlickException{
		Input in = c.getInput();

		//Just checking if anyone pressed the escape key to end game.
		//		if (in.isKeyDown(Input.KEY_ESCAPE)) {
		//			c.exit();
		//		}
		updatePlayers(c,delta,in);
		updateBombs(c,delta,in);
		updateItems(c,delta,in);
		updateMap(c,delta,in);
	}

	private void updateMap(GameContainer c, int delta, Input in){
		for(int i = 0;i < tiles.length;i++){
			for(int j=0;j<tiles.length;j++){
				if(tiles[i][j] instanceof FloorTile){
					FloorTile tempTile = (FloorTile)tiles[i][j];
					tempTile.update(c, this, delta);
				}
			}
		}
	}

	private void renderMap(GameContainer gc, Graphics g) throws SlickException{
		for(int i =0;i<nrtiles;i++){
			for(int j = 0;j<nrtiles;j++){
				tiles[i][j].render(gc,g, i, j, nrtiles);
			}
		}
	}

	private void updatePlayers(GameContainer c, int delta, Input in){
		int alivePlayers = 0;
		for(int i = 0; i < players.length; i++){
			if(players[i].isAlive()){
				alivePlayers++;
				Vector2f direction = new Vector2f(0, 0);
				if (in.isKeyDown(playerControls.get("P"+(i+1)+"N"))) {
					direction.add(new Vector2f(0, -1));
				}
				else if (in.isKeyDown(playerControls.get("P"+(i+1)+"S"))) {
					direction.add(new Vector2f(0, 1));
				}
				else if (in.isKeyDown(playerControls.get("P"+(i+1)+"W"))) {
					direction.add(new Vector2f(-1, 0));
				}
				else if (in.isKeyDown(playerControls.get("P"+(i+1)+"E"))) {
					direction.add(new Vector2f(1, 0));
				}
				if (in.isKeyDown(playerControls.get("P"+(i+1)+"B"))) {	
					players[i].hit();
				}

				players[i].setDirection(direction);

				try {
					players[i].update(c, delta, this);
				} catch (SlickException e) {
					e.printStackTrace();
				}
			}			

		}
		if(alivePlayers == 1){
			for(int i = 0; i < players.length; i++){
				if(players[i].isAlive()){					
					//c.exit();
				}
			}

		}else if(alivePlayers == 0){
			System.out.println("N00bs you died by the same bomb, lulz");
			//c.exit();
		}
	}

	private void renderPlayers(GameContainer c, Graphics g){
		for(int i = 0; i < players.length; i++){
			if(players[i].isAlive()) {
				players[i].render(c, g);				

				//Top left corner
				int position = 0;
					int pl1Life = players[0].getLifes();				
					while (pl1Life > 0) {
						g.drawImage(hearts, position, 0); 
						position+=25;
						pl1Life--;
					}

					//Right corner
					position = 0;
					int pl2Life = players[1].getLifes();				
					while (pl2Life > 0) {
						g.drawImage(hearts, 700+position, 570); 
						position+=25;
						pl2Life--;
					}
					
					//Left Corner
					position = 0;
					int pl3Life = players[2].getLifes();				
					while (pl3Life > 0) {
						g.drawImage(hearts, 20+position, 570); 
						position+=25;
						pl3Life--;
					}
				
					// Top right corner
					position = 0;
					int pl4Life = players[3].getLifes();			
					while (pl4Life > 0) {
						g.drawImage(hearts, 750+position, 570); 
						position+=25;
						pl4Life--;
					}
			}
		}
	}

	private void updateBombs(GameContainer c, int delta, Input in){
		for(int i = 0; i < bombs.size();i++){
			bombs.get(i).update(c, this, delta);
		}
	}

	private void renderBombs(GameContainer c, Graphics g){
		for(int i = 0; i < bombs.size();i++){
			bombs.get(i).render(c, g);
		}
	}

	private void updateItems(GameContainer c, int delta, Input in) {		
	}


	private void renderItems(GameContainer c, Graphics g) {
		for(int i = 0; i < item.size(); i++){
			item.get(i).render(c, g);
		}
	}

	@Override
	public int getID() {
		return Constants.GAME;
	}
	public Square[][] getTiles(){
		return tiles;
	}
	public ArrayList<Bomb> getBombs(){
		return bombs;
	}

	public void removeWall(Block b) {
		tiles[b.getGridx()][b.getGridy()] = new FloorTile(b.getGridx(),b.getGridy(),gamecont,nrtiles);		
	}

	public void createBomb(Player p, FloorTile tile){
		Bomb tempBomb = new Bomb(gamecont, p, bomb, tile, nrtiles);
		bombs.add(tempBomb);
		tile.setBomb(tempBomb);
		System.out.println("Bomb created in 'PlayState'");
	}

	public void removeItem(Item i) {
		item.remove(i);
	}


	/**
	 * Removes the bomb from the field and handles the CONSEQUENCES!!!!
	 * @param b
	 */
	public void removeBomb(Bomb b) {
		soundBomb.play();
		boolean hitWallNorth = false,hitWallEast= false,hitWallWest= false,hitWallSouth= false;
		bombs.remove(b);
		b.getTile().setBurning();
		int tilex = b.getTile().getGridx();
		int tiley = b.getTile().getGridy();
		int bombSize = b.getPlayer().getBombSize();

		b.getPlayer().decreaseBombCount();
		/*
		 * Handles the situation of the player standing on the bomb.
		 */
		FloorTile ft = (FloorTile)tiles[tilex][tiley];
		if(ft.hasPlayer()){
			ft.getPlayer().hurt();
		}
		ft.setBomb(null);

		/*
		 * Handles the blaswave in all directions.
		 */
		for (int i = 1; i<=bombSize;i++){
			if(!hitWallNorth)hitWallNorth=checkNorth(i,tilex,tiley);
			if(!hitWallEast)hitWallEast=checkEast(i,tilex,tiley);
			if(!hitWallSouth)hitWallSouth=checkSouth(i,tilex,tiley);
			if(!hitWallWest)hitWallWest=checkWest(i,tilex,tiley);
		}
	}

	/**
	 * Checks the path of the blastwave to the south with a distance of i.
	 * @param i - The distance from the origin.
	 * @param tilex - The origin x
	 * @param tiley - The origin y
	 * @return true - if anything were in the way of the blastwave false - otherwise
	 */
	private boolean checkSouth(int i, int tilex, int tiley) {
		if(tiley+i >= nrtiles)return false;
		Square tempSquare = tiles[tilex][tiley+i];
		return checkPath(tempSquare);
	}
	/**
	 * Checks the path of the blastwave to the West with a distance of i.
	 * @param i - The distance from the origin.
	 * @param tilex - The origin x
	 * @param tiley - The origin y
	 * @return true - if anything were in the way of the blastway false - otherwise
	 */
	private boolean checkWest(int i, int tilex, int tiley) {
		if(tilex-i < 0)return false;
		Square tempSquare = tiles[tilex-i][tiley];
		return checkPath(tempSquare);
	}
	/**
	 * Checks the path of the blastwave to the East with a distance of i.
	 * @param i - The distance from the origin.
	 * @param tilex - The origin x
	 * @param tiley - The origin y
	 * @return true - if anything were in the way of the blastway false - otherwise
	 */
	private boolean checkEast(int i, int tilex, int tiley) {
		if(tilex+i >=nrtiles)return false;
		Square tempSquare = tiles[tilex+i][tiley];
		return checkPath(tempSquare);
	}
	/**
	 * Checks the path of the blastwave to the North with a distance of i.
	 * @param i - The distance from the origin.
	 * @param tilex - The origin x
	 * @param tiley - The origin y
	 * @return true - if anything were in the way of the blastway false - otherwise
	 */
	private boolean checkNorth(int i, int tilex, int tiley) {
		if(tiley-i <0)return false;
		Square tempSquare = tiles[tilex][tiley-i];
		return checkPath(tempSquare);
	}
	/**
	 * Check the tile to find what happens when the blastway reach it.
	 * @param tempSquare - The tile to check.
	 * @return true - if the tile is obstructing the blastway false - otherwise.
	 */
	private boolean checkPath(Square tempSquare){
		if(tempSquare instanceof Block){
			int xTile = tempSquare.getGridx();
			int yTile = tempSquare.getGridy();

			Block block = (Block)tiles[xTile][yTile];	
			if(!block.isImmovable()){
				removeWall(block);					
				tiles[xTile][yTile] = new FloorTile(xTile,yTile,gamecont,nrtiles);
				FloorTile tempTile = (FloorTile)tiles[xTile][yTile];
				tempTile.setBurning();

				int dice = new Random().nextInt(10)+1;
				switch(dice) {
				case 1 : 
					BiggerBomb tempBB = new BiggerBomb(gamecont, tempTile, nrtiles);
					item.add(tempBB); 
					tempTile.setItem(tempBB);
					break;
				case 2 : 
					Life tempL = new Life(gamecont, tempTile, nrtiles);
					item.add(tempL); 	
					tempTile.setItem(tempL);	
					break;
				case 3 : 
					Slow tempSlow = new Slow(gamecont, tempTile, nrtiles);
					item.add(tempSlow); 	
					tempTile.setItem(tempSlow);	
					break;
				case 4 : 
					Speed tempSpeed = new Speed(gamecont, tempTile, nrtiles);
					item.add(tempSpeed);
					tempTile.setItem(tempSpeed);
					break;				
				}
			}
			return true;
		}else if(tempSquare instanceof FloorTile){
			FloorTile ft = (FloorTile)tempSquare;
			ft.setBurning();
			if(ft.hasPlayer()){
				ft.getPlayer().hurt();
			}

			if(ft.hasBomb()){
				ft.getBomb().explodeBomb(this);
			}

		}else if(tempSquare instanceof OuterWall){
			return true;
		}
		return false;
	}
	/**
	 * Parses the most used settings from the hashmap.
	 * This is to avoid constant parsing and ParseInts which are
	 * time consuming.
	 */
	private void parseSettings() {
		nrplayers = Integer.parseInt(gameSettings.get("PLAYERS"));
		playerControls = new HashMap<String,Integer>();
		for(int i = 0; i < nrplayers;i++) {
			playerControls.put("P"+(i+1)+"N",Integer.parseInt(gameSettings.get("P"+(i+1)+"N")));
			playerControls.put("P"+(i+1)+"S",Integer.parseInt(gameSettings.get("P"+(i+1)+"S")));
			playerControls.put("P"+(i+1)+"W",Integer.parseInt(gameSettings.get("P"+(i+1)+"W")));
			playerControls.put("P"+(i+1)+"E",Integer.parseInt(gameSettings.get("P"+(i+1)+"E")));
			playerControls.put("P"+(i+1)+"B",Integer.parseInt(gameSettings.get("P"+(i+1)+"B")));
		}
	}
}
