package game;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import entities.Player;


public class Game extends BasicGame {
    Player player;

    public static void main(String[] args) {
        new Game("Fredriks spel");
    }

    public Game(String title) {
        super(title);
        try {
            AppGameContainer game = new AppGameContainer(this, 1024, 720, false);
            game.setTargetFrameRate(60);
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
