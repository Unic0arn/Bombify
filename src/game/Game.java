package game;
import java.text.ParseException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import entities.Player;
/**
 * The main class of the game Bombify. A game that is based on players trying to blow each other up.
 * 
 * @author Fredrik Hallberg & Victor Dahlin
 *
 */

public class Game extends BasicGame {
    SettingsContainer gameSettings; // All the settings of the game stored in a HashMap
    Player player;
    
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

    @Override
    public void render(GameContainer c, Graphics g) throws SlickException {
        g.setBackground(Color.black);
        g.setAntiAlias(true);

        player.render(c, g);

    }

    @Override
    public void init(GameContainer c) throws SlickException {
        player = new Player(new Vector2f(100, 100));
    }

    @Override
    public void update(GameContainer c, int delta) throws SlickException {
        handleInput(c, delta);
        player.update(c, delta);
    }

    private void handleInput(GameContainer c, int delta) {
        Input in = c.getInput();
        if (in.isKeyDown(Input.KEY_ESCAPE)) {
            c.exit();
        }

        Vector2f accel = new Vector2f(0, 0);
        if (in.isKeyDown(Input.KEY_W)) {
            accel.add(new Vector2f(0, -1));
        }
        if (in.isKeyDown(Input.KEY_S)) {
            accel.add(new Vector2f(0, 1));
        }
        if (in.isKeyDown(Input.KEY_A)) {
            accel.add(new Vector2f(-1, 0));
        }
        if (in.isKeyDown(Input.KEY_D)) {
            accel.add(new Vector2f(1, 0));
        }
        player.setAccel(accel.normalise().scale(50f));
    }

}
