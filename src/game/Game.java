package game;

import java.text.ParseException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

import entities.Player;
/**
 * The main class of the game Bombify. A game that is based on players trying to blow each other up.
 * 
 * @author Fredrik Hallberg & Victor Dahlin
 * @version 2012-04-16
 *
 */

public class Game extends BasicGame {
	SettingsContainer gameSettings; // All the settings of the game stored in a HashMap
	Player[] player;
	Rectangle field;
	int hitCounter = 0;
		
	public static void main(String[] args) {
		new Game("Bombify");
	}

	public Game(String title) {
		super(title);
		try{
			gameSettings = new SettingsContainer("res/Bombify.cfg");
		} catch(ParseException e){
			System.out.println("Error in config file on line " + e.getErrorOffset());
			System.exit(0);
		}
		try {

			AppGameContainer game = new AppGameContainer(this, 
					Integer.parseInt(gameSettings.get("GAME_SIZE_X")),
					Integer.parseInt(gameSettings.get("GAME_SIZE_Y")), 
					Integer.parseInt(gameSettings.get("FULLSCREEN"))==1);
			game.setTargetFrameRate(Integer.parseInt(gameSettings.get("MAX_FPS")));
			game.setVSync(true);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
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
			if (in.isKeyDown(Integer.parseInt(gameSettings.get("P"+(i+1)+"N")))) {
				accel.add(new Vector2f(0, -1));
			}
			if (in.isKeyDown(Integer.parseInt(gameSettings.get("P"+(i+1)+"S")))) {
				accel.add(new Vector2f(0, 1));
			}
			if (in.isKeyDown(Integer.parseInt(gameSettings.get("P"+(i+1)+"W")))) {
				accel.add(new Vector2f(-1, 0));
			}
			if (in.isKeyDown(Integer.parseInt(gameSettings.get("P"+(i+1)+"E")))) {
				accel.add(new Vector2f(1, 0));
			}

			if(!player[i].intersects(field)){
				player[i].setAccel(accel.normalise().scale(50f));

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

	// Temp walls for testning player. 
	private void renderWalls(GameContainer c, Graphics g) {
		g.setColor(Color.white);
		
		// x-wall (1) above
		for (int i = 30; i < 760; i += 30) {			
			g.fillRect(i, 0, 30, 30);
		}
		
		// y-wall (1) left side
		for (int i = 0; i < 570; i += 30) {
			g.fillRect(0, i, 30, 30);
		}
		
		// x-wall (2) below
		for (int i = 0; i < 760; i += 30) {
			g.fillRect(i, 570, 30, 30);
		}
		
		// y-wall (2) right side
		for (int i = 0; i < 571; i += 30) {
			g.fillRect(770, i, 30, 30);
		}
	}

	private void renderItems(GameContainer c, Graphics g) {
		// TODO Auto-generated method stub

	}
	

	
	@Override
	public void init(GameContainer c) throws SlickException {
		field = new Rectangle(0, 0, 
				Integer.parseInt(gameSettings.get("GAME_SIZE_X")),
				Integer.parseInt(gameSettings.get("GAME_SIZE_Y")));
		player = new Player[Integer.parseInt(gameSettings.get("PLAYERS"))];
		player[0] = new Player(new Vector2f(100, 100));
		player[1] = new Player(new Vector2f(700, 500));
	}
	@Override
	public void update(GameContainer c, int delta) throws SlickException {
		Input in = c.getInput();
		if (in.isKeyDown(Input.KEY_ESCAPE)) {
			c.exit();
		}
		updateBombs(c,delta,in);
		updatePlayers(c,delta,in);
		updateItems(c,delta,in);
		updateWalls(c,delta,in);
	}
	@Override
	public void render(GameContainer c, Graphics g) throws SlickException {
		renderBackground(c,g);
		renderItems(c,g);
		renderWalls(c,g);
		renderPlayers(c,g);
	}
}
