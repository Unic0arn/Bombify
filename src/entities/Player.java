package entities;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Vector2f;

public class Player {

    Vector2f pos;
    Vector2f velo = new Vector2f(0, 0);
    Vector2f accel = new Vector2f(0, 0);

    public Player(Vector2f pos) {
        this.pos = pos.copy();
    }

    public boolean update(GameContainer c, int delta) {
        velo.add(accel.copy().scale(delta / 1000f));
        accel.set(0, 0);
        pos.add(velo);
        velo.scale(0.9f);
        if (velo.length() < 0.1) {
            velo.set(0, 0);
        }
        return true;
    }

    public void render(GameContainer c, Graphics g) {
        g.setColor(Color.green);
        g.fill(new Circle(pos.x, pos.y, 20));
    }

    public void setAccel(Vector2f a) {
        accel = a;
    }

}
